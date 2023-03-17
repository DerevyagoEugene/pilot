package model.fundtransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Beneficiary {

    @JsonProperty("Name")
    private String name;

    @JsonProperty("Account")
    private String account;

    @JsonProperty("Bank")
    private String bank;

    @JsonProperty("Branch")
    private String branch;

    @JsonProperty("Address")
    private String address;

    @JsonProperty("UltimateDetails")
    private String ultimateDetails;

    @JsonProperty("EMail")
    private String eMail;

    @JsonProperty("Phone")
    private String phone;

    @JsonProperty("Type")
    private String type;

    @Override
    public String toString() {
        return "Beneficiary{" +
                "name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", bank='" + bank + '\'' +
                ", branch='" + branch + '\'' +
                ", address='" + address + '\'' +
                ", ultimateDetails='" + ultimateDetails + '\'' +
                ", eMail='" + eMail + '\'' +
                ", phone='" + phone + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
