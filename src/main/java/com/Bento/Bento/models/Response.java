package com.Bento.Bento.models;
import java.io.Serializable;

import lombok.Data;

@Data
public class Response implements Serializable {

    private final String response;

    public Response(String response) {
        this.response = response;
    }

}