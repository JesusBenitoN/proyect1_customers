package com.bootcamp.webflux.proyect1_customers.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;
import com.bootcamp.webflux.proyect1_customers.models.services.CustomersService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/customers")
public class CustomersRestController {
	
	@Autowired
	private CustomersService service;

	
	@GetMapping
	public Mono<ResponseEntity<Flux<Customers>>> list(){
		return Mono.just(
				ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(service.findAllCustomer())
				);
	}
	
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Customers>> seeDetail(@PathVariable String id){
		return service.findCustomerById(id).map(c -> ResponseEntity.ok(c)
				/*.contentType(MediaType.APPLICATION_JSON)
				.body(c)*/).defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	
	@PostMapping
	public Mono<ResponseEntity<Customers>> create (@Valid @RequestBody Customers customers/*Mono<Customers> monoCustomers*/){
		
			return service.save(customers).map(c -> {
				return ResponseEntity
					.created(URI.create("/api/customers/" .concat(c.getId())))
					.contentType(MediaType.APPLICATION_JSON)
					.body(c);
			});

	}
	
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Customers>> edit (@RequestBody Customers customers, @PathVariable String id){
		return service.findCustomerById(id).flatMap(c -> {
			c.setName(customers.getName());
			c.setTypeCustomer(customers.getTypeCustomer());
			c.setTypeDocument(customers.getTypeDocument());
			c.setNumbDocument(customers.getNumbDocument());
			return service.save(c);
			}).map( c -> 
				ResponseEntity.created(URI.create("api/customers/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON)
				.body(c)).defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> delete (@PathVariable String id){
		return service.findCustomerById(id).flatMap(c -> {
			return service.delete(c).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NOT_FOUND));
	}
}
