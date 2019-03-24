package voting.backend.grails3

class BootStrap {

    def init = { servletContext ->
        new Candidate(id: 1, name: "Ville Vihreä", affiliation: "Vihreät", picture: "ville.jpg").save()
        new Candidate(id: 2, name: "Daniela Demari", affiliation: "SDP", picture: "daniela.jpg").save()
        new Candidate(id: 3, name: "Kalle Keskusta", affiliation: "Keskusta", picture: "kalle.jpg").save()
    }
    def destroy = {
    }
}
