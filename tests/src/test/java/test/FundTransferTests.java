package test;

import annotation.XrayId;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import listener.LogListener;
import model.createpayment.Customer;
import model.fundtransfer.Beneficiary;
import model.fundtransfer.FundTransferData;
import model.fundtransfer.StandingInstruction;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import stepdefinition.CommonSteps;
import stepdefinition.CoreDBSteps;
import stepdefinition.FundTransferSteps;

import static constatnt.Constants.EMPTY;

@Listeners(LogListener.class)
@Feature("[CliQ][IPS][Positive]")
public class FundTransferTests extends BaseTest {

    private final FundTransferSteps fundTransferSteps = new FundTransferSteps();
    private final CommonSteps commonSteps = new CommonSteps();
    private final CoreDBSteps dbSteps = new CoreDBSteps();

    @Test(description = "Successful payment from BaE to BaE accounts")
    @Description("Successful payment from BaE to BaE accounts")
    @TmsLink("MS-10112")
    @XrayId("10088")
    public void testSuccessfulPaymentFromBaeToBaeAccounts() {
        Customer customer = Customer
                .builder()
                .account("0010103438115101")
                .cif("001034381")
                .build();

        Beneficiary beneficiary = Beneficiary
                .builder()
                .name("Mister X")
                .account("2402300236561101")
                .bank("BKMEKWKW")
                .branch(null)
                .address("UAE")
                .ultimateDetails("Some details")
                .eMail("mrx@gmail.com")
                .phone("+19997778844")
                .type("Corporate")
                .build();

        StandingInstruction standingInstruction = StandingInstruction
                .builder()
                .startDate("2023-12-12")
                .endDate("2024-01-31")
                .isRecurring(true)
                .frequency("monthly")
                .every(1L)
                .daysOfMonth(new Long[] {10L, 12L})
                .build();

        FundTransferData fundTransferData = FundTransferData
                .builder()
                .chargeType("BEN")
                .isRecurring(true)
                .customer(customer)
                .amount(100L)
                .currency("USD")
                .standingInstruction(standingInstruction)
                .referenceID(EMPTY)
                .beneficiary(beneficiary)
                .purposeCode("0203")
                .build();

        Response response = fundTransferSteps.transferFund(fundTransferData);
        commonSteps.checkRequestHasBeenSuccessful(response);

        String detectionId = JsonPath.read(response.getBody().asString(), "$.Info.Id").toString();
        String transactionId = JsonPath.read(response.getBody().asString(), "$.Info.TransactionId");

        dbSteps.isFundTransferAccepted(detectionId);
        dbSteps.checkAccountTransactionEntries(transactionId);
    }
}
