package api.model;

import lombok.Data;

@Data
public class TransactionEntries {

    private String trnRefNo;

    private String acEntrySrNo;

    private String acBranch;

    private String relatedCust;

    private String tag;

}
