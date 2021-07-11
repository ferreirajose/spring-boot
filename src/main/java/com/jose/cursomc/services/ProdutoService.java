package com.jose.cursomc.services;

import java.util.List;
import java.util.Optional;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.domain.Produto;
import com.jose.cursomc.repositories.CategoriaRepository;
import com.jose.cursomc.repositories.ProdutoRepository;
import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    @Autowired
    private CategoriaRepository categoriaRepository;

    public Produto buscar(Integer id) {        
        Optional<Produto> obj = repo.findById(id); 

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado com Id: " + id + ", Produto: " + Produto.class.getName()));
    }

    public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer itensPerPage, String orderBy, String direction) {
        
        PageRequest pageRequest = PageRequest.of(page, itensPerPage, Direction.valueOf(direction), orderBy);
        List<Categoria> categorias = categoriaRepository.findAllById(ids);
        return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
        
    }

}
