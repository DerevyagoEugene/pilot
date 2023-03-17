package test;

import annotation.XrayId;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import listener.LogListener;
import model.payment.PaymentDetails;
import model.payment.PaymentDetailsResponse;
import model.createpayment.CreatePaymentData;
import model.createpayment.Customer;
import model.createpayment.PaymentDetailsOptions;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import stepdefinition.CoreDBSteps;
import stepdefinition.PaymentSteps;
import utility.JsonUtils;
import java.math.BigDecimal;

import static org.testng.Assert.*;

@Listeners(LogListener.class)
@Feature("[Positive-Functionality][SSC]")
public class PaymentTests extends BaseTest {

    private final PaymentSteps paymentSteps = new PaymentSteps();
    private final CoreDBSteps dbSteps = new CoreDBSteps();

    @Test(description = "[Get payment details]: Get payment details for actual 'payNo' and 'serviceId'")
    @Description("Get payment details for actual `payNo` and `serviceId`")
    @TmsLink("MS-13142")
    @XrayId("10090")
    public void testGetPaymentDetailsPayNoServiceId() {
        Response paymentDetails = paymentSteps.getPaymentDetailsByServiceId("250000074899", "1");
        paymentSteps.checkResponsePaymentStructure(paymentDetails);
    }

    @Test(description = "[Check Payment Status]: Check payment status using 'payNo' and 'externalReference'")
    @Description("Check payment status using `payNo` and `externalReference`")
    @TmsLink("MS-13148")
    @XrayId("10087")
    public void testGetPaymentDetailsPayNoExternalReference() {
        Response paymentDetails = paymentSteps.getPaymentDetailsByExternalReference(
                "21720000000479",
                "BAE364"
        );
        paymentSteps.checkPaymentCreatedStatus(paymentDetails);
    }

    @Test(description = "[Proxy Payment]: Submit payment for individual bill allow full due payment only")
    @Description("Submit payment for individual bill allow full due payment only")
    @TmsLink("MS-14583")
    @XrayId("10086")
    public void testSubmitPaymentForIndividualBillAllowFullDuePaymentOnly() {
        String customerAccountNumber = "0010100014015102";
        String paymentNumber = "3510000000522";
        String externalReference = "BAE422";

        Response paymentDetailsResponse = paymentSteps.getPaymentDetailsByServiceId(paymentNumber, "1");

        PaymentDetails paymentDetails = JsonUtils
                .deserialize(paymentDetailsResponse.getBody().asString(), PaymentDetailsResponse.class)
                .getPaymentDetails();

        BigDecimal accountBalance = dbSteps.getAccountBalance(customerAccountNumber);

        Customer customer = Customer
                .builder()
                .cif("001000140")
                .account(customerAccountNumber)
                .build();

        PaymentDetailsOptions paymentDetailsOptions = PaymentDetailsOptions
                .builder()
                .serviceID(1L)
                .serviceDesc("CorporationMonthly")
                .serviceType(1L)
                .build();

        CreatePaymentData createPaymentData = CreatePaymentData
                .builder()
                .customer(customer)
                .payNo(paymentNumber)
                .creditAccountIBAN(paymentDetails.getIban())
                .dueAmount(20L)
                .paidAmount(20L)
                .exactAmount(1L)
                .token(paymentDetails.getToken())
                .source("MobileApp")
                .paymentDetailsOptions(paymentDetailsOptions)
                .build();

        Response response = paymentSteps.createPayment(createPaymentData);
        String transaction = JsonPath.read(response.getBody().asString(), "$.TransactionId");

        Response paymentExternalRefResponse = paymentSteps.getPaymentDetailsByExternalReference(
                paymentNumber,
                externalReference
        );

        paymentSteps.checkPaymentCreatedStatus(paymentExternalRefResponse);

        dbSteps.checkTransactionIsPresent(transaction, customerAccountNumber);
        dbSteps.checkTransactionIsSuccessful(externalReference, paymentNumber);

        assertNotEquals(
                accountBalance,
                dbSteps.getAccountBalance(customerAccountNumber),
                "Balance should be different"
        );
    }
}
