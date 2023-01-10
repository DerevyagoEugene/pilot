package api;

import api.model.AuthCreds;
import api.model.GraphqlQuery;
import com.jayway.jsonpath.JsonPath;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utility.JsonUtils;
import utility.Logger;
import java.util.List;

import static configuration.Configuration.getXrayUrl;
import static constatnt.Constants.QUOTE;
import static constatnt.Constants.EMPTY;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static java.util.Base64.getEncoder;
import static java.util.stream.Collectors.joining;
import static utility.FileFunctions.convertFileContentsToString;
import static utility.FileHelper.getGraphQlQueryByName;

public class XrayCloudClient {

    private final String id;
    private final String secret;

    private final Logger logger = Logger.getInstance();

    public XrayCloudClient(String id, String secret) {
        this.id = id;
        this.secret = secret;
    }

    public void createTestPlan(String testPlanName) {
        logger.info(format("Creating a test plan '%s'", testPlanName));
        createEntity(testPlanName, "test_plan.gql");
    }

    public void addTestToTestExecution(String testExecution, List<String> tests) {
        logger.info(format("Adding tests to the test execution '%s'", testExecution));
        String content = convertFileContentsToString(getGraphQlQueryByName("add_test_to_exec.gql"))
                .replace("%TEST_EXECUTION%", testExecution)
                .replace("%LIST%", tests.stream().collect(joining("\", \"", QUOTE, QUOTE)));
        execute(content);
    }

    public void updateTestStatus(String testExecution, String issueId, String status) {
        logger.info(format("Updating test status for the test '%s' in test execution '%s'", issueId, testExecution));
        String id = getTestIdInExecution(testExecution, issueId);
        String content = convertFileContentsToString(getGraphQlQueryByName("update_status.gql"))
                .replace("%ID%", id)
                .replace("%STATUS%", status);
        execute(content);
    }

    public void updateTestComment(String testExecution, String issueId, String comment) {
        logger.info(format("Updating test comment for the test '%s' in test execution '%s'", issueId, testExecution));
        String id = getTestIdInExecution(testExecution, issueId);
        String content = convertFileContentsToString(getGraphQlQueryByName("update_test_run.gql"))
                .replace("%TEST_ID%", id)
                .replace("%DATA%", getEncoder().encodeToString(comment.getBytes()));
        execute(content);
    }

    public String createTest(String testName) {
        logger.info(format("Creating a new test '%s'", testName));
        Response response = createEntity(testName, "test.gql");
        return JsonPath.read(response.getBody().asString(), "$.data.createTest.test.issueId");
    }

    public String createTestExecution(String testExecutionName) {
        logger.info(format("Creating a new test execution '%s'", testExecutionName));
        Response response = createEntity(testExecutionName, "test_execution.gql");
        return JsonPath.read(response.getBody().asString(), "$.data.createTestExecution.testExecution.issueId");
    }

    private String getTestIdInExecution(String testExecution, String issueId) {
        String content = convertFileContentsToString(getGraphQlQueryByName("test_id_exec.gql"))
                .replace("%TEST_EXECUTION%", testExecution);
        String body = execute(content).getBody().asString();
        String query = format("$.data.getTestExecution.testRuns.results[?(@.test.issueId == '%s')].id", issueId);
        return ((List<String>) JsonPath.read(body, query)).get(0);
    }

    public String getTestJiraKeyOfExecution(String testExecution) {
        String content = convertFileContentsToString(getGraphQlQueryByName("test_id_exec.gql"))
                .replace("%TEST_EXECUTION%", testExecution);
        String body = execute(content).getBody().asString();
        String query = "$.data.getTestExecution.jira.key";
        return JsonPath.read(body, query);
    }

    private Response execute(String graphql) {
        GraphqlQuery graphqlQuery = GraphqlQuery
                .builder()
                .query(graphql)
                .build();
        return given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer " + getAuthToken())
                .body(JsonUtils.serialize(graphqlQuery))
                .post(format("%s/graphql", getXrayUrl()));
    }

    private Response createEntity(String entityName, String queryFile) {
        return execute(formatContent(entityName, queryFile));
    }

    private String formatContent(String entityName, String queryFile) {
        return convertFileContentsToString(
                getGraphQlQueryByName(queryFile)
        ).replace("%SUMMARY_NAME%", entityName);
    }

    private String getAuthToken() {
        AuthCreds authCreds = AuthCreds
                .builder()
                .clientID(id)
                .clientSecret(secret)
                .build();
        return given()
                .relaxedHTTPSValidation()
                .contentType(ContentType.JSON)
                .body(JsonUtils.serialize(authCreds))
                .post(format("%s/authenticate", getXrayUrl()))
                .getBody()
                .asString()
                .replace(QUOTE, EMPTY);
    }
}
