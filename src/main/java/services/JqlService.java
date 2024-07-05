package services;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JqlService {

    public static final String START_OF_WEEK = "startOfWeek()";
    public static final String ISSUETYPE_EQUALS = "issuetype = ";
    public static final String PROJECT_EQUALS = "project = '";

    // I see that a possibility is to use a more efficient query
    // "issueType IN (Bug,Story)
    // I was having issues running it and decided to use OR statements
    public String buildJQLQueryForIssueTypes(List<String> issueTypes, String projectKey) {
        if (issueTypes == null || issueTypes.isEmpty()) {
            return "";
        }
        StringBuilder jqlQuery = new StringBuilder();

        // Add the project key condition if provided
        if (projectKey != null && !projectKey.isEmpty()) {
            jqlQuery.append(PROJECT_EQUALS).append(projectKey).append("' ");
            if (issueTypes != null && !issueTypes.isEmpty()) {
                jqlQuery.append("AND ");
            }
        }

        for (int i = 0; i < issueTypes.size(); i++) {
            jqlQuery.append(ISSUETYPE_EQUALS).append(issueTypes.get(i));
            // if there is another issueType in list, add OR statement
            if (i < issueTypes.size() - 1) {
                jqlQuery.append(" OR ");
            }
        }

        return jqlQuery.toString();
    }

    public String addStartOfWeekClause(String jqlQuery){
        return jqlQuery + " AND updated > " + START_OF_WEEK;
    }
}
