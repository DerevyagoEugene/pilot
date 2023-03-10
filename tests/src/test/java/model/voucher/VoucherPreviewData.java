package model.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class VoucherPreviewData {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("VoucherAmount")
    private Long voucherAmount;

    @JsonProperty("VoucherCurrency")
    private String voucherCurrency;

    @JsonProperty("PaymentCurrency")
    private String paymentCurrency;

    @JsonProperty("VoucherDetails")
    private VoucherPreviewDetails voucherDetails;

    @JsonProperty("PaymentDetails")
    private VoucherPreviewPaymentDetails paymentDetails;
}
