package voting.backend.grails3

import grails.testing.gorm.DataTest
import grails.testing.web.controllers.ControllerUnitTest
import spock.lang.Specification

class VotingControllerSpec extends Specification implements ControllerUnitTest<VotingController>, DataTest {

    VotingService votingService

    def setup() {
        votingService = Mock(VotingService)
        votingService.vote(_) >> [success: true]
        controller.votingService = votingService
    }

    void "A vote request responds with json status ok"() {
        given: "A vote command object"
        VoteCommand cmd = [candidateId: 1]
        when:
        webRequest.actionName = 'vote'
        controller.vote(cmd)
        def jsonBody = response.getJson()
        then:
        jsonBody.success == true
    }

    void "An incomplete vote request responds with correct json error"() {
        given: "A vote command object"
        VoteCommand cmd = new VoteCommand()
        when:
        webRequest.actionName = 'vote'
        controller.vote(cmd)
        def jsonBody = response.getJson()
        then:
        jsonBody.success == false
    }

    void "An erroneous vote request responds with correct json error"() {
        given: "A vote command object"
        VoteCommand cmd = [candidateId: -1]
        when:
        webRequest.actionName = 'vote'
        controller.vote(cmd)
        def jsonBody = response.getJson()
        then:
        jsonBody.success == false
    }
}