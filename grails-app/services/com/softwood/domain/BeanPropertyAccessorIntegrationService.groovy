package com.softwood.domain

import bsfields.plugin.formfields.BsBeanPropertyAccessor
import bsfields.plugin.formfields.BsBeanPropertyAccessorFactory
import grails.gorm.transactions.Transactional

@Transactional
class BeanPropertyAccessorIntegrationService {

    BootstrapTestService bootstrapTestService
    BsBeanPropertyAccessorFactory bsBeanPropertyAccessorFactory

    def getAccessor() {

        assert bsBeanPropertyAccessorFactory
        BsBeanPropertyAccessorFactory fac = bsBeanPropertyAccessorFactory
        BootstrapTest bst = bootstrapTestService.get(1L)

        def accessor = fac.accessorFor(bst, 'strProp')

        accessor
    }
}
