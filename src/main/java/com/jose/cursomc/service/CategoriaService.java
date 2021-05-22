package com.jose.cursomc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.repositories.CategoriaRepository;

@Service
public class CategoriaService {
    /** 
     * @Autowired instancia automaticamento a CategoriaRepository utilizando injeção de depencias ou inversão de controle
     * ps: pequisar sobre inversão de controle
     * */ 
    @Autowired
    private CategoriaRepository repo;

    public Categoria buscar(Integer id) {
        Categoria obj = repo.findById(id).orElse(null); // caso não econtre o valor informado retorna NULL
        return obj;
    }

}
