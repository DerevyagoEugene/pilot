package test;

import annotation.XrayId;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import listener.LogListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import stepdefinition.CommonSteps;
import stepdefinition.RegistrationSteps;

@Listeners(LogListener.class)
@Feature("[CliQ][Positive Functionality][IPS]")
public class RegistrationTests extends BaseTest {

    private final RegistrationSteps registrationSteps = new RegistrationSteps();
    private final CommonSteps commonSteps = new CommonSteps();

    @Test(description = "[CliQ][Positive Functionality][IPS] - Registration of legal customer")
    @Description("Registration of legal customer where stateProvinceRegion is populated from STREETNAME2 has english chars")
    @TmsLink("MS-14312")
    @XrayId("10089")
    public void testRegistrationOfLegalCustomer() {
        String customerNumber = "001000763";
        commonSteps.checkRequestHasBeenSuccessful(registrationSteps.deleteCustomer(customerNumber));

        Response response = registrationSteps.registerCustomer(customerNumber);
        commonSteps.checkRequestHasBeenSuccessful(response);
    }
}
