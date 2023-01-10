package test;

import allure.AllureReport;
import api.SqlClient;
import api.XrayCloudClient;
import api.jira.JiraClient;
import io.qameta.allure.Issue;
import io.qameta.allure.TmsLink;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import resources.PropertiesManager;
import utility.Logger;
import utility.ScenarioContext;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static api.jira.JiraStatus.DONE;
import static encryptor.EncryptionFunctions.decryptValue;
import static java.lang.String.format;
import static java.nio.file.Files.readString;

abstract public class BaseTest {

    private static final String EXECUTION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH:mm.ssSSS").format(new Date());
    private static final String LOG_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
    private final PropertiesManager propertiesManager = new PropertiesManager("xray.properties");
    private final Logger logger = Logger.getInstance();
    private final Map<String, Object> store = ScenarioContext.getInstance();

    @BeforeMethod
    public void before(Method method) {
        String logFile = format("logfile_%s_%s.txt", method.getAnnotation(Issue.class).value(), LOG_DATE_FORMAT);
        logger.initLogFile(logFile);
        logger.info(
                format("[TEST CASE][%s]: %s",
                        method.getAnnotation(TmsLink.class).value(),
                        method.getAnnotation(Test.class).description()
                ));
    }

    @AfterMethod
    public void after(Method method) {
        Path logPath = logger.getLogFile().toPath();
        try {
            store.put(method.getAnnotation(Issue.class).value(), readString(logPath));
            AllureReport.addText("Logfile", Files.readAllBytes(logPath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterSuite
    public void afterAll(ITestContext context) {
        SqlClient.getInstance().terminate();

        XrayCloudClient xrayCloudClient = new XrayCloudClient(
                decryptValue(propertiesManager.getProperty("xray.id")),
                decryptValue(propertiesManager.getProperty("xray.secret"))
        );

        String execId = xrayCloudClient.createTestExecution("Automated Tests Execution " + EXECUTION_DATE_FORMAT);
        List<String> passedIds = new ArrayList<>();
        List<String> failedIds = new ArrayList<>();
        List<String> skippedIds = new ArrayList<>();

        // Passed Tests
        for (ITestNGMethod method: new ArrayList<>(context.getPassedTests().getAllMethods())) {
            passedIds.add(method.getConstructorOrMethod().getMethod().getAnnotation(Issue.class).value());
        }
        // Failed Tests
        for (ITestNGMethod method: new ArrayList<>(context.getFailedTests().getAllMethods())) {
            failedIds.add(method.getConstructorOrMethod().getMethod().getAnnotation(Issue.class).value());
        }
        // Skipped Tests
        for (ITestNGMethod method: new ArrayList<>(context.getSkippedTests().getAllMethods())) {
            skippedIds.add(method.getConstructorOrMethod().getMethod().getAnnotation(Issue.class).value());
        }
        if (!passedIds.isEmpty()) xrayCloudClient.addTestToTestExecution(execId, passedIds);
        if (!failedIds.isEmpty()) xrayCloudClient.addTestToTestExecution(execId, failedIds);
        if (!skippedIds.isEmpty()) xrayCloudClient.addTestToTestExecution(execId, skippedIds);

        passedIds.forEach(id -> {
            xrayCloudClient.updateTestStatus(execId, id, "PASSED");
            xrayCloudClient.updateTestComment(execId, id, (String) store.get(id));
        });
        failedIds.forEach(id -> {
            xrayCloudClient.updateTestStatus(execId, id, "FAILED");
            xrayCloudClient.updateTestComment(execId, id, (String) store.get(id));
        });

        JiraClient
                .getInstance()
                .setIssueStatus(execId, xrayCloudClient.getTestJiraKeyOfExecution(execId), DONE);
    }
}
