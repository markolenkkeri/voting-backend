package voting.backend.grails3

import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class TupasControllerSpec extends Specification implements ControllerUnitTest<TupasController> {

    void "A tupas initiation generates correct json"() {
        when:
        webRequest.actionName = 'index'
        controller.index()
        def jsonBody = response.getJson()
        then:
        jsonBody.loginVariables.A01Y_ACTION_ID == "701"
        jsonBody.loginVariables.A01Y_KEYVERS == "0001"
        jsonBody.loginVariables.A01Y_ALG == "03"
        jsonBody.loginVariables.A01Y_VERS == "0002"
        jsonBody.loginVariables.A01Y_LANGCODE == "FI"
    }
}