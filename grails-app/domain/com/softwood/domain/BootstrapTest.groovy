package com.softwood.domain

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class BootstrapTest {

    enum BootstrapTypes {
        Real,
        Plastic,
        Fantastic
    }

    String strProp = "init value"
    Map mapProp = ['Peter': 'Grimes', 'Edward':'Elgar']
    BootstrapTypes typeProp = BootstrapTypes.Plastic
    LocalDateTime ldtProp = LocalDateTime.now()
    LocalDate dtProp = LocalDate.now()

    static belongsTo = [holder: Holder]

    static constraints = {
        strProp()       //now mandatory
        typeProp nullable: false
        ldtProp nullable: true
        dtProp nullable: true
        mapProp nullable:true
        holder nullable: true

    }

    /* handle browser posted string */
    void setLdtProp(String browersString, format = null) {
        println "fired ldt convertor with string  $browersString "
        LocalDateTime ldt
        try {
            if (!format)
                ldt = LocalDateTime.parse(browersString) //assumes ISO_LOCAL_DATE_TIME
            else {
                //to sort out getting  locale
                ldt = LocalDateTime.parse(browersString, DateTimeFormatter.ofPattern(format))
            }
        } catch (ex) {
            throw ex
        }

        ldtProp = ldt

    }

    void setLdtProp(LocalDateTime ldt) {
        println "fired ldt convertor with LDT  $ldt"

        ldtProp = ldt

    }


    /* handle browser posted string */
    void setDtProp(String browersString, format = null) {
        println "fired dt convertor string $browersString"

        LocalDate dt
        try {
            if (!format)
                dt = LocalDateTime.parse(browersString) //assumes ISO_LOCAL_DATE
            else {
                //to sort out getting  locale
                dt = LocalDateTime.parse(browersString, DateTimeFormatter.ofPattern(format))
            }
        } catch (ex) {
            throw ex
        }

        ldtProp = dt

    }

    void setDtProp(LocalDate dt) {
        println "fired dt convertor DT $dt"

        dtProp = dt

    }

    /* handle browser posted map representation */
    void setMapProp (String browserString) {
        println "fired map convertor mapStr $browserString"

        Map map = new HashMap()

        String[] pairs = browserString?.replaceAll("[{}]", '').split(',')
        for (pair in pairs){
            String[] kv = pair.split('=')
            map.put (kv[0], kv[1])
        }
        mapProp = map
    }

    //needs this form as well or otherwise mapProp is not generated for the domain class
    void setMapProp (Map map) {
        println "fired map convertor with map $map"

        mapProp.clear()
        mapProp.putAll(map)
    }
}
