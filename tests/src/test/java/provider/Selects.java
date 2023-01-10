package provider;

public class Selects {

    public static final String SELECT_CUSTOMERS_WITH_ENGLISH_ADDRESS =
            "SELECT CUSTOMER_NO FROM STTM_CUSTOMER_ADDRESS WHERE REGEXP_LIKE(STREETNAME2, '[A-Za-z]') AND ROWNUM <= 100";
    public static final String SELECT_GET_ACCOUNT_BALANCE_PATTERN =
            "select cust_ac_no, acy_avl_bal from fcubeuser.sttm_cust_account where cust_ac_no = '%s'";
    public static final String SELECT_TRANSACTION_PRESENCE_PATTERN =
            "SELECT * FROM ACVW_ALL_AC_ENTRIES WHERE TRN_REF_NO = '%s' AND AC_NO = '%s'";
    public static final String SELECT_TRANSACTION_SUCCESS_STATUS_PATTERN =
            "SELECT * FROM FCUBEUSER.BAETB_SOCIALSECURITY_PMT WHERE EXTERNAL_REFERENCE = '%s' AND PAY_NO = '%s'";
    public static final String SELECT_TRANSACTION_ACCEPTED_PATTERN =
            "select * from fcubeuser.baetb_cma_adapter_log where detection_id = '%s'";
    public static final String SELECT_TRANSACTION_ENTRIES_PATTERN =
            "select * from fcubeuser.acvw_all_ac_entries where trn_ref_no = '%s'";
}
