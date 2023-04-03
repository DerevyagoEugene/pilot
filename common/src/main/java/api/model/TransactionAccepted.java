package api.model;

import lombok.Data;

@Data
public class TransactionAccepted {

    private String paymentDir;

    private String messFef;

    private String status;

    private String detectId;
}
