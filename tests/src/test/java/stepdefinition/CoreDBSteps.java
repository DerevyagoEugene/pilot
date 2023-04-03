package stepdefinition;

import api.SqlClient;
import api.model.*;
import io.qameta.allure.Step;
import java.math.BigDecimal;
import java.util.List;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.testng.Assert.*;
import static provider.Selects.*;

public class CoreDBSteps {

    @Step("DB: Get Customer Numbers where Street Name contains English characters")
    public List<String> getCustomerNumbersWithEnglishAddress() {
        return SqlClient
                .getInstance()
                .makeRequestAndFetch(SELECT_CUSTOMERS_WITH_ENGLISH_ADDRESS, String.class);
    }

    @Step("DB: Get Account balance")
    public BigDecimal getAccountBalance(String customerAccountNumber) {
        String query = format(SELECT_GET_ACCOUNT_BALANCE_PATTERN, customerAccountNumber);

        return SqlClient
                .getInstance()
                .makeRequestAndFetch(query, AccountBalance.class)
                .get(0)
                .getAcAvailBal();
    }

    @Step("DB: Check transaction is present in DB")
    public void checkTransactionIsPresent(String transaction, String accountNumber) {
        String query = format(SELECT_TRANSACTION_PRESENCE_PATTERN, transaction, accountNumber);

        boolean isTransactionPresent = !SqlClient
                .getInstance()
                .makeRequestAndFetch(query, TransactionPresence.class)
                .isEmpty();

        assertTrue(
                isTransactionPresent,
                "Transaction should be present in DB"
        );
    }

    @Step("DB: Check transaction has been successful")
    public void checkTransactionIsSuccessful(String externalReference, String paymentNumber) {
        String query = format(SELECT_TRANSACTION_SUCCESS_STATUS_PATTERN, externalReference, paymentNumber);

        String status = SqlClient
                .getInstance()
                .makeRequestAndFetch(query, SuccessTransaction.class)
                .get(0)
                .getStatus();

        assertEquals(
                status,
                "SUCCESS",
                "Transaction should be successful"
        );
    }

    @Step("DB: Check fund transfer has been accepted")
    public void isFundTransferAccepted(String detectionId) {
        await()
                .atMost(30, SECONDS)
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
                .makeRequestAndFetch(query, TransactionAccepted.class)
                .stream()
                .anyMatch(it -> it.getStatus().equals("Accepted"));
    }

    @Step("DB: Check account transaction entries are not empty")
    public void checkAccountTransactionEntries(String transactionId) {
        String query = format(SELECT_TRANSACTION_ENTRIES_PATTERN, transactionId);

        List<TransactionEntries> entries = SqlClient
                .getInstance()
                .makeRequestAndFetch(query, TransactionEntries.class);

        assertFalse(
                entries.isEmpty(),
                "Account transaction entries should not be empty"
        );
    }
}
