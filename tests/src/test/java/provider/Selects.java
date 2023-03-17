package provider;

public class Selects {

    public static final String SELECT_CUSTOMERS_WITH_ENGLISH_ADDRESS =
            "SELECT CUSTOMER_NO FROM STTM_CUSTOMER_ADDRESS WHERE REGEXP_LIKE(STREETNAME2, '[A-Za-z]') AND ROWNUM <= 100";
    public static final String SELECT_GET_ACCOUNT_BALANCE_PATTERN =
            "select cust_ac_no AS custAcNo, acy_avl_bal AS acAvailBal, CUST_NO AS customerNo, AC_OPEN_DATE AS acOpenDate from fcubeuser.sttm_cust_account where cust_ac_no='%s'";
    public static final String SELECT_TRANSACTION_PRESENCE_PATTERN =
            "SELECT TRN_REF_NO AS trnRefNo, AC_No AS acNo, EXTERNAL_REF_NO AS externalRefNo, FINANCIAL_CYCLE AS financialCycle  FROM ACVW_ALL_AC_ENTRIES WHERE TRN_REF_NO = '%s' AND AC_NO = '%s'";
    public static final String SELECT_TRANSACTION_SUCCESS_STATUS_PATTERN =
            "SELECT ID AS id, EXTERNAL_REFERENCE AS extRef, CIF AS cif, DEBIT_ACCOUNT AS debitAc, CREDIT_ACCOUNT AS creditAc, STATUS AS status FROM FCUBEUSER.BAETB_SOCIALSECURITY_PMT WHERE EXTERNAL_REFERENCE = '%s' AND PAY_NO = '%s'";
    public static final String SELECT_TRANSACTION_ACCEPTED_PATTERN =
            "select PAYMENT_DIRECTION AS paymentDir, MESSAGE_REFERENCE AS messFef, STATUS AS status, DETECTION_ID AS detectId from fcubeuser.baetb_cma_adapter_log where detection_id = '%s'";
    public static final String SELECT_TRANSACTION_ENTRIES_PATTERN =
            "select trn_ref_no AS trnRefNo, AC_ENTRY_SR_NO AS acEntrySrNo, AC_BRANCH AS acBranch, RELATED_CUSTOMER AS relatedCust, AMOUNT_TAG AS tag fcubeuser.acvw_all_ac_entries where trn_ref_no = '%s'";
}
