package test

import grails.test.mixin.TestFor
import spock.lang.Specification
import spock.lang.Unroll
import static javax.servlet.http.HttpServletResponse.SC_METHOD_NOT_ALLOWED
import static javax.servlet.http.HttpServletResponse.SC_NOT_FOUND

@TestFor(BookController)
class BookControllerAllowedMethodsSpec extends Specification  {

    @Unroll
    def "test TestController.show does not accept #method requests"(String method) {
        when:
        request.method = method
        controller.show()

        then:
        response.status == SC_METHOD_NOT_ALLOWED

        where:
        method << ['PATCH', 'DELETE', 'POST', 'PUT']
    }

    def "test TestController.show accepts GET requests"() {
        when:
        request.method = 'GET'
        controller.show()

        then:
        response.status == 422
    }

}
