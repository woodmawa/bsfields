package com.softwood.domain

class Holder {

    String name

    Collection bsitems = []

    static hasMany = [bsitems: BootstrapTest]

    static constraints = {
    }
}
