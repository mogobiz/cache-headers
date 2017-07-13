package test

import grails.plugins.rest.client.RestBuilder
import grails.test.mixin.integration.Integration
import grails.transaction.Rollback
import spock.lang.Specification

@Rollback
@Integration
class BookControllerSpec extends Specification {

    def setup() {
        new Book(name: 'The Definitive Guide to Grails 2').save()
    }

    def cleanup() {
        Book.where { name == 'The Definitive Guide to Grails 2' }.deleteAll()
    }

    def "book show"() {
        given:
        RestBuilder rest = new RestBuilder()

        expect:
        Book.read(1)
        
        when:

        def resp = rest.get("http://localhost:${serverPort}/book/1")

        then:
        resp.statusCode.value() == 200
        resp.headers.keySet().contains('ETag')
        resp.headers.keySet().contains('Last-Modified')
        resp.headers.get('ETag') as String == '[1:0]'
        resp.headers.get('Last-Modified') != null
    }
}
