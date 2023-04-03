package api.jira;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resource.PropertiesManager;
import utility.JsonUtils;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static configuration.Configuration.getJiraUrl;
import static encryptor.EncryptionFunctions.decryptValue;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Base64.getEncoder;
import static java.util.Objects.isNull;

public class JiraClient {

    private static JiraClient jiraClient = null;

    private final String username;

    private final String password;

    private JiraClient() {
        PropertiesManager propertiesManager = new PropertiesManager("jira.properties");
        this.username = decryptValue(propertiesManager.getProperty("jira.username"));
        this.password = decryptValue(propertiesManager.getProperty("jira.password"));
    }

    public static JiraClient getInstance() {
        if (isNull(jiraClient)) {
            jiraClient = new JiraClient();
        }
        return jiraClient;
    }

    public Response setIssueStatus(String issueId, String jiraKey, JiraStatus jiraStatus) {
        StatusCategory statusCategory = StatusCategory
                .builder()
                .id(jiraStatus.getStatusCategory())
                .build();
        To to = To
                .builder()
                .id(issueId)
                .name(jiraStatus.getName())
                .statusCategory(statusCategory)
                .build();
        Transition transition = Transition
                .builder()
                .id(jiraStatus.getId())
                .name(jiraStatus.getName())
                .hasScreen(false)
                .isGlobal(true)
                .isInitial(false)
                .isConditional(false)
                .isLooped(false)
                .to(to)
                .build();
        JiraStatusBody jiraStatusBody = JiraStatusBody
                .builder()
                .transition(transition)
                .build();
        return common()
                .when()
                .body(JsonUtils.serialize(jiraStatusBody))
                .post(format("%s/issue/%s/transitions", getJiraUrl(), jiraKey));
    }

    private RequestSpecification common() {
        String token = format("Basic %s", getEncoder().encodeToString(format("%s:%s", username, password).getBytes()));
        return given()
                .contentType(ContentType.JSON)
                .header(AUTHORIZATION, token);
    }
}
