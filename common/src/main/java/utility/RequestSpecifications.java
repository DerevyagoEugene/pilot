package utility;

import filter.RestAssuredRequestFilter;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.Header;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;

import static configuration.Configuration.getApiUrl;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static java.nio.charset.StandardCharsets.UTF_8;

@UtilityClass
public class RequestSpecifications {

    public static RequestSpecification commonGiven() {
        return given()
                .baseUri(getApiUrl())
                .filters(
                        new AllureRestAssured(),
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new RestAssuredRequestFilter()
                )
                .contentType(JSON);
    }
}
