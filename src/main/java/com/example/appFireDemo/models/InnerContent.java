package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class InnerContent {
    // inside the per Issue Id object
    private String type;
    private String text;
}


