package com.softwood.domain

import com.softwood.domain.BootstrapTest
import com.softwood.domain.BootstrapTestService
import grails.validation.ValidationException
import org.springframework.validation.FieldError

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

import static org.springframework.http.HttpStatus.*

class BootstrapTestController {

    BootstrapTestService bootstrapTestService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bootstrapTestService.list(params), model:[bootstrapTestCount: bootstrapTestService.count()]
    }

    def show(Long id) {
        def bst =bootstrapTestService.get(id)
        respond bst
    }

    def plain(Long id) {
        respond bootstrapTestService.get(id)
    }

    def strprop(Long id) {
        respond bootstrapTestService.get(id)
    }

    def create() {
        respond new BootstrapTest(params)
    }

    def save(BootstrapTest bootstrapTest) {
        if (bootstrapTest == null) {
            notFound()
            return
        }

        //special handling for <input type="date" or "datetime-local" as these are delivered as strings
        //in params map and will fail validation on domain properties

        LocalDateTime ldtProp
        LocalDate dtProp

        if (bootstrapTest.hasErrors()) {
            bootstrapTest.clearErrors()
            try {
                ldtProp = LocalDateTime.parse(params.ldtProp?.toString()) //ISO_LOCAL_DATE_TIME
                dtProp = LocalDate.parse(params.dtProp?.toString(), DateTimeFormatter.ISO_LOCAL_DATE) //ISO_LOCAL_DATE
                bootstrapTest.ldtProp =  ldtProp
                bootstrapTest.dtProp = dtProp
                bootstrapTest.validate()
            } catch (ex) {
                println "exception $ex.message"
                respond bootstrapTest.errors, view:'edit'
                return
            }
        }



        try {
            bootstrapTestService.save(bootstrapTest)
        } catch (ValidationException e) {
            respond bootstrapTest.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'bootstrapTest.label', default: 'BootstrapTest'), bootstrapTest.id])
                redirect bootstrapTest
            }
            '*' { respond bootstrapTest, [status: CREATED] }
        }
    }

    def edit(Long id) {
        def bs = bootstrapTestService.get(id)
        respond bs
    }

    def update(BootstrapTest bootstrapTest) {
        if (bootstrapTest == null) {
            notFound()
            return
        }

        LocalDateTime ldtProp
        LocalDate dtProp
        String mapPropStr = params.mapProp

        Map map = new HashMap()

        String[] pairs = mapPropStr?.replaceAll("[{}]", '').split(',')
        for (pair in pairs){
            String[] kv = pair.split('=')
            map.put (kv[0], kv[1])
        }

        if (bootstrapTest.hasErrors()) {
            bootstrapTest.clearErrors()
            try {
                ldtProp = LocalDateTime.parse(params.ldtProp?.toString()) //ISO_LOCAL_DATE_TIME
                dtProp = LocalDate.parse(params.dtProp?.toString(), DateTimeFormatter.ISO_LOCAL_DATE) //ISO_LOCAL_DATE
                bootstrapTest.ldtProp =  ldtProp
                bootstrapTest.dtProp = dtProp
                bootstrapTest.mapProp = map
                bootstrapTest.validate()
            } catch (ex) {
                println "exception $ex.message"
                respond bootstrapTest.errors, view:'edit'
                return
            }
        }


        try {
            bootstrapTestService.save(bootstrapTest)
        } catch (ValidationException e) {
            respond bootstrapTest.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'bootstrapTest.label', default: 'BootstrapTest'), bootstrapTest.id])
                redirect bootstrapTest
            }
            '*'{ respond bootstrapTest, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        bootstrapTestService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'bootstrapTest.label', default: 'BootstrapTest'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'bootstrapTest.label', default: 'BootstrapTest'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
