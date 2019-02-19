package com.domain

import bsfields.Application
import bsfields.plugin.formfields.BsBeanPropertyAccessor
import bsfields.plugin.formfields.BsBeanPropertyAccessorFactory
import com.softwood.domain.BeanPropertyAccessorIntegrationService
import com.softwood.domain.BootstrapTest
import grails.core.GrailsApplication
import grails.testing.gorm.DataTest
import grails.testing.mixin.integration.Integration
import grails.transaction.*
import grails.util.Holders
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification

@Integration (applicationClass = Application.class)
@Rollback
class BeanPropertyAccessorIntegrationServiceSpec extends Specification {

    @Autowired BeanPropertyAccessorIntegrationService service
    @Autowired GrailsApplication grailsApplication

    def setup() {
        assert BootstrapTest.count() == 1

    }

    def cleanup() {
    }

    void "test something"() {
        given : ""


        assert service
        BootstrapTest.count() == 1

        when:"we get the service "
        BsBeanPropertyAccessor acc = service.getAccessor()
        println "got fac $acc "

        println acc.getConstraints().appliedConstraints.collect {it.name}

        then: ""
        acc.value == "init value"
        acc.propertyName == "strProp"

    }
}
