package com.bootcamp.webflux.proyect1_customers.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.webflux.proyect1_customers.models.documents.TypeCustomer;

import reactor.core.publisher.Mono;

public interface TypeCustomerDao extends ReactiveMongoRepository<TypeCustomer, String>{
	
	public Mono<TypeCustomer> findByName(String name);

}
