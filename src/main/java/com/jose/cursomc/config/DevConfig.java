package com.jose.cursomc.config;

import java.text.ParseException;

import com.jose.cursomc.services.DBService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import net.bytebuddy.dynamic.loading.ClassReloadingStrategy.Strategy;

@Configuration
@Profile("dev")
public class DevConfig {
    
    // injetando a class resposanvel pela intanciação da aplicação
    @Autowired DBService dbService;
    
    @Value("${spring.jpa.hibernate.ddl-auto}")
    private Strategy strategy;
    
    @Bean
    public boolean instantiateDatabase() throws ParseException {

        if (!"create".equals(strategy)) {
            return false;
        }

        dbService.instantiateTestDatabase();
        return true;
    }
}
