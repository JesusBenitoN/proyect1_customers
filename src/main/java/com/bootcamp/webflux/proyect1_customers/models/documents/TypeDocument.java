package com.bootcamp.webflux.proyect1_customers.models.documents;

import javax.validation.constraints.NotEmpty;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="typeDocuments")
public class TypeDocument {
	
	@Id
	@NotEmpty
	private String id;
	
	private String name;
	
	public TypeDocument(String name) {
		this.name = name;
	}

	public TypeDocument() {
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
	
}
