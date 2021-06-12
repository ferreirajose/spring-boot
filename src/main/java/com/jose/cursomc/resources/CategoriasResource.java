package com.jose.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.dto.CategoriaDTO;
import com.jose.cursomc.services.CategoriaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(
		@RequestParam(value = "page", defaultValue = "0")
		Integer page,

		@RequestParam(value = "itensPerPage", defaultValue = "24")
		Integer itensPerPage,

		@RequestParam(value = "orderBy", defaultValue = "nome")
		String orderBy,

		@RequestParam(value = "direction", defaultValue = "ASC")
		String direction
	) {
		Page<Categoria> list = service.findPage(page, itensPerPage, orderBy, direction);
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {
		List<Categoria> list = service.findAll();

		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(listDto);
	}


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
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);

		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDto, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDto);

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
