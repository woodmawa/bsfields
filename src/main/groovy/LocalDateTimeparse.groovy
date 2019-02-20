import java.time.LocalDateTime

def input = '2019-02-04T13:44:29' //'2019-02-04T13:44:29.949'

LocalDateTime ldt
try {
     ldt = LocalDateTime.parse(input)
}
catch (ex){
    println "exceptiopn : $ex.message"
    System.exit (-1)
}

println "got ldt $ldt"