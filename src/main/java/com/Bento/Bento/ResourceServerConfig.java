//package com.Bento.Bento;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//
//@Configuration
//public class ResourceServerConfig extends ResourceC {
//
//    @Override
//    public void configure(HttpSecurity http) throws Exception {
//
//      http.authorizeRequests().antMatchers("/xxx/**").authenticated();          
//      http.authorizeRequests().anyRequest().permitAll();
//      http.csrf().disable();
//
//    }
//
//}