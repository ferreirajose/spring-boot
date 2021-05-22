package com.jose.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.service.CategoriaService;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
	
	public ResponseEntity<?> find(@PathVariable Integer id) {
		
		Categoria cat = service.buscar(id);

		return ResponseEntity.ok().body(cat);

	
	}
}
