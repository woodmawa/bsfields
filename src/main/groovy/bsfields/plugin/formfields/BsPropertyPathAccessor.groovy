package bsfields.plugin.formfields

import grails.gorm.validation.DefaultConstrainedProperty

import grails.util.GrailsNameUtils
//import grails.validation.ConstrainedProperty
import grails.gorm.validation.ConstrainedProperty
import org.grails.datastore.mapping.model.PersistentEntity
import org.grails.datastore.mapping.model.PersistentProperty
import org.springframework.validation.FieldError

import static java.util.Collections.EMPTY_LIST
import static org.apache.commons.lang.StringUtils.substringAfterLast

class BsPropertyPathAccessor implements BsBeanPropertyAccessor {

	final String pathFromRoot
	final String propertyName = BsBeanPropertyAccessorFactory.stripIndex pathFromRoot.contains('.') ? substringAfterLast(pathFromRoot, '.') : pathFromRoot

	BsPropertyPathAccessor(String pathFromRoot) {
		this.pathFromRoot = pathFromRoot
	}

	String getDefaultLabel() {
		GrailsNameUtils.getNaturalName(propertyName)
	}

	Object getRootBean() { null }
	Class getRootBeanType() { null }
	Class getBeanType() { null }

	PersistentEntity getBeanClass() { null }
	List<Class> getBeanSuperclasses() { EMPTY_LIST }
	Class getPropertyType() { null }
	List<Class> getPropertyTypeSuperclasses() { EMPTY_LIST }
	Object getValue() { null }
	ConstrainedProperty getConstraints() { new DefaultConstrainedProperty(Object, propertyName, String) }
	PersistentProperty getPersistentProperty() { null }
	List<String> getLabelKeys() { EMPTY_LIST }
	List<FieldError> getErrors() { EMPTY_LIST }
	boolean isRequired() { false }
	boolean isInvalid() { false }
}
