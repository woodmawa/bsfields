package com.softwood.domain

import java.time.LocalDate
import java.time.LocalDateTime

class BootstrapTest {

    String strProp = "init value"
    Map mapProp = ['Peter': 'Grimes', 'Edward':'Elgar']
    LocalDateTime ldtProp = LocalDateTime.now()
    LocalDate dtProp = LocalDate.now()

    static belongsTo = [holder:Holder]

    static constraints = {
        strProp()       //now mandatory
        ldtProp nullable:true
        dtProp nullable:true
        mapProp nullable:true
        holder nullable:true

    }
}
