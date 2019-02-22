package bsfields

import com.softwood.domain.BootstrapTest
import com.softwood.domain.Holder

class BootStrap {

    def init = { servletContext ->
        BootstrapTest dinst = new BootstrapTest()
        dinst.save (failOnError:true, flush:true)

        assert dinst.id

        Holder h = new Holder(name:'cup')
        h.save()
        h.addToBsitems(dinst)
        h.addToBsitems(new BootstrapTest())
        h.save(flush:true)


    }


    def destroy = {
    }
}
