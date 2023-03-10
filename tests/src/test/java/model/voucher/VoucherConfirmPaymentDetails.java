package model.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class VoucherConfirmPaymentDetails {

    @JsonProperty("Method")
    private String method;

    @JsonProperty("AccountId")
    private String accountID;

    @JsonProperty("AccountCurrency")
    private String accountCurrency;

    @JsonProperty("Amount")
    private Long amount;

    @JsonProperty("VoucherCurrency")
    private String voucherCurrency;

    @JsonProperty("Narrative")
    private String narrative;
}
