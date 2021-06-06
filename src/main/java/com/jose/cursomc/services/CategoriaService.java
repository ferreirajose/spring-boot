package com.jose.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.repositories.CategoriaRepository;
import com.jose.cursomc.services.exceptions.DataIntegrityException;
import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
    /** 
     * @Autowired instancia automaticamento a CategoriaRepository utilizando injeção de depencias ou inversão de controle
     * ps: pequisar sobre inversão de controle
     * */ 
    @Autowired
    private CategoriaRepository repo;

    public List<Categoria> findAll() {

        return repo.findAll();
    }

    public Categoria find(Integer id) {

        /** 
         * Optional foi add na versão 8 do JAVA, Antes era
         * public Categoria find(Integer id) {
         *   Categoria obj = repo.findOne(id);
         *   return obj;
         *  }
         * Optional foi add para resolver o problema null point exception, pois caso não tenha valor o retorno sera NULL
        */
        // Optional<Categoria> obj = repo.findById(id); 
        // return obj.orElse(null); // caso não econtre o valor informado retorna NULL

        Optional<Categoria> obj = repo.findById(id); 

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado com Id: " + id + ", Tipo: " + Categoria.class.getName()));
        
    }


    public Categoria insert(Categoria obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Categoria update(Categoria obj) {
        find(obj.getId());  // verificar se o id informa existe
        return repo.save(obj);
    }

    public void remove(Integer id) {
        try {
            find(id); // verificar se o id informa existe
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possue produtos");   
        }
    }
}
