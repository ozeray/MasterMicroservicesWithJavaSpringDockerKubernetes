package com.ahmet.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayserverApplication.class, args);
	}

	@Bean
	public RouteLocator myRoutes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(p -> p
						.path("/ahmet/accounts/**")
						.filters(f -> f.rewritePath("/ahmet/accounts/(?<segment>.*)" , "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://accounts"))
				.route(p -> p
						.path("/ahmet/loans/**")
						.filters(f -> f.rewritePath("/ahmet/loans/(?<segment>.*)" , "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://loans"))
				.route(p -> p
						.path("/ahmet/cards/**")
						.filters(f -> f.rewritePath("/ahmet/cards/(?<segment>.*)" , "/${segment}")
								.addResponseHeader("X-Response-Time", LocalDateTime.now().toString()))
						.uri("lb://cards"))
				.build();

	}

}
