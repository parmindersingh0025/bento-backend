package com.Bento.Bento.models;
import java.io.Serializable;

import lombok.Data;

@Data
public class Response implements Serializable {

    private final String response;
    
    private String name;

    public Response(String response) {
        this.response = response;
    }

    public Response(String response,String name) {
        this.response = response;
        this.name = name;
    }

}