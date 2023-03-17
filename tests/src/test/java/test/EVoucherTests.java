package test;

import annotation.XrayId;
import com.jayway.jsonpath.JsonPath;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import io.restassured.response.Response;
import listener.LogListener;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import stepdefinition.CommonSteps;
import stepdefinition.EVoucherPurchaseSteps;

import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;

@Listeners(LogListener.class)
@Feature("[Evoucher API][Positive Functionality]")
public class EVoucherTests extends BaseTest {

    private final EVoucherPurchaseSteps purchaseSteps = new EVoucherPurchaseSteps();
    private final CommonSteps commonSteps = new CommonSteps();

    @Test(description = "[Positive Functionality] - Confirm eVoucher Purchase API")
    @Description("Request when flex date difference from current to check BAEFN_CALCULATE_SALES_TAX")
    @TmsLink("MS-15322")
    @XrayId("10091")
    public void testRequestWhenFlexDateDifferenceFromCurrentToCheckBAEFN_CALCULATE_SALES_TAX() {
        Response previewResponse = purchaseSteps.requestPreviewEVoucherPayment();
        String orderId = JsonPath.read(previewResponse.getBody().asString(), "$.Tax.VoucherDetails.OrderId");

        commonSteps.checkRequestHasBeenFailed(
                purchaseSteps.requestConfirmEVoucherPayment(orderId),
                SC_INTERNAL_SERVER_ERROR,
                "Failure"
        );
    }
}
