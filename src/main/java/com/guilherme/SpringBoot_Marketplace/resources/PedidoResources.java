package com.guilherme.SpringBoot_Marketplace.resources;


import com.guilherme.SpringBoot_Marketplace.domain.Pedido;
import com.guilherme.SpringBoot_Marketplace.services.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/pedidos") // encaminha para parte categoria do código
public class PedidoResources {

    @Autowired
    private PedidoService service;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)


    public ResponseEntity<Pedido>find(@PathVariable Integer id) {

        Pedido obj = service.find(id); // procura o id a ser mostrada
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(method = RequestMethod.POST) //RequestBody faz o json ser convertido para objeto java
    public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj) { //@Valid serve para validação do objeto, para se fazer um filtro do que deve ser colocado
        obj = service.insert(obj);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();  // metodo para inserir novas categoria! = POST
    }

}
