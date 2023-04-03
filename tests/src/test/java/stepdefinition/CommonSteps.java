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

    @Step("Check request has been failed")
    public void checkRequestHasBeenFailed(Response response, int httpStatus, String message) {
        response
                .then()
                .assertThat()
                .statusCode(httpStatus)
                .body("Message", equalTo(message));
    }
}
