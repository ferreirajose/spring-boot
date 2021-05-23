package com.jose.cursomc.resources.exception;

import javax.servlet.http.HttpServletRequest;

import com.jose.cursomc.services.exceptions.ObjectNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** 
 * class auxilizar que ira interceptar as execeções e ser reponsavel por lidar com erros da aplicação, 
 * ira um retorna um reposta HTTP com informações
 * */ 
@ControllerAdvice
public class ResourceExeceptionHandler {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
        
        StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }

     
}
