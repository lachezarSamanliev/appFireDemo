package com.example.appFireDemo.models;

import lombok.Data;

import java.util.List;

@Data
public class RootResponse {
    private String expand;
    private int startAt;
    private int maxResults;
    private int total;
    private List<JiraIssue> issues;
}
