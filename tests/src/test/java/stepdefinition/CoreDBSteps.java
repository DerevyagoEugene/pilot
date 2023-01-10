package stepdefinition;

import api.SqlClient;
import io.qameta.allure.Step;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.testng.Assert.*;
import static provider.Selects.*;

public class CoreDBSteps {

    @Step("DB: Run XML message request to Core")
    public void runXmlMessageToCore() {
        List<Map<String, Object>> maps = SqlClient
                .getInstance()
                .makeRequestAndGet("", new MapListHandler());
    }

    @Step("DB: Run XML message response from Core")
    public void runXmlMessageFromCore() {
        List<Map<String, Object>> maps = SqlClient
                .getInstance()
                .makeRequestAndGet("", new MapListHandler());
    }

    @Step("DB: Get Customer Numbers where Street Name contains English characters")
    public List<String> getCustomerNumbersWithEnglishAddress() {
        return SqlClient
                .getInstance()
                .makeRequestAndGet(SELECT_CUSTOMERS_WITH_ENGLISH_ADDRESS, new ColumnListHandler<>());
    }

    @Step("DB: Get Account balance")
    public BigDecimal getAccountBalance(String customerAccountNumber) {
        String query = format(SELECT_GET_ACCOUNT_BALANCE_PATTERN, customerAccountNumber);
        return (BigDecimal) SqlClient
                .getInstance()
                .makeRequestAndGet(query, new ColumnListHandler<>(2))
                .get(0);
    }

    @Step("DB: Check transaction is present in DB")
    public void checkTransactionIsPresent(String transaction, String accountNumber) {
        String query = format(SELECT_TRANSACTION_PRESENCE_PATTERN, transaction, accountNumber);
        boolean isTransactionPresent = !SqlClient
                .getInstance()
                .makeRequestAndGet(query, new ColumnListHandler<>())
                .isEmpty();
        assertTrue(
                isTransactionPresent,
                "Transaction should be present in DB"
        );
    }

    @Step("DB: Check transaction has been successful")
    public void checkTransactionIsSuccessful(String externalReference, String paymentNumber) {
        String query = format(SELECT_TRANSACTION_SUCCESS_STATUS_PATTERN, externalReference, paymentNumber);
        String status = (String) SqlClient
                .getInstance()
                .makeRequestAndGet(query, new ColumnListHandler<>(18))
                .get(0);
        assertEquals(
                status,
                "SUCCESS",
                "Transaction should be successful"
        );
    }

    @Step("DB: Check fund transfer has been accepted")
    public void isFundTransferAccepted(String detectionId) {
        await()
                .atMost(10, SECONDS)
                .until(() -> isTransferAccepted(detectionId));
        assertTrue(
                isTransferAccepted(detectionId),
                "Fund transfer should be accepted"
        );
    }

    private boolean isTransferAccepted(String detectionId) {
        String query = format(SELECT_TRANSACTION_ACCEPTED_PATTERN, detectionId);
        return SqlClient
                .getInstance()
                .makeRequestAndGet(query, new ColumnListHandler<>(8))
                .stream()
                .anyMatch(it -> it.toString().equals("Accepted"));
    }

    @Step("DB: Check account transaction entries are not empty")
    public void checkAccountTransactionEntries(String transactionId) {
        String query = format(SELECT_TRANSACTION_ENTRIES_PATTERN, transactionId);
        List<String> entries = SqlClient
                .getInstance()
                .makeRequestAndGet(query, new ColumnListHandler<>());
        assertFalse(
                entries.isEmpty(),
                "Account transaction entries should not be empty"
        );
    }
}
