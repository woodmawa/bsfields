package com.softwood.domain

import grails.gorm.services.Service

@Service(BootstrapTest)
interface BootstrapTestService {

    BootstrapTest get(Serializable id)

    List<BootstrapTest> list(Map args)

    Long count()

    void delete(Serializable id)

    BootstrapTest save(BootstrapTest bootstrapTest)

}