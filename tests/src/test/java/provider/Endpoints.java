package provider;

import static java.lang.String.format;

public class Endpoints {

    public static final String FUNDS_TRANSFER_ENDPOINT = "/1.6/bpmFundTransfer/fundsTransfer";
    public static final String REGISTER_LEGAL_CUSTOMER_ENDPOINT = "/1.6/ips/register/legal";
    public static final String CUSTOMER_ENDPOINT_PATTERN = "/1.6/ips/customer/%s";
    public static final String SOCIAL_SECURITY_ENDPOINT_PATTERN = "v1/socialsecurity/payment/%s";
    public static final String SOCIAL_SECURITY_PAYMENT_STATUS_ENDPOINT_PATTERN = "/v1/socialsecurity/payment/%s/status";
    public static final String CREATE_PAYMENT_ENDPOINT = "/1.6/damanpay/createpayment";
    public static final String E_VOUCHER_ENDPOINT_PATTERN = "1.6/evoucher/eVoucher/%s";
    public static final String E_VOUCHER_PREVIEW_PURCHASE_ENDPOINT = format(E_VOUCHER_ENDPOINT_PATTERN, "previewPurchase");
    public static final String E_VOUCHER_CONFIRM_PURCHASE_ENDPOINT = format(E_VOUCHER_ENDPOINT_PATTERN, "confirmPurchase");
}
