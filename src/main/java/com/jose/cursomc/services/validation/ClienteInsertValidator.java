package com.jose.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.jose.cursomc.domain.Cliente;
import com.jose.cursomc.domain.enums.TipoCliente;
import com.jose.cursomc.dto.ClienteNewDto;
import com.jose.cursomc.repositories.ClienteRepository;
import com.jose.cursomc.resources.exception.FieldMessage;
import com.jose.cursomc.services.validation.utils.BR;

import org.springframework.beans.factory.annotation.Autowired;


/** 
 * @TODO
 * VERIFICAR NOVAMENTE A IMPLEMENTAÇÃO POIS ESTA GERANDO ERRO 
 * java.lang.IllegalStateException: JSR-303
*/

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDto> {
   
    @Autowired
    private ClienteRepository repo;

    @Override
    public void initialize(ClienteInsert ann) {
    }

    @Override
    public boolean isValid(ClienteNewDto objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();
        
        if (objDto.getTipo().equals(TipoCliente.PF.getCode()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("CPF_OU_CNPJ", "CPF inválido"));
        }

        if (objDto.getTipo().equals(TipoCliente.PJ.getCode()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage("CPF_OU_CNPJ", "CNPJ inválido"));
        }

        Cliente aux = repo.findByEmail(objDto.getEmail());

        if(aux != null) {
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
