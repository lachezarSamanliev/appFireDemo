package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraIssue {
    private String key;
    private Fields fields;
    private String self;
    private String id;
}