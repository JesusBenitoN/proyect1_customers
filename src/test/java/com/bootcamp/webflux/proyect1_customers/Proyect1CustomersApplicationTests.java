package com.bootcamp.webflux.proyect1_customers;

import java.util.Collections;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeCustomer;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeDocument;
import com.bootcamp.webflux.proyect1_customers.models.services.CustomersService;

import reactor.core.publisher.Mono;
//para el mock
//@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Proyect1CustomersApplicationTests {

	@Autowired
	private WebTestClient client;
		
	@Autowired
	private CustomersService service;
		
	@SuppressWarnings("deprecation")
	@Test
	void listTest() {
		
		client.get()
		.uri("api/customers")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Customers.class)
		.consumeWith(response -> {
			List<Customers> customers = response.getResponseBody();
			customers.forEach(c -> {
				System.out.print(c.getName());
			});
			Assertions.assertThat(customers.size()>0).isTrue();
		});
//		.hasSize(2);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void seeTest() {
		
		Customers customers = service.findByName("Laive").block();
		
		client.get()
		.uri("api/customers/{id}", Collections.singletonMap("id", customers.getId()))
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		//cuando el body es un objeto
		.expectBody(Customers.class)
		.consumeWith(response -> {
			Customers c = response.getResponseBody();
			Assertions.assertThat(c.getId()).isNotEmpty();
			Assertions.assertThat(c.getName()).isEqualTo("Laive");
		});
		//cuando el body es un json
		/*.expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.name").isEqualTo("Gloria");*/
	}
	
	@SuppressWarnings("deprecation")
	@Test
	void createTest() {
		
		TypeCustomer typeCustomer = service.findTypeCustomerByName("Personal").block();
		TypeDocument typeDocument = service.findTypeDocumentByName("DNI").block();
		
		Customers customers = new Customers("Luis Felipe", typeCustomer, typeDocument, "09805072");
		client.post()
		.uri("api/customers")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(customers), Customers.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody(Customers.class)
		.consumeWith(response -> {
			Customers c = response.getResponseBody();
			Assertions.assertThat(c.getId()).isNotEmpty();
			Assertions.assertThat(c.getTypeCustomer().getName()).isEqualTo("Personal");
			Assertions.assertThat(c.getTypeDocument().getName()).isEqualTo("DNI");
			Assertions.assertThat(c.getNumbDocument()).isEqualTo("09805072");
			Assertions.assertThat(c.getNumbDocument()).isNotEmpty();
		});
		/*.expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.name").isEqualTo("Luis Felipe")
		.jsonPath("$.typeCustomer.name").isEqualTo("Personal")
		.jsonPath("$.typeDocument.name").isEqualTo("DNI")
		.jsonPath("$.numbDocument").isEqualTo("09805072")
		.jsonPath("$.numbDocument").isNotEmpty();*/
	}

	@SuppressWarnings("deprecation")
	@Test
	void editTest() {
		Customers customers = service.findByName("Jesus Benito").block();
		
		TypeCustomer typeCustomer = service.findTypeCustomerByName("Business").block();
		TypeDocument typeDocument = service.findTypeDocumentByName("RUC").block();
		
		Customers customersEdit = new Customers("A y N", typeCustomer, typeDocument, "20509805072");
		client.put()
		.uri("api/customers/{id}", Collections.singletonMap("id", customers.getId()))
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(Mono.just(customersEdit), Customers.class)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBody(Customers.class)
		.consumeWith(response -> {
			Customers c = response.getResponseBody();
			Assertions.assertThat(c.getId()).isNotEmpty();
			Assertions.assertThat(c.getTypeCustomer().getName()).isEqualTo("Business");
			Assertions.assertThat(c.getTypeDocument().getName()).isEqualTo("RUC");
			Assertions.assertThat(c.getNumbDocument()).isEqualTo("20509805072");
			Assertions.assertThat(c.getNumbDocument()).isNotEmpty();
		});
		/*.expectBody()
		.jsonPath("$.id").isNotEmpty()
		.jsonPath("$.name").isEqualTo("A y N")
		.jsonPath("$.typeCustomer.name").isEqualTo("Business")
		.jsonPath("$.typeDocument.name").isEqualTo("RUC")
		.jsonPath("$.numbDocument").isNotEmpty()
		.jsonPath("$.numbDocument").isEqualTo("20509805072");*/
	}
	
	@Test
	void deleteTest() {
		Customers customers = service.findByName("Gloria").block();
		
		client.delete()
		.uri("api/customers/{id}", Collections.singletonMap("id", customers.getId()))
		.exchange()
		.expectStatus().isNoContent()
		.expectBody()
		.isEmpty();
		
	}
}
