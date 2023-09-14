package com.ahmet.gatewayserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity // Spring Gateway is built with Spring Reactive module, so use this annotation
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityWebFilterChain(ServerHttpSecurity http) {
//        Was used for client credentials grant flow during the course
//        http.authorizeExchange(exchanges -> exchanges.pathMatchers("/ahmet/accounts/**").hasRole("ACCOUNTS")
        http.authorizeExchange(exchanges -> exchanges.pathMatchers("/ahmet/accounts/**").authenticated()
                        .pathMatchers("/ahmet/cards/**").authenticated()
                        .pathMatchers("/ahmet/loans/**").permitAll())
                .oauth2Login(Customizer.withDefaults()) // Automatically authenticates the user and fills the principal with the token from auth. server
    //        Was used for client credentials grant flow during the course
  //            .oauth2ResourceServer(server -> server.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())))
            .csrf(ServerHttpSecurity.CsrfSpec::disable); // No browser involved here
        return http.build();
    }

//    Was used for client credentials grant flow during the course
//    private Converter<Jwt, Mono<AbstractAuthenticationToken>> jwtAuthenticationConverter() {
//        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
//        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
//        return new ReactiveJwtAuthenticationConverterAdapter(jwtAuthenticationConverter);
//    }
}
