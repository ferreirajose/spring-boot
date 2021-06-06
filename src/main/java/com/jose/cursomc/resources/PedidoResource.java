package com.jose.cursomc.resources;

import com.jose.cursomc.domain.Pedido;
import com.jose.cursomc.services.PedidoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController // Mapeando class  controller
@RequestMapping(value="/pedidos") // Mapendo rota para acesssar 
public class PedidoResource {

	/** 
     * @Autowired instancia automaticamento a CategoriaRepository utilizando inverja de depencias
     * ps: pequisar sobre inverja de depencias
	 * 
	 * o controlador PedidoResource esta utilizando o Pedidoervice que por sua vez esta utilizado o CategoriaRepository responsavel por fazer
	 * consultas em base de dados ou consumir api externa
     * */ 
	@Autowired
	private PedidoService  service;

	// Mapeando verbo HTTP
	@RequestMapping(value="/{id}", method=RequestMethod.GET)

	/** 
	 * ResponseEntity<?> encapsula um retorno HTTP e o ? significa qualquer coisa, seria algo semelhante ao
	 * any no typescrip
	 * */ 
	
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Pedido ped = service.buscar(id);

		return ResponseEntity.ok().body(ped);

	
	}
}
