package com.jose.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import com.jose.cursomc.domain.Pedido;
import com.jose.cursomc.repositories.PedidoRepository;
import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
    /** 
     * @Autowired instancia automaticamento a PedidoRepository utilizando injeção de depencias ou inversão de controle
     * ps: pequisar sobre inversão de controle
     * */ 
    @Autowired
    private PedidoRepository repo;

    public Pedido buscar(Integer id) {

        /** 
         * Optional foi add na versão 8 do JAVA, Antes era
         * public Pedido find(Integer id) {
         *   Pedido obj = repo.findOne(id);
         *   return obj;
         *  }
         * Optional foi add para resolver o problema null point exception, pois caso não tenha valor o retorno sera NULL
        */
        // Optional<Pedido> obj = repo.findById(id); 
        // return obj.orElse(null); // caso não econtre o valor informado retorna NULL

        Optional<Pedido> obj = repo.findById(id); 

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado com Id: " + id + ", Tipo: " + Pedido.class.getName()));
        
    }

}
