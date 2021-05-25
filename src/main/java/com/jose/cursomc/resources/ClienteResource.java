package com.jose.cursomc.resources;

import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.services.ClienteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController // Mapeando class  controller
@RequestMapping(value="/clientes") // Mapendo rota para acesssar 
public class ClienteResource {

	/** 
     * @Autowired instancia automaticamento a ClienteRepository utilizando inverja de depencias
     * ps: pequisar sobre inverja de depencias
	 * 
	 * o controlador ClientesResource esta utilizando o ClienteService que por sua vez esta utilizado o ClienteRepository responsavel por fazer
	 * consultas em base de dados ou consumir api externa
     * */ 
	@Autowired
	private ClienteService  service;

	// Mapeando verbo HTTP
	@RequestMapping(value="/{id}", method=RequestMethod.GET)

	/** 
	 * ResponseEntity<?> encapsula um retorno HTTP e o ? significa qualquer coisa, seria algo semelhante ao
	 * any no typescrip
	 * */ 
	
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Cliente cli = service.buscar(id);

		return ResponseEntity.ok().body(cli);

	
	}
}
