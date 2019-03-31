tupas {
    key = "fake-checksum-key"
}

grails {
    plugin {
        springsecurity {
            providerNames = [
                    'tupasAuthenticationProvider',
                    'anonymousAuthenticationProvider',
                    'rememberMeAuthenticationProvider'
            ]
            filterNames = [
                    'securityContextPersistenceFilter',
                    'tupasAuthenticationFilter',
                    'exceptionTranslationFilter',
                    'restTokenValidationFilter',
                    'anonymousAuthenticationFilter',
                    'filterInvocationInterceptor'
            ]
            filterChain {
                chainMap = [
                        [pattern: '/candidate/**', filters: 'anonymousAuthenticationFilter'],
                        [pattern: '/dbconsole', filters: 'anonymousAuthenticationFilter'],
                        [pattern: '/tupas/**', filters: 'anonymousAuthenticationFilter'],
                        [pattern: '/api/login', filters: 'tupasAuthenticationFilter'],
                        [pattern: '/**/favicon.ico', filters: 'anonymousAuthenticationFilter'],
                        [pattern: '/voting/vote', filters: 'JOINED_FILTERS,-anonymousAuthenticationFilter'],
                        [pattern: '/**', filters: 'restTokenValidationFilter,anonymousAuthenticationFilter']
                ]
            }

            controllerAnnotations {
                staticRules = [
                        [pattern: '/', access: ['permitAll']],
                        [pattern: '/error', access: ['permitAll']],
                        [pattern: '/index', access: ['permitAll']],
                        [pattern: '/index.gsp', access: ['permitAll']],
                        [pattern: '/shutdown', access: ['permitAll']],
                        [pattern: '/assets/**', access: ['permitAll']],
                        [pattern: '/**/js/**', access: ['permitAll']],
                        [pattern: '/**/css/**', access: ['permitAll']],
                        [pattern: '/**/images/**', access: ['permitAll']],
                        [pattern: '/**/favicon.ico', access: ['permitAll']]
                ]
            }

            rest {
                token {
                    validation {
                        enableAnonymousAccess = true
                    }
                    storage {
                        jwt {
                            issuer = 'Marko ja Juho'
                            useEncryptedJwt = true
                            privateKeyPath = 'secret/voting_key.der'
                            publicKeyPath = 'secret/public_voting.der'
                            secret = 'asdfasdfasfdfasdfedsasfeasdasdfwasdasdasdas'
                        }
                    }
                }
            }
        }
    }
}