package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

    private String displayName;
    //         "displayName":"Lachezar Samanliev"
    //private String emailAddress;
    //"emailAddress":"binarybuff13@gmail.com",
}

