package bsfields


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner

class MessagePrinter implements CommandLineRunner {

    @Autowired (required=true)
    SayHello sample

    @Override
    void run(final String... args) throws Exception {
        println "Sample message printer says: ${sample.speak()}"
        println "up and running..."
    }
}
