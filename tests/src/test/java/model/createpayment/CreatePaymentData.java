package model.createpayment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class CreatePaymentData {

    @JsonProperty("Customer")
    private Customer customer;

    @JsonProperty("PayNo")
    private String payNo;

    @JsonProperty("CreditAccountIBAN")
    private String creditAccountIBAN;

    @JsonProperty("DueAmount")
    private Long dueAmount;

    @JsonProperty("PaidAmount")
    private Long paidAmount;

    @JsonProperty("ExactAmount")
    private Long exactAmount;

    @JsonProperty("Token")
    private String token;

    @JsonProperty("Source")
    private String source;

    @JsonProperty("PaymentDetailsOptions")
    private PaymentDetailsOptions paymentDetailsOptions;
}
