package voting.backend.grails3

import groovy.util.logging.Slf4j
import org.apache.commons.codec.digest.DigestUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

@Slf4j
class TupasAuthenticationProvider implements AuthenticationProvider {

    @Value('${tupas.key}')
    String tupasKey

    @Override
    Authentication authenticate(Authentication authentication) throws AuthenticationException {
        TupasAuthenticationToken authToken = authentication as TupasAuthenticationToken
        String verificationString = "0002&${authToken.B02K_TIMESTMP}&${authToken.B02K_IDNBR}&${authToken.B02K_STAMP}&" +
                "${authToken.B02K_CUSTNAME}&${authToken.B02K_KEYVERS}&${authToken.B02K_ALG}&${authToken.B02K_CUSTID}&" +
                "${authToken.B02K_CUSTTYPE}&$tupasKey&"
        String verificationMac = DigestUtils.sha256Hex(verificationString).toUpperCase()
        authToken.authenticated = verificationMac == authToken.B02K_MAC
        return authToken
    }

    @Override
    boolean supports(Class<?> authentication) {
        TupasAuthenticationToken.class == authentication
    }
}
