package voting.backend.grails3

class BootStrap {

    def init = { servletContext ->
        new Candidate(id: 1, name: "Ville Vihreä", party: "Vihreät", picture: "ville.jpg", profession: "Viherkasviasiantuntija", residency: "Turku", age: 38, education: "Yes").save()
        new Candidate(id: 2, name: "Daniela Demari", party: "SDP", picture: "daniela.jpg", profession: "Demograafikko", residency: "Turku", age: 38, education: "Yes").save()
        new Candidate(id: 3, name: "Kalle Keskusta", party: "Keskusta", picture: "kalle.jpg", profession: "Kauppamies", residency: "Turku", age: 38, education: "Yes").save()
    }
    def destroy = {
    }
}
