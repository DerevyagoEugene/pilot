package model.fundtransfer;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Beneficiary {

    @JsonProperty("Alias")
    private String alias;

    @JsonProperty("AliasType")
    private String aliasType;

    @Override
    public String toString() {
        return String.format("Beneficiary{alias='%s', aliasType='%s'}", alias, aliasType);
    }
}
