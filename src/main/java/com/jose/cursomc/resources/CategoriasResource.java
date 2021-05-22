package com.jose.cursomc.resources;

import java.util.ArrayList;
import java.util.List;

import com.jose.cursomc.domain.Categoria;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController // Mapeando class  controller
@RequestMapping(value="/categorias") // Mapendo rota para acesssar 
public class CategoriasResource {
	// Mapeando verbo HTTP
	@RequestMapping(method=RequestMethod.GET)
	public List<Categoria> listar() {
		
		Categoria cat1 = new Categoria(1, "Informatica");
		Categoria cat2 = new Categoria(2, "Escritório");

		List<Categoria> lista = new ArrayList<>();

		lista.add(cat1);
		lista.add(cat2);
		
		return lista;
	}
}
