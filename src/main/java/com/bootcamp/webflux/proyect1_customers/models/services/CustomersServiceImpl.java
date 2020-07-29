package com.bootcamp.webflux.proyect1_customers.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bootcamp.webflux.proyect1_customers.models.dao.CustomersDao;
import com.bootcamp.webflux.proyect1_customers.models.dao.TypeCustomerDao;
import com.bootcamp.webflux.proyect1_customers.models.dao.TypeDocumentDao;
import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeCustomer;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeDocument;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomersServiceImpl implements CustomersService{
	
	@Autowired
	private CustomersDao daoCustomers;
	
	@Autowired
	private TypeCustomerDao daoTypeCustomer;
	
	@Autowired
	private TypeDocumentDao daoTypeDocument ;

	@Override
	public Flux<Customers> findAllCustomer() {
		// TODO Auto-generated method stub
		return daoCustomers.findAll();
	}

	@Override
	public Mono<Customers> findCustomerById(String id) {
		return daoCustomers.findById(id);
	}

	@Override
	public Mono<Customers> save(Customers customers) {
		return daoCustomers.save(customers);
	}

	@Override
	public Mono<Void> delete(Customers customers) {
		return daoCustomers.delete(customers);
	}

	@Override
	public Flux<TypeDocument> findAllTypeDocuments() {
		return daoTypeDocument.findAll();
	}

	@Override
	public Mono<TypeDocument> findTypeDocumentById(String id) {
		return daoTypeDocument.findById(id);
	}

	@Override
	public Mono<TypeDocument> saveTypeDocument(TypeDocument typeDocument) {
		return daoTypeDocument.save(typeDocument);
	}

	@Override
	public Flux<TypeCustomer> findAllTypeCustomer() {
		return daoTypeCustomer.findAll();
	}

	@Override
	public Mono<TypeCustomer> findTypeCustomerById(String id) {
		return daoTypeCustomer.findById(id);
	}

	@Override
	public Mono<TypeCustomer> saveTypeCustomer(TypeCustomer typeCustomer) {
		return daoTypeCustomer.save(typeCustomer);
	}

	@Override
	public Mono<Customers> findByName(String name) {
		return daoCustomers.findByName(name);
	}

	@Override
	public Mono<TypeCustomer> findTypeCustomerByName(String name) {
		return daoTypeCustomer.findByName(name);
	}

	@Override
	public Mono<TypeDocument> findTypeDocumentByName(String name) {
		return daoTypeDocument.findByName(name);
	}

	
}
