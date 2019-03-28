package voting.backend.grails3


import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Candidate {
    Integer id
    String name
    String party
    String profession
    String residency
    Integer age
    String education
    String picture
}