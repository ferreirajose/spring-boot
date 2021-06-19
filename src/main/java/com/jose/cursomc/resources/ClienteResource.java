package com.jose.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.dto.ClienteDTO;
import com.jose.cursomc.dto.ClienteNewDto;
import com.jose.cursomc.services.ClienteService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.bind.annotation.RequestParam;


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


	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(
		@RequestParam(value = "page", defaultValue = "0")
		Integer page,

		@RequestParam(value = "itensPerPage", defaultValue = "24")
		Integer itensPerPage,

		@RequestParam(value = "orderBy", defaultValue = "nome")
		String orderBy,

		@RequestParam(value = "direction", defaultValue = "ASC")
		String direction
	) {
		Page<Cliente> list = service.findPage(page, itensPerPage, orderBy, direction);
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {
		List<Cliente> list = service.findAll();

		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}


	// Mapeando verbo HTTP
	@RequestMapping(value="/{id}", method=RequestMethod.GET)

	/** 
	 * ResponseEntity<?> encapsula um retorno HTTP e o ? significa qualquer coisa, seria algo semelhante ao
	 * any no typescrip
	 * */ 
	
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente cli = service.find(id);
		return ResponseEntity.ok().body(cli);
	}

	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDto objDto) {
		Cliente obj = service.fromDTO(objDto);

		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}
	

	/**
	* esse MissingServletRequestParameterException aconteceu pois tinha RequestParam e RequestBody, já que é put a informação vem no body
	* não na URL
	*/ 
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDto);
		
		obj.setId(id);
		obj = service.update(obj);
		
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		
		service.remove(id);

		return ResponseEntity.noContent().build();

	}
	
	
}
