package api.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountBalance {

    private String custAcNo;

    private BigDecimal acAvailBal;

    private String customerNo;

    private String acOpenDate;
}
