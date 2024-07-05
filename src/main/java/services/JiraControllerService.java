package services;

import com.example.appFireDemo.controller.dto.JiraIssueDto;
import com.example.appFireDemo.models.IssueTypeDetails;
import com.example.appFireDemo.models.JiraIssue;
import com.example.appFireDemo.models.RootResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JiraControllerService {

    public static final String REST_API_2_SEARCH = "/rest/api/2/search?";
    public static final String REST_API_3_ISSUE = "/rest/api/3/issue/";
    public static final String REST_API_3_PROJECT_SEARCH = "/rest/api/3/project/search";

    @Value("${jira.email}")
    private String jiraEmail;

    @Value("${jira.access.token}")
    private String jiraToken;

    @Value("${jira.base.url}")
    private String jiraUrl;

    @Autowired
    protected JqlService jqlService;

    @Autowired
    protected JiraIssueConverter jiraIssueConverter;

    public String getIssuesByTypeAndUpdatedStartOfWeek(
            List<String> issueTypes,
            String projectKey, String format) {

        // Create jql query for IssueTypes and Add startOfWeek()
        String jqlQuery = jqlService.buildJQLQueryForIssueTypes(issueTypes, projectKey);
        jqlQuery = jqlService.addStartOfWeekClause(jqlQuery);

        // get response for query
        HttpResponse<JsonNode> response = executeJqlSearch(jqlQuery);
        return getJiraIssuesDtoFromRootResponse(format, response);
    }

    public String getJiraIssuesDtoFromRootResponse(String format, HttpResponse<JsonNode> response) {
        ObjectMapper objectMapper = new ObjectMapper();
        // initialize Dtos list
        List<JiraIssueDto> jiraIssueDtos = new ArrayList<>();

        try {
            // get RootResponse of Unirest GET request
            RootResponse rootResponse = objectMapper.readValue(response.getBody().toString(), RootResponse.class);
            for(JiraIssue jiraIssue: rootResponse.getIssues()){
                // for loop results of Jira Issues
                // convert to Dtos and add to return list
                jiraIssueDtos.add(jiraIssueConverter.convertJiraIssueToJiraIssueDto(getJiraIssueDetails(jiraIssue.getId())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertResponseToUserFormat(format, response, jiraIssueDtos);
    }

    public String convertResponseToUserFormat(String format, HttpResponse<JsonNode> response, List<JiraIssueDto> jiraIssueDtos) {
        // using response object because of possible error and getting status text
        if (response.getStatus() == 200) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonString = objectMapper.writeValueAsString(jiraIssueDtos);

                if ("xml".equalsIgnoreCase(format)) {
                    return convertJsonToXml(jsonString);
                }
                return jsonString;
            } catch (Exception e) {
                return String.format("Failed to convert data to JSON: %s", e.getMessage());
            }
        } else {
            return String.format("Failed to fetch data from JIRA: %s", response.getStatusText());
        }
    }

    public HttpResponse<JsonNode> executeJqlSearch(String jqlQuery){
        String finalUrl = String.format(jiraUrl + REST_API_2_SEARCH);
        return Unirest.get(finalUrl)
                .queryString("jql", jqlQuery)
                .queryString("fields", "key,summary")
                .basicAuth(jiraEmail, jiraToken)
                .asJson();
    }

    private String convertJsonToXml(String jsonString) {
        try {
            ObjectMapper jsonMapper = new ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode jsonNode = jsonMapper.readTree(jsonString);

            // Wrapping the array in a root element
            if (jsonNode.isArray()) {
                ObjectNode rootNode = jsonMapper.createObjectNode();
                rootNode.set("items", jsonNode);
                jsonNode = rootNode;
            }

            XmlMapper xmlMapper = new XmlMapper();
            return xmlMapper.writeValueAsString(jsonNode);
        } catch (Exception e) {
            return String.format("Failed to convert JSON to XML: %s", e.getMessage());
        }
    }

    public JiraIssue getJiraIssueDetails(String issueId) {
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl + REST_API_3_ISSUE + issueId)
                .basicAuth(jiraEmail, jiraToken)
                .header("Accept", "application/json")
                .asJson();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            System.out.println(response.getBody().toString());
            return objectMapper.readValue(response.getBody().toString(), JiraIssue.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getAllProjectsForUser() {
        HttpResponse<JsonNode> response = Unirest.get(jiraUrl + REST_API_3_PROJECT_SEARCH)
                .basicAuth(jiraEmail, jiraToken)
                .header("Accept", "application/json")
                .asJson();

        if (response.getStatus() == 200) {
            System.out.println(response.getBody());
        } else {
            System.out.println(String.format("Failed to fetch data from JIRA: %s", response.getStatusText()));
        }
    }

    public void getAllIssueTypesForProject(String projectId) {
        HttpResponse<JsonNode> response = Unirest.get("https://binarybuff.atlassian.net/rest/api/3/issuetype")
                .basicAuth(jiraEmail, jiraToken)
                .header("Accept", "application/json")
                .queryString("projectId", projectId)
                .asJson();

        for(Object jsonNode: response.getBody().getArray().toList()){
            ObjectMapper objectMapper = new ObjectMapper();
            try {

                IssueTypeDetails issueType = objectMapper.readValue(jsonNode.toString(), IssueTypeDetails.class);
                System.out.println(issueType.getName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
