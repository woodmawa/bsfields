package com.softwood.domain

import com.softwood.domain.BootstrapTest
import com.softwood.domain.BootstrapTestService
import grails.validation.ValidationException

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.http.HttpStatus.*

class BootstrapTestController {

    BootstrapTestService bootstrapTestService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond bootstrapTestService.list(params), model:[bootstrapTestCount: bootstrapTestService.count()]
    }

    def show(Long id) {
        respond bootstrapTestService.get(id)
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

        //hack - as validation is failing - copy seems to fix ?

        BootstrapTest bst  = new BootstrapTest()
        bst.strProp = bootstrapTest.strProp
        bst.typeProp = bootstrapTest.typeProp
        bst.ldtProp = bootstrapTest.ldtProp
        bst.dtProp = bootstrapTest.dtProp
        bst.mapProp = bootstrapTest.mapProp

        if (!bst.validate())
        {
            println "got errors " + bst.errors.fieldErrors
        }
        bootstrapTest = bst
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

        //hack - to get validation to work
        BootstrapTest bst = new BootstrapTest()
        bst.strProp = bootstrapTest.strProp
        bst.typeProp = bootstrapTest.typeProp
        //bst.ldtProp = bootstrapTest.ldtProp
        bst.dtProp = bootstrapTest.dtProp
        //bst.mapProp = bootstrapTest.mapProp
        bst.id = bootstrapTest.id

        if (!bootstrapTest.validate()){
            println "object delivered to update action from edit form doesnt validate "
            respond bootstrapTest.errors, view:'edit'
            return
        }
        /*else {
            bootstrapTest = bst
            bootstrapTest.save()
        }*/

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
