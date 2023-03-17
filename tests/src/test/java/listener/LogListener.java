package listener;

import io.qameta.allure.Attachment;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class LogListener implements ITestListener {
    private final ByteArrayOutputStream requestStream = new ByteArrayOutputStream();
    private final ByteArrayOutputStream responseStream = new ByteArrayOutputStream();

    private final PrintStream requestPrintStream = new PrintStream(requestStream, true);
    private final PrintStream responsePrintStream = new PrintStream(responseStream, true);


    @Override
    public void onStart(ITestContext iTestContext) {
        RestAssured.filters(
                new ResponseLoggingFilter(LogDetail.ALL, responsePrintStream),
                new RequestLoggingFilter(LogDetail.ALL, requestPrintStream)
        );
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        logRequest(requestStream);
        logResponse(responseStream);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        onTestSuccess(iTestResult);
    }

    @Attachment(value = "request")
    public byte[] logRequest(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    @Attachment(value = "response")
    public byte[] logResponse(ByteArrayOutputStream stream) {
        return attach(stream);
    }

    public byte[] attach(ByteArrayOutputStream log) {
        byte[] array = log.toByteArray();
        log.reset();
        return array;
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
    }
}
