package voting.backend.grails3

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority

class TupasAuthenticationToken extends AbstractAuthenticationToken{

    private Object principal
    private Object credentials

    String B02K_VERS
    String B02K_TIMESTMP
    String B02K_IDNBR
    String B02K_STAMP
    String B02K_CUSTNAME
    String B02K_KEYVERS
    String B02K_ALG
    String B02K_CUSTID
    String B02K_CUSTTYPE
    String B02K_USERID
    String B02K_USERNAME
    String B02K_MAC

    TupasAuthenticationToken(Collection<? extends GrantedAuthority> authorities, String b02K_VERS, String b02K_TIMESTMP, String b02K_IDNBR, String b02K_STAMP, String b02K_CUSTNAME, String b02K_KEYVERS, String b02K_ALG, String b02K_CUSTID, String b02K_CUSTTYPE, String b02K_USERID, String b02K_USERNAME, String b02K_MAC) {
        super(authorities)
        B02K_VERS = b02K_VERS
        B02K_TIMESTMP = b02K_TIMESTMP
        B02K_IDNBR = b02K_IDNBR
        B02K_STAMP = b02K_STAMP
        B02K_CUSTNAME = b02K_CUSTNAME
        B02K_KEYVERS = b02K_KEYVERS
        B02K_ALG = b02K_ALG
        B02K_CUSTID = b02K_CUSTID
        B02K_CUSTTYPE = b02K_CUSTTYPE
        B02K_USERID = b02K_USERID
        B02K_USERNAME = b02K_USERNAME
        B02K_MAC = b02K_MAC
    }

    TupasAuthenticationToken(Object principal, Object credentials) {
        super(null)
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    @Override
    Object getCredentials() {
        return null
    }

    @Override
    Object getPrincipal() {
        return null
    }
}
