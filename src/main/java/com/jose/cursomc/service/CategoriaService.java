package com.jose.cursomc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        /** 
         * Optional foi add na versão 8 do JAVA, Antes era
         * public Categoria find(Integer id) {
         *   Categoria obj = repo.findOne(id);
         *   return obj;
         *  }
         * Optional foi add para resolver o problema null point exception, pois caso não tenha valor o retorno sera NULL
        */
        Optional<Categoria> obj = repo.findById(id); 
        return obj.orElse(null); // caso não econtre o valor informado retorna NULL
    }

}
