package com.jose.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.repositories.ClienteRepository;
import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    /** 
     * @Autowired instancia automaticamento a ClienteRepository utilizando injeção de depencias ou inversão de controle
     * ps: pequisar sobre inversão de controle
     * */ 
    @Autowired
    private ClienteRepository repo;

    public Cliente buscar(Integer id) {

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

        Optional<Cliente> obj = repo.findById(id); 

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado com Id: " + id + ", Tipo: " + Cliente.class.getName()));
        
    }

}
