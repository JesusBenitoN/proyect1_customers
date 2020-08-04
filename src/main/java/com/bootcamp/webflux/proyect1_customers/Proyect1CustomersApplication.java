package com.bootcamp.webflux.proyect1_customers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;

import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeCustomer;
import com.bootcamp.webflux.proyect1_customers.models.documents.TypeDocument;
import com.bootcamp.webflux.proyect1_customers.models.services.CustomersService;

import reactor.core.publisher.Flux;

@EnableEurekaClient
@SpringBootApplication
public class Proyect1CustomersApplication implements CommandLineRunner {
	
	@Autowired
	private CustomersService service;
	
	@Autowired
	private ReactiveMongoTemplate mongoTemplate;
	
	private static final Logger log = LoggerFactory.getLogger(Proyect1CustomersApplication.class);
	

	public static void main(String[] args) {
		SpringApplication.run(Proyect1CustomersApplication.class, args);
		
	}

	@Override
	public void run(String... args) throws Exception {
		
		mongoTemplate.dropCollection("typeCustomers").subscribe();
		mongoTemplate.dropCollection("typeDocuments").subscribe();
		mongoTemplate.dropCollection("customers").subscribe();
		
		TypeCustomer personal = new TypeCustomer("Personal");
		TypeCustomer business = new TypeCustomer("Business");
		TypeDocument dni = new TypeDocument("DNI");
		TypeDocument ruc = new TypeDocument("RUC");
		
		Flux.just(personal,business).flatMap(tc -> service.saveTypeCustomer(tc))
		.doOnNext(c -> log.info("TypeCustomer creado: "+ c.getName()))
		.thenMany(Flux.just(dni, ruc).flatMap(td -> service.saveTypeDocument(td))
				.doOnNext(d -> log.info("TypeDocument creado: "+ d.getName()))
				).thenMany(
						Flux.just(new Customers("Jesus Benito", personal, dni, "10040305"),
								new Customers("Gloria", business, ruc, "20506050020"),
								new Customers("Laive", business, ruc, "20405008974"))
						.flatMap(customer -> {
							return service.save(customer);
						})
						).subscribe(customer -> log.info("Insert Customer: "+ customer.getName()));
	}

}
