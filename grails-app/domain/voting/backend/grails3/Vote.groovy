package voting.backend.grails3

class Vote {

    String voterSsn
    Candidate candidate

    static mapping = {
        voterSsn index: 'ssn_index', unique: true
    }
}
