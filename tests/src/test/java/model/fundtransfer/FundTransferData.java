package model.fundtransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import model.createpayment.Customer;

@Builder
public class FundTransferData {

    @JsonProperty("ChargeType")
    private String chargeType;

    @JsonProperty("AdditionalDetails")
    private String additionalDetails;

    @JsonProperty("IsRecurring")
    private Boolean isRecurring;

    @JsonProperty("Customer")
    private Customer customer;

    @JsonProperty("Amount")
    private Long amount;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("StandingInstruction")
    private StandingInstruction standingInstruction;

    @JsonProperty("TransferType")
    private String transferType;

    @JsonProperty("referenceId")
    private Object referenceID;

    @JsonProperty("Beneficiary")
    private Beneficiary beneficiary;

    @JsonProperty("MyWUNumber")
    private String myWUNumber;

    @JsonProperty("PromoCode")
    private String promoCode;
}
