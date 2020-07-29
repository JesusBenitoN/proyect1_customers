package com.bootcamp.webflux.proyect1_customers;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp.webflux.proyect1_customers.handler.CustomersHandler;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
//para que se aplique a todos los metodos del request
import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterFunctionConfig {
	
	@Bean
	public RouterFunction<ServerResponse> routes(CustomersHandler handler){
		
		return route(GET("/api/v2/customers"), request -> handler.list(request))
				.andRoute(GET("/api/v2/customers/{id}"), handler::see)
				.andRoute(POST("/api/v2/customers"), handler::create)
				.andRoute(PUT("/api/v2/customers/{id}"), handler::edit)
				.andRoute(DELETE("/api/v2/customers/{id}"), handler::delete);
	}

}
