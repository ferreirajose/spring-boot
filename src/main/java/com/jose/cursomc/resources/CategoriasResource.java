package com.jose.cursomc.resources;

import java.net.URI;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController // Mapeando class  controller
@RequestMapping(value="/categorias") // Mapendo rota para acesssar 
public class CategoriasResource {

	/** 
     * @Autowired instancia automaticamento a CategoriaRepository utilizando inverja de depencias
     * ps: pequisar sobre inverja de depencias
	 * 
	 * o controlador CategoriasResource esta utilizando o CategoriaService que por sua vez esta utilizado o CategoriaRepository responsavel por fazer
	 * consultas em base de dados ou consumir api externa
     * */ 
	@Autowired
	private CategoriaService  service;

	// Mapeando verbo HTTP
	@RequestMapping(value="/{id}", method=RequestMethod.GET)

	/** 
	 * ResponseEntity<?> encapsula um retorno HTTP e o ? significa qualquer coisa, seria algo semelhante ao
	 * any no typescrip
	 * */ 
	
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		
		Categoria cat = service.find(id);

		return ResponseEntity.ok().body(cat);
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		
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
