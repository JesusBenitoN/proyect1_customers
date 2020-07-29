package com.bootcamp.webflux.proyect1_customers.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static org.springframework.web.reactive.function.BodyInserters.*;

import java.net.URI;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.bootcamp.webflux.proyect1_customers.models.documents.Customers;
import com.bootcamp.webflux.proyect1_customers.models.services.CustomersService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class CustomersHandler {
	
	@Autowired
	private CustomersService service;
	
	@Autowired
	private Validator validator;
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> list(ServerRequest request){
		return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllCustomer(), Customers.class);
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> see(ServerRequest request){
		
		String id = request.pathVariable("id");
		return service.findCustomerById(id).flatMap(c -> ServerResponse
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(fromObject(c)))
				//en caso el id es vacio
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> create(ServerRequest request){
		
		//se puebla los datos del objeto en el mono customers
		Mono<Customers> customers = request.bodyToMono(Customers.class);
		
		//conervir el mono de customers en un objeto para guardar en db
		return customers.flatMap(c -> {
			Errors errors = new BeanPropertyBindingResult(c, Customers.class.getName());
			//validamos si hay error
			validator.validate(c, errors);
			if(errors.hasErrors()) {
				//majenamos el error
				//obtenemos la lista del error y convertimos a un iterable
				return Flux.fromIterable(errors.getFieldErrors())
						//lo modificamos a tipo String
						.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
						//convertimos a Mono list
						.collectList()
						//convertimos a Mono.serverResponse
						.flatMap(lista -> ServerResponse.badRequest().body(fromObject(lista)));
			}else {
				return service.save(c).flatMap(cdb -> ServerResponse
						.created(URI.create("/api/customers/".concat(cdb.getId())))
						.contentType(MediaType.APPLICATION_JSON_UTF8)
						//pasamos la lista al body para mostrar los errores al json
						.body(fromObject(cdb)));
			}
		});
	}
	
	@SuppressWarnings("deprecation")
	public Mono<ServerResponse> edit(ServerRequest request){
		Mono<Customers> customers = request.bodyToMono(Customers.class);
		String id = request.pathVariable("id");
		//se obtiene los datos de la db por id
		Mono<Customers> customersdb = service.findCustomerById(id);
		//combinando monos: customersdb y customers
		return customersdb.zipWith(customers, (db , req) -> {
			db.setName(req.getName());
			db.setTypeCustomer(req.getTypeCustomer());
			db.setTypeDocument(req.getTypeDocument());
			db.setNumbDocument(req.getNumbDocument());
			return db;
		})//convirtiendo a mono serverResponse
		.flatMap(c -> ServerResponse.created(URI.create("/api/customers/".concat(c.getId())))
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				//guardando los datos actualizados
				.body(service.save(c), Customers.class)
				//manejamos el error en caso el id sea vacio o no exista
				.switchIfEmpty(ServerResponse.notFound().build()));
	}
	
	public Mono<ServerResponse> delete(ServerRequest request){
		String id = request.pathVariable("id");
		
		//buscamos el customers en la db
		Mono<Customers> customersdb = service.findCustomerById(id);
		//si existe lo eliminamos - status 201 : no content
		return customersdb.flatMap(c -> service.delete(c).then(ServerResponse.noContent().build()))
				//manejamos el error en caso el id sea vacio o no exista - status 404 no found
				.switchIfEmpty(ServerResponse.notFound().build());
	}
}
