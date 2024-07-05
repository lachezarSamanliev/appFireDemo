package com.example.appFireDemo;

import com.example.appFireDemo.controller.dto.JiraIssueDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonDtoReaderTest {

    @Test
    public void testReadJson() throws IOException {
        // Load the JSON file from resources
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("JiraIssueDtoResult.json");
        ObjectMapper objectMapper = new ObjectMapper();

        List<JiraIssueDto> jiraIssueDtos = objectMapper.readValue(inputStream, new TypeReference<List<JiraIssueDto>>(){});
        assertEquals(2, jiraIssueDtos.size());
        assertEquals("KAN-8", jiraIssueDtos.get(0).getKey().toString());
        assertEquals("KAN-7", jiraIssueDtos.get(1).getKey().toString());

    }
}
