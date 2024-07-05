package com.example.appFireDemo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Fields {
    private String summary;
    private IssueTypeDetails issuetype;
    private Description description;
    private Priority priority;
    private Reporter reporter;

    // "created":"2024-07-03T15:56:07.716+0300",
    private String created;
    private Comment comment;
}