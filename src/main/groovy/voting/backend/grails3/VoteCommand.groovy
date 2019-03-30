package voting.backend.grails3

import grails.validation.Validateable

class VoteCommand implements Validateable {

    Integer candidateId

    static constraints = {
        candidateId nullable: false, min: 0
    }
}
