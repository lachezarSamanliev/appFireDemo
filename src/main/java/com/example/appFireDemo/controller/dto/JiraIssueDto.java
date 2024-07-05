package com.example.appFireDemo.controller.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class JiraIssueDto {
    private String summary;
    private String key;
    private String url;
    private String issueType;
    private String priority;
    private String description;
    private String reporter;
    private String createdDate;
    private Map<String, List<String>> comments; // commentText + commentAuthor
}
