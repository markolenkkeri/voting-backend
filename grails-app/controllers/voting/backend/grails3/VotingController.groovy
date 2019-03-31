package voting.backend.grails3

import grails.plugin.springsecurity.annotation.Secured
import grails.converters.*
import groovy.util.logging.Slf4j

@Slf4j
class VotingController {
	static responseFormats = ['json']

    def votingService

    @Secured('ROLE_VOTER')
    def vote(VoteCommand cmd) {
        if(!cmd?.validate())
        {
            def returnValues = ["success":false, message:"Command object validation failed", "errors":cmd.errors.allErrors]
            render returnValues as JSON
            return
        }

        render votingService.vote(cmd) as JSON
    }
}
