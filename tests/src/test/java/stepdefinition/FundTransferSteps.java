package stepdefinition;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.fundtransfer.FundTransferData;
import provider.TokenProvider;
import utility.JsonUtils;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static provider.Endpoints.FUNDS_TRANSFER_ENDPOINT;
import static utility.RequestSpecifications.commonGiven;

public class FundTransferSteps {

    @Step("Send a successful payment from BaE account to BaE account ")
    public Response transferFund(FundTransferData fundTransferData) {
        return commonGiven()
                .when()
                .header(AUTHORIZATION, TokenProvider.getTokenByKey("payment"))
                .body(JsonUtils.serialize(fundTransferData))
                .post(FUNDS_TRANSFER_ENDPOINT);
    }
}
