package com.softwood.domain

import grails.gorm.services.Service

@Service(Holder)
interface HolderService {

    Holder get(Serializable id)

    List<Holder> list(Map args)

    Long count()

    void delete(Serializable id)

    Holder save(Holder holder)

}