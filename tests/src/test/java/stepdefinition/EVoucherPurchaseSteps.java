package stepdefinition;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.voucher.*;
import provider.TokenProvider;
import utility.JsonUtils;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR;
import static org.hamcrest.Matchers.equalTo;
import static provider.Endpoints.E_VOUCHER_CONFIRM_PURCHASE_ENDPOINT;
import static provider.Endpoints.E_VOUCHER_PREVIEW_PURCHASE_ENDPOINT;
import static utility.RequestSpecifications.commonGiven;

public class EVoucherPurchaseSteps {

    private static final String JOD_CURRENCY = "JOD";
    private static final Long ONE = 1L;
    private static final String DENOMINATION_ID = "152";
    private static final String SOURCE = "APPSIOS";
    private static final String ACCOUNT_ID = "0010100067615101";
    private static final String PAYMENT_METHOD = "Account";

    @Step("Request Confirm Voucher Purchase API with valid request for OrderID = {0}")
    public Response requestConfirmEVoucherPayment(String orderId) {
        return commonGiven()
                .when()
                .header("Authorization", TokenProvider.getTokenByKey("payment"))
                .body(JsonUtils.serialize(getVoucherConfirmPurchaseData(orderId)))
                .post(E_VOUCHER_CONFIRM_PURCHASE_ENDPOINT);
    }

    @Step("Request Preview Voucher Purchase API with valid request")
    public Response requestPreviewEVoucherPayment() {
        return commonGiven()
                .when()
                .header("Authorization", TokenProvider.getTokenByKey("3"))
                .body(JsonUtils.serialize(getVoucherPreviewPurchaseData()))
                .post(E_VOUCHER_PREVIEW_PURCHASE_ENDPOINT);
    }

    @Step("Check RT transaction in logs")
    public void checkRtTransactionsInLogs(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(SC_INTERNAL_SERVER_ERROR)
                .body("Message", equalTo("Failure"))
                .body("Code", equalTo(1));
    }

    private VoucherPreviewData getVoucherPreviewPurchaseData() {
        VoucherPreviewPaymentDetails paymentDetails = VoucherPreviewPaymentDetails
                .builder()
                .method(PAYMENT_METHOD)
                .accountID(ACCOUNT_ID)
                .build();
        VoucherPreviewDetails previewDetails = VoucherPreviewDetails
                .builder()
                .orderID(randomAlphanumeric(10))
                .denominationID(DENOMINATION_ID)
                .voucherPrice(ONE)
                .voucherCost(ONE)
                .quantity(ONE)
                .build();
        return VoucherPreviewData
                .builder()
                .source(SOURCE)
                .voucherAmount(ONE)
                .voucherCurrency(JOD_CURRENCY)
                .paymentCurrency(JOD_CURRENCY)
                .voucherDetails(previewDetails)
                .paymentDetails(paymentDetails)
                .build();
    }

    private VoucherConfirmData getVoucherConfirmPurchaseData(String orderId) {
        VoucherConfirmPaymentDetails paymentDetails = VoucherConfirmPaymentDetails
                .builder()
                .method(PAYMENT_METHOD)
                .accountID(ACCOUNT_ID)
                .accountCurrency(JOD_CURRENCY)
                .amount(ONE)
                .voucherCurrency(JOD_CURRENCY)
                .narrative("iTunes $1")
                .build();
        return VoucherConfirmData
                .builder()
                .source(SOURCE)
                .orderID(orderId)
                .denominationID(DENOMINATION_ID)
                .voucherPrice(ONE)
                .voucherCost(ONE)
                .quantity(ONE)
                .paymentDetails(paymentDetails)
                .build();
    }
}
