package model.createpayment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class PaymentDetailsOptions {

    @JsonProperty("ServiceId")
    private Long serviceID;

    @JsonProperty("ServiceDesc")
    private String serviceDesc;

    @JsonProperty("ServiceType")
    private Long serviceType;
}
