package com.bootcamp.webflux.proyect1_customers.models.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.bootcamp.webflux.proyect1_customers.models.documents.TypeDocument;

import reactor.core.publisher.Mono;

public interface TypeDocumentDao extends ReactiveMongoRepository<TypeDocument, String>{
	
	public Mono<TypeDocument> findByName(String name);

}
