package com.jose.cursomc.services.validation;

import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;

import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = ClienteInsertValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)


/** 
 * @TODO
 * VERIFICAR NOVAMENTE A IMPLEMENTAÇÃO POIS ESTA GERANDO ERRO 
 * java.lang.IllegalStateException: JSR-303
*/
public @interface ClienteInsert {
    String message() default "Erro de validação";
    
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
