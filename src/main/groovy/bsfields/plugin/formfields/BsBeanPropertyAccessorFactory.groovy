/*
 * Copyright 2012 Rob Fletcher
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bsfields.plugin.formfields

import org.grails.datastore.gorm.validation.constraints.registry.ConstraintRegistry
import org.grails.datastore.mapping.model.types.Basic
import org.grails.scaffolding.model.property.Constrained
//import grails.gorm.validation.Constrained
import grails.gorm.validation.DefaultConstrainedProperty
import org.grails.datastore.mapping.model.MappingContext
import org.grails.datastore.mapping.model.PersistentEntity
import groovy.transform.PackageScope
import grails.core.*
import grails.core.support.GrailsApplicationAware
import grails.core.support.proxy.ProxyHandler
import grails.gorm.validation.ConstrainedProperty
//import grails.gorm.validation.ConstraintsEvaluator
import org.grails.datastore.gorm.validation.constraints.eval.ConstraintsEvaluator
import org.grails.datastore.mapping.model.PersistentProperty
import org.grails.datastore.mapping.model.types.Association
import org.springframework.beans.BeanWrapper
import org.springframework.beans.BeanWrapperImpl
import org.springframework.beans.NotReadablePropertyException
import org.springframework.beans.PropertyAccessorFactory
import org.springframework.beans.factory.annotation.Autowired

import java.lang.reflect.ParameterizedType
import java.util.regex.Pattern

class BsBeanPropertyAccessorFactory implements GrailsApplicationAware {

	def grailsApplication
	ConstraintsEvaluator constraintsEvaluator
	ProxyHandler proxyHandler

	@Override
	@Autowired
	void setGrailsApplication(GrailsApplication grailsApplication) {
		println "injecting grailsApplication"
		this.grailsApplication = grailsApplication
	}

	//for 3.3 move to this : https://stackoverflow.com/questions/45797420/upgrade-from-grails-3-2-to-grails-3-3-grailsdomainclass-api-deprecated
	MappingContext mappingContext //= grailsApplication.mappingContext

	//no-args constructor
	BsBeanPropertyAccessorFactory() {
		if (!grailsApplication) {


			println "BsBeanPropertyAccessorFactory  found mappingContext $mappingContext "
		}
		mappingContext
	}


	BsBeanPropertyAccessor accessorFor(bean, String propertyPath) {
		if (bean == null) {
			new BsPropertyPathAccessor(propertyPath)
		} else {
			def params = [rootBean: bean, rootBeanType: bean.getClass(), pathFromRoot: propertyPath, grailsApplication: grailsApplication]
			params.rootBeanClass = resolveDomainClass(bean.getClass())

			resolvePropertyFromPath(bean, propertyPath, params)

			new BsBeanPropertyAccessorImpl(params)
		}
	}

	//private GrailsDomainClass resolveDomainClass(Class beanClass) {
	private org.grails.datastore.mapping.model.PersistentEntity resolveDomainClass(Class beanClass) {
		//wwgrailsApplication.getDomainClass(beanClass.name)

		def persistentEntity = grailsApplication.mappingContext.getPersistentEntity(beanClass.name)

		persistentEntity
	}

	private void resolvePropertyFromPath(bean, String propertyPath, Map params) {
		def beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(bean)
		def pathElements = propertyPath.tokenize(".")
		resolvePropertyFromPathComponents(beanWrapper, pathElements, params)
	}

	private void resolvePropertyFromPathComponents(BeanWrapper beanWrapper, List<String> pathElements, Map params) {
		def propertyName = pathElements.remove(0)
		def beanClass = resolveDomainClass(beanWrapper.wrappedClass)
		def propertyType = resolvePropertyType(beanWrapper, beanClass, propertyName)
		def value = beanWrapper.getPropertyValue(propertyName)
		if (pathElements.empty) {
			params.beanType = beanWrapper.wrappedClass
			params.beanClass = beanClass
			params.value = value
			params.propertyType = propertyType
			params.propertyName = stripIndex(propertyName)
			//ww params.persistentProperty = beanClass?.getPersistentProperty(params.propertyName)
			params.persistentProperty = beanClass.getPropertyByName(params.propertyName)
			params.constraints = resolveConstraints(beanWrapper, beanClass, params.propertyName)
		} else {
			resolvePropertyFromPathComponents(beanWrapperFor(propertyType, value), pathElements, params)
		}
	}

	//private ConstrainedProperty resolveConstraints(BeanWrapper beanWrapper, GrailsDomainClass beanClass, String propertyName) {
	//private Constrained resolveConstraints(BeanWrapper beanWrapper, PersistentEntity beanClass, String propertyName) {
	/*
	 * this is a hack but seems to be working
	 */

	private ConstrainedProperty resolveConstraints(BeanWrapper beanWrapper, PersistentEntity beanClass, String propertyName) {
		grails.gorm.validation.Constrained constraint = constraintsEvaluator.evaluate(beanWrapper.wrappedClass)[propertyName]
		if (!constraint) {
			constraint = createDefaultConstraint(beanWrapper, propertyName)
		}

		//cheated here
		constraint as ConstrainedProperty

	}

    private grails.gorm.validation.Constrained createDefaultConstraint(BeanWrapper beanWrapper, String propertyName) {
		//def defaultConstraint = new ConstrainedProperty(beanWrapper.wrappedClass, propertyName, beanWrapper.getPropertyType(propertyName))
        def defaultConstraint = new DefaultConstrainedProperty (beanWrapper.wrappedClass, propertyName, beanWrapper.getPropertyType(propertyName))
        defaultConstraint.nullable = true
        defaultConstraint
    }

	//private Class resolvePropertyType(BeanWrapper beanWrapper, GrailsDomainClass beanClass, String propertyName) {
    private Class resolvePropertyType(BeanWrapper beanWrapper, PersistentEntity beanClass, String propertyName) {
		Class propertyType = null
		if (beanClass) {
			propertyType = resolveDomainPropertyType(beanClass, propertyName)
		}
		if (!propertyType) {
			propertyType = resolveNonDomainPropertyType(beanWrapper, propertyName)
		}
		propertyType
	}

	//private Class resolveDomainPropertyType(GrailsDomainClass beanClass, String propertyName) {
	private Class resolveDomainPropertyType(PersistentEntity beanClass, String propertyName) {

		def propertyNameWithoutIndex = stripIndex(propertyName)
		def persistentProperty = beanClass.getPropertyByName(propertyNameWithoutIndex)
		if (!persistentProperty && beanClass.isIdentityName(propertyNameWithoutIndex)) {
			persistentProperty = beanClass.identity
		}
		if (!persistentProperty) {
			return null
		}
		boolean isIndexed = propertyName =~ INDEXED_PROPERTY_PATTERN
		if (isIndexed) {
			if (persistentProperty instanceof Basic) {
				persistentProperty.componentType
			} else if (persistentProperty instanceof Association) {
				persistentProperty.associatedEntity.javaClass
			}
		} else {
			persistentProperty.type
		}
	}

	private Class resolveNonDomainPropertyType(BeanWrapper beanWrapper, String propertyName) {
		def type = beanWrapper.getPropertyType(propertyName)
		if (type == null) {
			def match = propertyName =~ INDEXED_PROPERTY_PATTERN
			if (match) {
				def genericType = beanWrapper.getPropertyDescriptor(match[0][1]).readMethod.genericReturnType
				if (genericType instanceof ParameterizedType) {
					switch (genericType.rawType) {
						case Collection:
							type = genericType.actualTypeArguments[0]
							break
						case Map:
							type = genericType.actualTypeArguments[1]
							break
					}
				} else {
					type = Object
				}
			}
		}
		type
	}

	private BeanWrapper beanWrapperFor(Class type, value) {
		value ? PropertyAccessorFactory.forBeanPropertyAccess(proxyHandler.unwrapIfProxy(value)) : new BeanWrapperImpl(type)
	}

	private static final Pattern INDEXED_PROPERTY_PATTERN = ~/^(\w+)\[(.+)\]$/

	@PackageScope
	static String stripIndex(String propertyName) {
		def matcher = propertyName =~ INDEXED_PROPERTY_PATTERN
		matcher.matches() ? matcher[0][1] : propertyName
	}


}
