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

    private final FundTransferSteps fundTransferSteps = pico.getComponent(FundTransferSteps.class);
    private final CommonSteps commonSteps = pico.getComponent(CommonSteps.class);
    private final CoreDBSteps dbSteps = pico.getComponent(CoreDBSteps.class);

    @Test(description = "Successful payment from BaE to BaE accounts")
    @Description("Successful payment from BaE to BaE accounts")
    @TmsLink("MS-10112")
    @XrayId("10010")
    public void testSuccessfulPaymentFromBaeToBaeAccounts() {
        Customer customer = Customer
                .builder()
                .account("0010100027215102")
                .cif("001000272")
                .build();

        Beneficiary beneficiary = Beneficiary
                .builder()
                .alias("MYNEWALIAS")
                .aliasType("ALIAS")
                .build();

        FundTransferData fundTransferData = FundTransferData
                .builder()
                .chargeType("our")
                .additionalDetails(EMPTY)
                .isRecurring(false)
                .customer(customer)
                .amount(7L)
                .currency("JOD")
                .standingInstruction(new StandingInstruction())
                .transferType("Ips")
                .referenceID(null)
                .beneficiary(beneficiary)
                .promoCode(EMPTY)
                .myWUNumber(EMPTY)
                .build();

        Response response = fundTransferSteps.transferFund(fundTransferData);
        commonSteps.checkRequestHasBeenSuccessful(response);

        String detectionId = JsonPath.read(response.getBody().asString(), "$.Info.Id").toString();
        String transactionId = JsonPath.read(response.getBody().asString(), "$.Info.TransactionId");

        dbSteps.isFundTransferAccepted(detectionId);
        dbSteps.checkAccountTransactionEntries(transactionId);
    }
}
