package stepdefinition;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.RegistrationDetails;
import provider.TokenProvider;
import utility.JsonUtils;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static java.lang.String.format;
import static provider.Endpoints.CUSTOMER_ENDPOINT_PATTERN;
import static provider.Endpoints.REGISTER_LEGAL_CUSTOMER_ENDPOINT;
import static utility.RequestSpecifications.commonGiven;

public class RegistrationSteps {

    @Step("Delete Customer by number '{0}'")
    public Response deleteCustomer(String number) {
        return commonGiven()
                .when()
                .header(AUTHORIZATION, TokenProvider.getTokenByKey("payment"))
                .delete(format(CUSTOMER_ENDPOINT_PATTERN, number));
    }

    @Step("Registration of legal customer with number '{0}'")
    public Response registerCustomer(String number) {
        RegistrationDetails details = RegistrationDetails
                .builder()
                .cif(number)
                .source("TEST")
                .build();

        return commonGiven()
                .when()
                .header(AUTHORIZATION, TokenProvider.getTokenByKey("payment"))
                .body(JsonUtils.serialize(details))
                .post(REGISTER_LEGAL_CUSTOMER_ENDPOINT);
    }
}
