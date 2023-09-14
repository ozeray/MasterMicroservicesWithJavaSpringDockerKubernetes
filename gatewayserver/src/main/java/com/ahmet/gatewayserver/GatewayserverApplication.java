package com.ahmet.gatewayserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.filter.factory.TokenRelayGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	// Responsible for taking the token from auth server and appending it to the request to be forwarded to the resource server.
	// Added for authorization code grant flow.
	@Autowired
	private TokenRelayGatewayFilterFactory filterFactory;

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/ahmet/accounts/**")
//						.filters(f -> f
						.filters(f -> f.filters(filterFactory.apply()) 	// Added for authorization code grant flow.
								.rewritePath("/ahmet/accounts/(?<segment>.*)" , "/${segment}")
//								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
								.removeRequestHeader("Cookie")) 	// Added for authorization code grant flow.
																			// Protects the resource server from cookies, which are not needed there.
						.uri("lb://accounts"))
				.route(p -> p
						.path("/ahmet/loans/**")
						.filters(f -> f.filters(filterFactory.apply())
								.rewritePath("/ahmet/loans/(?<segment>.*)" , "/${segment}")
								.removeRequestHeader("Cookie"))
//								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://loans"))
				.route(p -> p
						.path("/ahmet/cards/**")
						.filters(f -> f.filters(filterFactory.apply())
								.rewritePath("/ahmet/cards/(?<segment>.*)" , "/${segment}")
								.removeRequestHeader("Cookie"))
//								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://cards"))
				.build();

	}

}
