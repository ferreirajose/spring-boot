package com.jose.cursomc.repositories;

import com.jose.cursomc.domain.Categoria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
    /** 
     * CategoriaRepository é responsavel pelo acesso as dados realizando operações com base de dados ou consumir API, caso
    * a aplicaão se comunicase com outros sistemas 
    */ 

}
