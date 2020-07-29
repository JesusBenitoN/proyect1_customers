package com.bootcamp.webflux.proyect1_customers.models.services;

import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeCustomer;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomersService {
	
	public Flux<Customers> findAllCustomer();		
	
	public Mono<Customers> findCustomerById( String id);
	
	public Mono<Customers> save(Customers customers);
	
	public Mono<Void> delete(Customers customers);
	
	public Flux<TypeDocument> findAllTypeDocuments();		
	
	public Mono<TypeDocument> findTypeDocumentById( String id);
	
	public Mono<TypeDocument> saveTypeDocument(TypeDocument typeDocument);
	
	public Flux<TypeCustomer> findAllTypeCustomer();		
	
	public Mono<TypeCustomer> findTypeCustomerById( String id);
	
	public Mono<TypeCustomer> saveTypeCustomer(TypeCustomer typeCustomer);
	
	public Mono<Customers> findByName(String name);
	
	public Mono<TypeCustomer> findTypeCustomerByName(String name);
	
	public Mono<TypeDocument> findTypeDocumentByName(String name);

}
