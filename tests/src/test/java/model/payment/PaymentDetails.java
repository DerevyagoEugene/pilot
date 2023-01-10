package model.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentDetails {

    @JsonProperty("DueAmount")
    private Long dueAmount;

    @JsonProperty("MaxAmount")
    private Long maxAmount;

    @JsonProperty("PayExact")
    private Boolean payExact;

    @JsonProperty("CustomerName")
    private String customerName;

    @JsonProperty("BillerName")
    private String billerName;

    @JsonProperty("IBAN")
    private String iban;

    @JsonProperty("Token")
    private String token;
}
