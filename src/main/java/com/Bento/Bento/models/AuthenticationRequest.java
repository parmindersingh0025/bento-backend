package com.Bento.Bento.models;

import java.io.Serializable;

import lombok.Data;

@Data
public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;

    //need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

    public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}