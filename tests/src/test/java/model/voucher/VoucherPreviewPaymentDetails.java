package model.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class VoucherPreviewPaymentDetails {

    @JsonProperty("Method")
    private String method;

    @JsonProperty("AccountId")
    private String accountID;

    @Override
    public String toString() {
        return String.format("VoucherPreviewPaymentDetails{method='%s', accountID='%s'}", method, accountID);
    }
}
