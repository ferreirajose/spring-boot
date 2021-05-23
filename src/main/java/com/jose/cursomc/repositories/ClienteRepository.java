package com.jose.cursomc.repositories;

import com.jose.cursomc.domain.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    /** 
     * ClienteRepository é responsavel pelo acesso as dados realizando operações com base de dados ou consumir API, caso
    * a aplicaão se comunicase com outros sistemas 
    */ 

}
