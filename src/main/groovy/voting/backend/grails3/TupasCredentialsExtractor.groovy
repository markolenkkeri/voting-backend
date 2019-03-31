package voting.backend.grails3

import com.google.common.io.CharStreams
import groovy.json.JsonSlurper
import groovy.util.logging.Slf4j
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority

import javax.servlet.http.HttpServletRequest

@Slf4j
class TupasCredentialsExtractor {
    public static final String ROLE_NAME = "ROLE_VOTER";
    public static final GrantedAuthority ROLE = new SimpleGrantedAuthority(ROLE_NAME);
    public static final List<GrantedAuthority> ROLES = Collections.singletonList(ROLE);

    TupasAuthenticationToken extractCredentials(HttpServletRequest httpServletRequest) {
        def jsonBody = getJsonBody(httpServletRequest)

        if (jsonBody) {
            String B02K_VERS = jsonBody.B02K_VERS
            String B02K_TIMESTMP = jsonBody.B02K_TIMESTMP
            String B02K_IDNBR = jsonBody.B02K_IDNBR
            String B02K_STAMP = jsonBody.B02K_STAMP
            String B02K_CUSTNAME = jsonBody.B02K_CUSTNAME
            String B02K_KEYVERS = jsonBody.B02K_KEYVERS
            String B02K_ALG = jsonBody.B02K_ALG
            String B02K_CUSTID = jsonBody.B02K_CUSTID
            String B02K_CUSTTYPE = jsonBody.B02K_CUSTTYPE
            String B02K_USERID = jsonBody.B02K_USERID
            String B02K_USERNAME = jsonBody.B02K_USERNAME
            String B02K_MAC = jsonBody.B02K_MAC

            log.debug "Extracted credentials from JSON payload."

            new TupasAuthenticationToken(ROLES, B02K_VERS, B02K_TIMESTMP, B02K_IDNBR, B02K_STAMP, B02K_CUSTNAME, B02K_KEYVERS,
                    B02K_ALG, B02K_CUSTID, B02K_CUSTTYPE, B02K_USERID, B02K_USERNAME, B02K_MAC)
        } else {
            log.error "No JSON body sent in the request"
            return null
        }
    }

    static Object getJsonBody(HttpServletRequest httpServletRequest) {
        try {
            String body = CharStreams.toString(httpServletRequest.reader)
            JsonSlurper slurper = new JsonSlurper()
            slurper.parseText(body)
        } catch (ex) {
            log.error("Failed to parse credentials from JSON body", ex)
            [:]
        }
    }

}
