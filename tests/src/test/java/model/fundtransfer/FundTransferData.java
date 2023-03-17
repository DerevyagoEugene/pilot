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

    @JsonProperty("PurposeCode")
    private String purposeCode;

    @Override
    public String toString() {
        return "FundTransferData{" +
                "chargeType='" + chargeType + '\'' +
                ", additionalDetails='" + additionalDetails + '\'' +
                ", isRecurring=" + isRecurring +
                ", customer=" + customer +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", standingInstruction=" + standingInstruction +
                ", transferType='" + transferType + '\'' +
                ", referenceID=" + referenceID +
                ", beneficiary=" + beneficiary +
                ", myWUNumber='" + myWUNumber + '\'' +
                ", promoCode='" + promoCode + '\'' +
                ", purposeCode='" + purposeCode + '\'' +
                '}';
    }
}
