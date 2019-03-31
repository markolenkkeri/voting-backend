package voting.backend.grails3

import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class CandidateSpec extends Specification implements DomainUnitTest<Candidate> {

    def setup() {
    }

    def cleanup() {
    }

    void "Saving candidates works correctly"() {
        setup: "Attempt to save 3 candidates"
        new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes").save()
        new Candidate(id: 2, name: "Daniela Demari", party: "SDP", picture: "daniela.jpg", profession: "Demograafikko", residency: "Turku", age: 38, education: "Yes").save()
        new Candidate(id: 3, name: "Kalle Keskusta", party: "Keskusta", picture: "kalle.jpg", profession: "Kauppamies", residency: "Turku", age: 38, education: "Yes").save()

        expect: "Database size is correct"
        Candidate.count() == 3
    }

    @Unroll
    void "Invalid candidates fail correctly for #name"(int id, String name, String party, String picture, String profession, String residency, int age, String education, boolean isValid) {
        expect:
        new Candidate(id: id, name: name, party: party, picture: picture, profession: profession, residency: residency, age: age, education: education).validate() == isValid

        where:
        id | name              | party     | picture | profession   | residency | age | education | isValid
        1  | "a"               | "Vihreät" | null    | "On"         | "Jossain" | 19  | "Ei"      | false
        2  | "Erkki Esimerkki" | "Hakkeri" | null    | "Rikollinen" | null      | 12  | "Ei"      | false
    }
}
