package api.model;

import lombok.Data;

@Data
public class SuccessTransaction {

    private String id;

    private String extRef;

    private String cif;

    private String debitAc;

    private String creditAc;

    private String status;
}
