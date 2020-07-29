package com.bootcamp.webflux.proyect1_customers.models.documents;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="customers")
public class Customers {
	
	@Id
	private String id;
	
	@NotEmpty
	private String name;
	
	@Valid
	@NotNull
	private TypeCustomer typeCustomer;
	
	@Valid
	@NotNull
	private TypeDocument typeDocument;
	
	@NotEmpty
	private String numbDocument;
	
	public Customers(String name, TypeCustomer typeCustomer, TypeDocument typeDocument, String numbDocument) {
		this.name = name;
		this.typeCustomer = typeCustomer;
		this.typeDocument = typeDocument;
		this.numbDocument = numbDocument;
	}

	public Customers() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TypeCustomer getTypeCustomer() {
		return typeCustomer;
	}

	public void setTypeCustomer(TypeCustomer typeCustomer) {
		this.typeCustomer = typeCustomer;
	}

	public TypeDocument getTypeDocument() {
		return typeDocument;
	}

	public void setTypeDocument(TypeDocument typeDocument) {
		this.typeDocument = typeDocument;
	}

	public String getNumbDocument() {
		return numbDocument;
	}

	public void setNumbDocument(String numbDocument) {
		this.numbDocument = numbDocument;
	}
	
}
