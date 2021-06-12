package com.jose.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.dto.ClienteDTO;
import com.jose.cursomc.repositories.ClienteRepository;
import com.jose.cursomc.services.exceptions.DataIntegrityException;
import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
    /** 
     * @Autowired instancia automaticamento a ClienteRepository utilizando injeção de depencias ou inversão de controle
     * ps: pequisar sobre inversão de controle
     * */ 
    @Autowired
    private ClienteRepository repo;

    public Cliente find(Integer id) {

        /** 
         * Optional foi add na versão 8 do JAVA, Antes era
         * public Cliente find(Integer id) {
         *   Cliente obj = repo.findOne(id);
         *   return obj;
         *  }
         * Optional foi add para resolver o problema null point exception, pois caso não tenha valor o retorno sera NULL
        */
        // Optional<Cliente> obj = repo.findById(id); 
        // return obj.orElse(null); // caso não econtre o valor informado retorna NULL

        Optional<Cliente> obj = repo.findById(id); 

        return obj.orElseThrow(() -> new ObjectNotFoundException(
                                "Objeto não encontrado com Id: " + id + ", Tipo: " + Cliente.class.getName()));
        
    }

    public List<Cliente> findAll() {

        return repo.findAll();
    }


    public Cliente insert(Cliente obj) {
        obj.setId(null);
        return repo.save(obj);
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());  // verificar se o id informa existe
        updateDate(newObj, obj);

        return repo.save(newObj);
    }

    public void remove(Integer id) {
        try {
            find(id); // verificar se o id informa existe
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possivel excluir uma Cliente que possui produtos");   
        }
    }

    public Page<Cliente> findPage(Integer page, Integer itensPerPage, String orderBy, String direction) {
        
        PageRequest pageRequest = PageRequest.of(page, itensPerPage, Direction.valueOf(direction), orderBy);

        return repo.findAll(pageRequest);
    }

    // metodo auxiliar para que recebe um ClienteDTO e retorna uma instancia de Cliente
    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
    }

    /**
     * 
     * @param newObj
     * @param obj
     * 
     * metodo auxiliar que salvar um objeto do tipo Cliente, no fromDTO() do ClientResource, passamos um obj 
     * do tipo ClienteDTO e retornamos um Cliente() sendo que os paramtros cpf e tipoClintes passamos null, 
     * esse metodo ira atualizar apenas o nome e email sem alterar o cpf e tipoClintes atuais
    */
    private void updateDate(Cliente newObj, Cliente obj) {
        newObj.setNome(obj.getNome());
        newObj.setEmail(obj.getEmail());
    }

}
