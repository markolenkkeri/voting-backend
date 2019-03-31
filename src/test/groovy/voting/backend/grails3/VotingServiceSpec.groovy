package voting.backend.grails3

import grails.plugin.springsecurity.SpringSecurityService
import grails.testing.gorm.DataTest
import grails.testing.services.ServiceUnitTest
import spock.lang.Specification

class VotingServiceSpec extends Specification implements ServiceUnitTest<VotingService>, DataTest {

    def mockSecurity

    void setupSpec() {
        mockDomains Vote, Candidate
    }

    def setup() {
        mockSecurity = Mock(SpringSecurityService)
        service.springSecurityService = mockSecurity
    }

    def cleanup() {
    }

    void "Voting with correct authentication works correctly"() {
        given: "An authenticated user"
        mockSecurity.principal >> [password: "password"]
        new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes").save()
        when: "The voting method is called"
        def returnValue = service.vote(new VoteCommand(candidateId: 1))
        then: "An OK message is returned"
        returnValue.success
    }

    void "Voting with incorrect authentication gives an error"() {
        given: "An authenticated user"
        mockSecurity.principal >> null
        new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes").save()
        when: "The voting method is called"
        def returnValue = service.vote(new VoteCommand(candidateId: 1))
        then: "An OK message is returned"
        !returnValue.success
    }

    void "Voting with incorrect candidate id gives an error"() {
        given: "An authenticated user"
        mockSecurity.principal >> [password: "password"]
        new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes").save()
        when: "The voting method is called"
        def returnValue = service.vote(new VoteCommand(candidateId: 2))
        then: "An OK message is returned"
        !returnValue.success
    }

    void "Voting only works once"() {
        given: "An authenticated user"
        mockSecurity.principal >> [password: "password"]
        new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes").save()
        when: "The voting method is called"
        service.vote(new VoteCommand(candidateId: 1))
        def returnValue = service.vote(new VoteCommand(candidateId: 1))
        then: "An OK message is returned"
        !returnValue.success
    }
}
