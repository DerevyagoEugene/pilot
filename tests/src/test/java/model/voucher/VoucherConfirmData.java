package model.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class VoucherConfirmData {

    @JsonProperty("Source")
    private String source;

    @JsonProperty("OrderId")
    private String orderID;

    @JsonProperty("DenominationId")
    private String denominationID;

    @JsonProperty("Quantity")
    private Long quantity;

    @JsonProperty("VoucherPrice")
    private Long voucherPrice;

    @JsonProperty("VoucherCost")
    private Long voucherCost;

    @JsonProperty("PaymentDetails")
    private VoucherConfirmPaymentDetails paymentDetails;
}
