package com.softwood.service

import com.softwood.domain.Holder
import com.softwood.domain.HolderService
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class HolderServiceSpec extends Specification {

    HolderService holderService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Holder(...).save(flush: true, failOnError: true)
        //new Holder(...).save(flush: true, failOnError: true)
        //Holder holder = new Holder(...).save(flush: true, failOnError: true)
        //new Holder(...).save(flush: true, failOnError: true)
        //new Holder(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //holder.id
    }

    void "test get"() {
        setupData()

        expect:
        holderService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Holder> holderList = holderService.list(max: 2, offset: 2)

        then:
        holderList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        holderService.count() == 5
    }

    void "test delete"() {
        Long holderId = setupData()

        expect:
        holderService.count() == 5

        when:
        holderService.delete(holderId)
        sessionFactory.currentSession.flush()

        then:
        holderService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Holder holder = new Holder()
        holderService.save(holder)

        then:
        holder.id != null
    }
}
