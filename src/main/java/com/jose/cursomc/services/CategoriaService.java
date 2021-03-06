package com.jose.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.jose.cursomc.domain.Categoria;
import com.jose.cursomc.dto.CategoriaDTO;
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
        Categoria newObj = find(obj.getId());  // verificar se o id informa existe
        updateDate(newObj, obj);

        return repo.save(newObj);
    }

    public void remove(Integer id) {
        try {
            find(id); // verificar se o id informado existe
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir uma categoria que possue produtos");   
        }
    }

    public Page<Categoria> findPage(Integer page, Integer itensPerPage, String orderBy, String direction) {
        
        PageRequest pageRequest = PageRequest.of(page, itensPerPage, Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }

    // metodo auxiliar para que recebe um CategoriaDTO e retorna uma instancia de Categoria
    public Categoria fromDTO(CategoriaDTO objDto) {
        return new Categoria(objDto.getId(), objDto.getNome());
    }
    
    /**
     * 
     * @param newObj
     * @param obj
     * 
     * metodo auxiliar que salvar um objeto do tipo Categoria, no fromDTO() do CategoriaResource, passamos um obj 
     * do tipo CategoriaDTO e retornamos um Categoria() sendo que os paramtros cpf e tipoClintes passamos null, 
     * esse metodo ira atualizar apenas o nome e email sem alterar o cpf e tipoClintes atuais
    */
    private void updateDate(Categoria newObj, Categoria obj) {
        newObj.setNome(obj.getNome());
    }
}
