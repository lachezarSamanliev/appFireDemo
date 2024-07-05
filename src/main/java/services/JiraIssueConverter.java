package services;

import com.example.appFireDemo.controller.dto.JiraIssueDto;
import com.example.appFireDemo.models.IssueComment;
import com.example.appFireDemo.models.JiraIssue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JiraIssueConverter {

    public JiraIssueDto convertJiraIssueToJiraIssueDto(JiraIssue jiraIssue) {
        JiraIssueDto jiraIssueDto = new JiraIssueDto();
        jiraIssueDto.setSummary(jiraIssue.getFields().getSummary());
        jiraIssueDto.setKey(jiraIssue.getKey());
        jiraIssueDto.setUrl(jiraIssue.getSelf());
        jiraIssueDto.setIssueType(jiraIssue.getFields().getIssuetype().getName());
        jiraIssueDto.setDescription(jiraIssue.getFields().getDescription().getContent().getFirst().getContent().getFirst().getText());
        jiraIssueDto.setReporter(jiraIssue.getFields().getReporter().getDisplayName());
        jiraIssueDto.setCreatedDate(jiraIssue.getFields().getCreated());

        // List<String> is required because one person can have more than 1 comment
        Map<String, List<String>> map = new HashMap<>();
        for (IssueComment issueComment : jiraIssue.getFields().getComment().getComments()) {
            String author = issueComment.getAuthor().getDisplayName();
            String commentBody = issueComment.getBody().getContent().getFirst().getContent().getFirst().getText();

            // Check if the author already has entries in the map
            if (map.containsKey(author)) {
                // append the new comment to the existing list
                map.get(author).add(commentBody);
            } else {
                // create a new list with the comment and put it in the map
                List<String> comments = new ArrayList<>();
                comments.add(commentBody);
                map.put(author, comments);
            }
        }
        jiraIssueDto.setComments(map);

        return jiraIssueDto;
    }
}
