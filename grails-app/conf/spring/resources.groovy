import voting.backend.grails3.TupasAuthenticationFilter
import voting.backend.grails3.TupasAuthenticationProvider
import voting.backend.grails3.TupasCredentialsExtractor

// Place your Spring DSL code here
beans = {
    tupasAuthenticationProvider(TupasAuthenticationProvider)
    tupasCredentialsExtractor(TupasCredentialsExtractor)
    tupasAuthenticationFilter(TupasAuthenticationFilter) {
        authenticationManager = ref('authenticationManager')
        authenticationSuccessHandler = ref('restAuthenticationSuccessHandler')
        authenticationFailureHandler = ref('restAuthenticationFailureHandler')
        authenticationDetailsSource = ref('authenticationDetailsSource')
        tupasCredentialsExtractor = ref('tupasCredentialsExtractor')
        endpointUrl = "/api/login"
        tokenGenerator = ref('tokenGenerator')
        tokenStorageService = ref('tokenStorageService')
        authenticationEventPublisher = ref('authenticationEventPublisher')
    }
}
