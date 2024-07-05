package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueComment {
    //private String type;
    private CommentAuthor author;
    private Body body;
}
