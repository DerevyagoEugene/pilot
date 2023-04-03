package api.model;

import lombok.Data;

@Data
public class TransactionPresence {

    private String trnRefNo;
    private String acNo;

    private String externalRefNo;

    private String financialCycle;
}
