package com.guilherme.SpringBoot_Marketplace.resources;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.guilherme.SpringBoot_Marketplace.domain.Categoria;
import com.guilherme.SpringBoot_Marketplace.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // encaminha para parte categoria do código
public class CategoriaResources {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	
	
	public ResponseEntity<?> find(@PathVariable Integer id) {
	
		Categoria obj = service.consultar(id); // procura o id a ser mostrada
	
	
		return ResponseEntity.ok().body(obj);
	}
}
