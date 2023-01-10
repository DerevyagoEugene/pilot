package stepdefinition;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.createpayment.CreatePaymentData;
import provider.TokenProvider;
import utility.JsonUtils;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.lang.String.format;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.*;
import static provider.Endpoints.*;
import static utility.RequestSpecifications.commonGiven;

public class PaymentSteps {

    @Step("Get payment details for actual billing number = '{0}' and serviceId = '{1}'")
    public Response getPaymentDetailsByServiceId(String paymentNumber, String serviceId) {
        return commonGiven()
                .pathParam("payment", format(SOCIAL_SECURITY_ENDPOINT_PATTERN, paymentNumber))
                .queryParam("serviceId", serviceId)
                .when()
                .header(AUTHORIZATION, TokenProvider.getTokenByKey("payment"))
                .get("/{payment}");
    }

    @Step("Get payment details for actual billing number = '{0}' and externalReference = '{1}'")
    public Response getPaymentDetailsByExternalReference(String paymentNumber, String externalReference) {
        return commonGiven()
                .queryParam("externalReference", externalReference)
                .when()
                .header(AUTHORIZATION, TokenProvider.getTokenByKey("payment"))
                .get(format(SOCIAL_SECURITY_PAYMENT_STATUS_ENDPOINT_PATTERN, paymentNumber));
    }

    @Step("Submit payment passed on all stages")
    public Response createPayment(CreatePaymentData createPaymentData) {
        return commonGiven()
                .when()
                .header(AUTHORIZATION, TokenProvider.getTokenByKey("payment"))
                .body(JsonUtils.serialize(createPaymentData))
                .post(CREATE_PAYMENT_ENDPOINT);
    }

    @Step("Check success response payment structure")
    public void checkResponsePaymentStructure(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("Message", equalTo("Success"))
                .body("PaymentDetails", notNullValue());
    }

    @Step("Check payment has been already recieved")
    public void checkPaymentCreatedStatus(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("Message", equalTo("Success"))
                .body("Details.DamanPayDetails.Status", equalTo("Success"))
                .body("Details.DamanPayDetails.Description", containsString("recieved"));
    }
}
