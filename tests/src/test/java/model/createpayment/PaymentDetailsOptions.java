package model.createpayment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import static java.lang.String.format;

@Builder
public class PaymentDetailsOptions {

    @JsonProperty("ServiceId")
    private Long serviceID;

    @JsonProperty("ServiceDesc")
    private String serviceDesc;

    @JsonProperty("ServiceType")
    private Long serviceType;

    @Override
    public String toString() {
        return format("PaymentDetailsOptions{serviceID=%d, serviceDesc='%s', serviceType=%d}", serviceID, serviceDesc, serviceType);
    }
}
