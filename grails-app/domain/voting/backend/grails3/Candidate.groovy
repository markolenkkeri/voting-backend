package voting.backend.grails3


import grails.rest.*

@Resource(readOnly = false, formats = ['json', 'xml'])
class Candidate {
    Integer id
    String name
    String affiliation
    String picture
}