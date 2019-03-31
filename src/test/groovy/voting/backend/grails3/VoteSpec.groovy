package voting.backend.grails3

import grails.testing.gorm.DomainUnitTest
import spock.lang.Shared
import spock.lang.Specification

class VoteSpec extends Specification implements DomainUnitTest<Vote> {

    @Shared
    Candidate candidate

    def setup() {
        candidate = new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes")
        candidate.save()
    }

    def cleanup() {
        candidate.delete()
        Vote.deleteAll()
    }

    void "Saving a vote works correctly"() {
        when: "A vote is saved"
        new Vote(voterSsn: "010101-001A", candidate: candidate).save()
        then: "A vote is in the database"
        Vote.count() == 1
    }
}
