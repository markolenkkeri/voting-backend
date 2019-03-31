package voting.backend.grails3

import grails.plugin.springsecurity.rest.authentication.RestAuthenticationEventPublisher
import grails.plugin.springsecurity.rest.credentials.CredentialsExtractor
import grails.plugin.springsecurity.rest.token.AccessToken
import grails.plugin.springsecurity.rest.token.generation.TokenGenerator
import grails.plugin.springsecurity.rest.token.storage.TokenStorageService
import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.springframework.security.authentication.AuthenticationDetailsSource
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.web.filter.GenericFilterBean

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Slf4j
@CompileStatic
class TupasAuthenticationFilter extends GenericFilterBean {
    String endpointUrl

    AuthenticationManager authenticationManager

    AuthenticationSuccessHandler authenticationSuccessHandler
    AuthenticationFailureHandler authenticationFailureHandler
    RestAuthenticationEventPublisher authenticationEventPublisher

    AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource

    TokenGenerator tokenGenerator
    TokenStorageService tokenStorageService

    TupasCredentialsExtractor tupasCredentialsExtractor

    @Override
    void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = request as HttpServletRequest
        HttpServletResponse httpServletResponse = response as HttpServletResponse

        String actualUri =  httpServletRequest.requestURI - httpServletRequest.contextPath

        logger.info "Actual URI is ${actualUri}; endpoint URL is ${endpointUrl}"
        //Only apply filter to the configured URL
        if (actualUri == endpointUrl) {
            log.debug "Applying authentication filter to this request"

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
            Authentication authenticationResult
            
            TupasAuthenticationToken authenticationRequest = tupasCredentialsExtractor.extractCredentials(httpServletRequest)

            boolean authenticationRequestIsCorrect = (authenticationRequest?.B02K_MAC && authenticationRequest?.B02K_IDNBR)

            if(authenticationRequestIsCorrect){
                authenticationRequest.details = authenticationDetailsSource.buildDetails(httpServletRequest)

                try {
                    log.debug "Trying to authenticate the request"
                    authenticationResult = authenticationManager.authenticate(authenticationRequest)

                    if (authenticationResult.authenticated) {
                        log.debug "Request authenticated. Storing the authentication result in the security context"
                        User user = new User(authenticationRequest.B02K_CUSTNAME, authenticationRequest.B02K_CUSTID, authenticationRequest.authorities)
                        AccessToken accessToken = tokenGenerator.generateAccessToken(user)

                        tokenStorageService.storeToken(accessToken.accessToken, user)
                        authenticationEventPublisher.publishTokenCreation(accessToken)
                        authenticationSuccessHandler.onAuthenticationSuccess(httpServletRequest, httpServletResponse, accessToken)
                        SecurityContextHolder.context.setAuthentication(accessToken)
                    } else {
                        log.debug "Not authenticated. Rest authentication token not generated."
                    }
                } catch (AuthenticationException ae) {
                    log.debug "Authentication failed: ${ae.message}"
                    authenticationFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, ae)
                }

            }else{
                log.debug "Username and/or password parameters are missing."
                if(!authentication){
                    log.debug "Setting status to ${HttpServletResponse.SC_BAD_REQUEST}"
                    httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST)
                }else{
                    log.debug "Using authentication already in security context."
                }
            }

        } else {
            chain.doFilter(request, response)
        }
    }
}
