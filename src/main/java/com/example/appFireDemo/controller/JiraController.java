package com.example.appFireDemo.controller;

import com.example.appFireDemo.controller.dto.JiraIssueDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import services.JiraControllerService;
import services.JiraIssueConverter;

import java.util.List;

@RestController
@RequestMapping(value = "/")
public class JiraController {

    @Autowired
    protected JiraControllerService jiraControllerService;

    @Autowired
    protected JiraIssueConverter jiraIssueConverter;

    // Task Provided by AppFire
    //Use the JIRA REST API to obtain the data with the following query:
    //issuetype in (Bug, Documentation, Enhancement) and updated > startOfWeek()
    // Example request: http://localhost:8080/jira/issues/start-of-week?issueTypes=Bug&issueTypes=Story&projectKey=KAN&format=json
    @GetMapping("jira/issues/start-of-week")
    public String getIssuesForProjectKeyAndIssueTypesAndUpdatedStartOfWeek(
            @RequestParam List<String> issueTypes,
            @RequestParam(defaultValue = "") String projectKey, @RequestParam(defaultValue = "json") String format) {

        return jiraControllerService.getIssuesByTypeAndUpdatedStartOfWeek(issueTypes, projectKey, format);
    }

    // Method to get all projects from account
    @GetMapping("jira/projects")
    public void getAllProjects(){
        jiraControllerService.getAllProjectsForUser();
    }

    // Method to get all Issue Types in project
    // Can be used if user sends issue types that do not exist in current project
    @GetMapping("jira/projects/issue-types")
    public void getAllTypes(@RequestParam String projectId){
        jiraControllerService.getAllIssueTypesForProject(projectId);
    }

    // Method to get details for Issue based on its ID
    @GetMapping("jira/issue/details")
    public JiraIssueDto getIssueById(@RequestParam String issueId){
        return jiraIssueConverter.convertJiraIssueToJiraIssueDto(jiraControllerService.getJiraIssueDetails(issueId));
    }

}
