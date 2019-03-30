package voting.backend.grails3

import grails.plugin.springsecurity.annotation.Secured
import grails.converters.*
import groovy.util.logging.Slf4j

@Slf4j
class VotingController {
	static responseFormats = ['json']
	def springSecurityService

    @Secured('ROLE_VOTER')
    def vote(VoteCommand cmd) {
        if(!cmd?.validate())
        {
            def returnValues = ["success":false, message:"Command object validation failed", "errors":cmd.errors.allErrors]
            render returnValues as JSON
            return
        }

        def user = springSecurityService.principal
        if(Vote.findAllByVoterSsn(user.password))
        {
            def returnValues = ["success":false, message:"This voter has already voted"]
            render returnValues as JSON
            return
        }
        Vote vote = [candidate:Candidate.read(cmd.candidateId), voterSsn:user.password]
        vote.save([flush:true])
        def returnValues = ["success":true, message:"Vote accepted", vote:vote]
        render returnValues as JSON
    }
}
