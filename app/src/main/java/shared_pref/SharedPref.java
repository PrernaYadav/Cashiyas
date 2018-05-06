package shared_pref;

/**
 * Created by JKB-2 on 4/2/2016.
 */
public class SharedPref {

    //////////////////radio button values/////////
    public static String
            sradioButtonMaritalStatus,
            gender,
            sRadioButtonResidenceType,
            sRadioBtnTypeOfProperty,
            sradioButtonVerified,
            sradioButtonreadyToMove
    ;

    public static String  sRadioButtonRepayment, seditTextMonthlyIncome, seditTextLoanTenure,
            loantypes, loanmant,sSpinnerSelectBank,
            seditTextEMI,sSpinnerAccountType, time, city,emptype,
            Location_gps;
    public static String Adharcard_loantransfer,Adharcard_loan,pancard_loantransfer, pancard_loan , salaryModeValue;
    public static String  banktypes, ownership;
    public static String nametrns,incometrans,citytrance,interest,tenture,exitloan,
            gentrans,dobtrans,emailtrans,phonetrans,grossinctrans,transf_type;

    public static String Sloan_where,Sloan_amnt;
    public static String subcatory_loan,dob_loan, current_resi_date;


    /////////////////////////////////////////insurance_data/////////////////////////////////////////////////

    public  static  String namei_insurance,emaili_insurance,contacti_insurance,ser_insurance;

    public  static String panview, adharview, enqtypetransfer,nametransfer,gendertransfer,dobtransfer,emailtransfer,citytransfer,contacttransfer,ExistingLoantransfer,ouutstandingloantransfer,intersesttransfer,loanbanktransfer,grossincometransfer,netincometransfer;
    /////////////////////////////api used in app//////////////////////////////////////////////////////////////////////////////////////////////////////////

    public  static  String contacts ="http://cashiya.in/cashiya-api/saveContactlist.php";

    public static String loaninsurance ="http://cashiya.in/cashiya-api/userLoanInquiry_2.php"; //1st form

    public static String loaninsurance5 ="http://cashiya.in/cashiya-api/userLoanInquiry_3.php";  //2nd form

    public  static String loan_transfer="http://cashiya.in/cashiya-api/loan_transfer.php";

    public static String sendotponregisternumber = "http://cashiya.in/cashiya-api/register_signin_sendotp.php";

    public static String sendotponupdatenumber = "http://cashiya.in/cashiya-api/register_update_newno.php";

    public static String loaninsurance_edit="http://cashiya.in/cashiya-api/userLoanInquiry_edit_4.php"; //form 3

    public  static String user_insurance_list="http://cashiya.in/cashiya-api/user_insurance_query_list.php";

    public  static String user_insurance_data="http://cashiya.in/cashiya-api/insurance_api_response.php";

    public  static String user_transfer_list="http://cashiya.in/cashiya-api/user_loan_transfer_query_list.php";

    public  static String user_transfer_data="http://cashiya.in/cashiya-api/user_loan_transfer_query_data.php";

    public  static String user_loan_list="http://cashiya.in/cashiya-api/user_loan_query_list.php";

    public  static String user_loan_data="http://cashiya.in/cashiya-api/user_loan_query_data.php";

    public  static String city_api="http://cashiya.in/cashiya-api/city_api.php";

    public  static String state_api="http://cashiya.in/cashiya-api/state_api.php";

    public  static String bank_condition="http://cashiya.in/cashiya-api/bank_condition.php";

    public  static String company_name="http://cashiya.in/cashiya-api/company_name_api.php";

    public  static String Sname, Semail,Ssubcatory, SCurrentlyEmployedAs, SCurrentPosition, SCompanyAddress, SCompanyAddress1, SCompanyState, SCompanyCity, SCompanyPincode, SCurrentAddress, SCurrentAddress1, SCurrentState, SCurrentCity, SCurrentPincode, SPermanentAddress, SPermanentAddress1, SPermanentState, SPermanentCity, SPermanentPincode, Stvadhars, Stvpans, Sdob, SbankAccountType, SBankName, Sincome, Ssalryaccount,Ssalryaccount_type, Scity, Semployment, SDate, Sloantype, Samount, Stimeperiod, SGender, SResType , Shaveloan_status,Shaveloan, Semi, Sidproof, SAddproof, Sincproof, Sofficeproof, Sbankproof, Sownershipproof, Sbusinessproof, SCompanyName, SSalaryMode, Swhere_loan,Stenure_loan;

    public  static String contactloan,monthlyincome, id_category,loan_amnt,add_proof,income_proof,office_proof,countinuity, bankstm,
            random;

}


