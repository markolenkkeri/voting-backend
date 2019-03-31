package voting.backend.grails3

import grails.gorm.transactions.Transactional
import groovy.util.logging.Slf4j

@Transactional
@Slf4j
class VotingService {
    def springSecurityService

    def vote(VoteCommand cmd) {
        log.info("Starting vote operation")
        def user = springSecurityService.principal
        if(Vote.findAllByVoterSsn(user.password))
        {
            log.warn("Voter has already voted, failing vote operation.")
            def returnValues = ["success":false, message:"This voter has already voted"]
            return returnValues
        }

        Vote vote = [candidate:Candidate.read(cmd.candidateId), voterSsn:user.password]
        if(!vote.validate())
        {
            log.warn("Failed to validate vote-object, failing vote operation.")
            def returnValues = ["success":false, message:"Saving the vote object failed"]
            return returnValues
        }

        vote.save([flush:true])
        def returnValues = ["success":true, message:"Vote accepted", vote:vote]
        log.info("Voting succeeded.")
        return returnValues
    }
}
