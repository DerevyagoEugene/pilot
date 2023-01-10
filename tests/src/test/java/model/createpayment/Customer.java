package model.createpayment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Customer {

    @JsonProperty("CIF")
    private String cif;

    @JsonProperty("Account")
    private String account;
}
