package com.jose.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;
 
    /** 
     * Liberando rotas para acesso direto, todas as rotas mapeados no obj PUBLIC_MATCHER,
     * teram acesso livre
    */
    private static final String[] PUBLIC_MATCHER = {
        "/h2-console/**",
    };

    private static final String[] PUBLIC_MATCHER_GET = {
        "/produtos/**",
        "/categorias/**",
    };
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // permitindo acesso ao h2-console, quanto estiver no ambiente de teste
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable();
        http.authorizeRequests()
            .antMatchers(HttpMethod.GET, PUBLIC_MATCHER_GET).permitAll() // permite apenas a consulta, sem possibilidade alteração
            .antMatchers(PUBLIC_MATCHER).permitAll()
            .anyRequest().authenticated();
            // configuraçaõ para não gera estado na aplicação
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    /**
     * CONFIGURANDO CORS
     * Permitindo acesso de multiplas fontes com as configurações basicas 
    */
    @Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
