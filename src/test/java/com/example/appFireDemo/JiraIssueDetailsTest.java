package com.example.appFireDemo;

import com.example.appFireDemo.models.JiraIssue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JiraIssueDetailsTest {

    @Test
    public void testJiraIssueObjectFromJson() throws IOException {

        // Load the JSON file from resources
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JiraIssueDetailsById.json");
        ObjectMapper objectMapper = new ObjectMapper();

        JiraIssue jiraIssue = objectMapper.readValue(inputStream, JiraIssue.class);

        assertEquals("Lachezar Samanliev", jiraIssue.getFields().getReporter().getDisplayName());
        assertEquals("10073", jiraIssue.getId());
        assertEquals("3", jiraIssue.getFields().getPriority().getId());
    }
}
