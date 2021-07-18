package com.jose.cursomc.resources;

import java.util.List;

import com.jose.cursomc.domain.Produto;
import com.jose.cursomc.dto.ProdutoDTO;
import com.jose.cursomc.resources.utils.URL;
import com.jose.cursomc.services.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

    @Autowired
    private ProdutoService service;

    @RequestMapping(value="path", method=RequestMethod.GET)
    public ResponseEntity<Produto> find(@PathVariable Integer id) {
        Produto obj = service.buscar(id);
        return ResponseEntity.ok().body(obj);
    }

    @RequestMapping(value="/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findPage(
        @RequestParam(value = "nome", defaultValue = "") String nome,	
        @RequestParam(value = "categorias", defaultValue = "") String categorias,	
        @RequestParam(value = "page", defaultValue = "0") Integer page,
		@RequestParam(value = "itensPerPage", defaultValue = "24") Integer itensPerPage,
		@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
		@RequestParam(value = "direction", defaultValue = "ASC") String direction
	) {
        List<Integer> ids = URL.decodeIntList(categorias);
        String nomeDecode = URL.decodeParam(nome);
		Page<Produto> list = service.search(nomeDecode, ids, page, itensPerPage, orderBy, direction);
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj));

		return ResponseEntity.ok().body(listDto);
	}
    
}
