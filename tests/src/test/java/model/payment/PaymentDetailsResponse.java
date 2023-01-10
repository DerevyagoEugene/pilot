package model.payment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PaymentDetailsResponse {

    @JsonProperty("Code")
    private Long code;

    @JsonProperty("Message")
    private String message;

    @JsonProperty("PaymentDetails")
    private PaymentDetails paymentDetails;
}
