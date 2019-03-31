package voting.backend.grails3

import grails.plugin.springsecurity.SpringSecurityService
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification
import spock.lang.Subject

class VotingServiceSpec extends Specification implements ServiceUnitTest<VotingService>{

    @Subject
    def votingService

    def mockSecurity

    def setup() {
        votingService = new VotingService()
        mockSecurity = Mock(SpringSecurityService)
        votingService.springSecurityService = mockSecurity
    }

    def cleanup() {
    }

    void "Voting with correct authentication works correctly"() {
        given: "An authenticated user"
            mockSecurity.principal >> null
        when: "The voting method is called"
            def returnValue = votingService.vote(new VoteCommand())
        then: "An OK message is returned"
            returnValue.success==true
    }

    void "test something"() {
        expect:"fix me"
            true == false
    }
}
