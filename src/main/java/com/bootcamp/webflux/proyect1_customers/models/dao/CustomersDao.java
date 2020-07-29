package com.bootcamp.webflux.proyect1_customers.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;

import reactor.core.publisher.Mono;

public interface CustomersDao extends ReactiveMongoRepository<Customers, String>{
	
	public Mono<Customers> findByName(String name);

}
