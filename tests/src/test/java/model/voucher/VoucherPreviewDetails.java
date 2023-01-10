package model.voucher;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class VoucherPreviewDetails {

    @JsonProperty("OrderId")
    private String orderID;

    @JsonProperty("DenominationId")
    private String denominationID;

    @JsonProperty("VoucherPrice")
    private Long voucherPrice;

    @JsonProperty("VoucherCost")
    private Long voucherCost;

    @JsonProperty("Quantity")
    private Long quantity;
}
