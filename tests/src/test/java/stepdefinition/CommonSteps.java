package stepdefinition;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.equalTo;

public class CommonSteps {

    @Step("Check request has been successful")
    public void checkRequestHasBeenSuccessful(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(SC_OK)
                .body("Message", equalTo("Success"));
    }
}
