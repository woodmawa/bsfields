package com.softwood.service

import bsfields.plugin.formfields.BsBeanPropertyAccessorFactory
import com.softwood.domain.BeanPropertyAccessorIntegrationService
import com.softwood.domain.BootstrapTest
import grails.core.GrailsApplication
import grails.testing.services.ServiceUnitTest
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Specification
import grails.util.Holders

class BeanPropertyAccessorIntegUnitServiceSpec extends Specification implements ServiceUnitTest<BeanPropertyAccessorIntegrationService>{


    @Autowired BeanPropertyAccessorIntegrationService service
    GrailsApplication grailsApplication

    def setup() {

    }

    def cleanup() {
    }

    void "test something"() {

        given : ""

            if (!service) {
                grailsApplication = Holders.grailsApplication

                service = grailsApplication?.getArtefact('Service', 'beanPropertyAccessorIntegrationService')
            }
            assert service

        when:"we get the service "
        BsBeanPropertyAccessorFactory fac = service.getAccessor()

        then: ""
        fac

    }
}
