package com.jose.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.dto.ClienteDTO;
import com.jose.cursomc.repositories.ClienteRepository;
import com.jose.cursomc.resources.exception.FieldMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

public class ClienteUpdateValidator  implements ConstraintValidator<ClienteUpdate, ClienteDTO> {
    
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteUpdate ann) {

    }

    @Override
    public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

        /** 
         * Obeter o id do usuario da url de put
         * */ 
        @SuppressWarnings("unchecked")
        Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        
        Integer uriId = Integer.parseInt(map.get("id"));


        List<FieldMessage> list = new ArrayList<>();

        Cliente aux = repo.findByEmail(objDto.getEmail());

        /** 
         * Verificando atraves do id, se o email que esta sendo atualizado existe em outro usuario
        */
        if (aux != null && !aux.getId().equals(uriId)) {
            list.add(new FieldMessage("email", "E-mail já cadastrado"));
        }

        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
                    .addConstraintViolation();
        }

        return list.isEmpty();
    }
}
