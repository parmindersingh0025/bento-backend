package com.Bento.Bento;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs/**",
        		"/configuration/ui/**",
        		"/swagger-resources/**", "/configuration/security/**",
        		"/configuration/security",
                "/swagger-ui/**",
                "/swagger-ui","/swagger-ui.html",
                "/swagger-ui.html/**","/webjars/**");
    }

}