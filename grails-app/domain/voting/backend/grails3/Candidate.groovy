package voting.backend.grails3


import grails.rest.Resource

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

    static constraints = {
        id min: 1
        party inList: ['Vihreät', 'SDP', 'Keskusta']
        name minSize: 3, maxSize: 256
        profession maxSize: 256
        residency minSize: 2, maxSize: 50
        age min: 18, max: 150
        picture nullable: true
    }
}