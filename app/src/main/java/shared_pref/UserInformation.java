package shared_pref;


import com.bounside.mj.cashiya.R;

import java.util.HashSet;
import java.util.Set;

public class UserInformation {


    /****************************** Variables ******************************/

    /********** MainActivity variables************/

    public static String
            APPLICATIONVERSION = "14",
            UserFirstName,
            UserLastName,
            Phone,
            Location,
            seditTextCurrentAddressState,
            seditTextPermanentAdress,
            seditTextPostition,
            seditTextPermanentAdressState,
            seditTextPermanentAdressPincode,
            seditTextComapnyName,
            seditTextCompanyAddress,
            seditTextCompanyAddress1,
            seditTextCompanyAddressCity,
            seditTextCompanyAddressState,
            seditTextCompanyAddressPinCode,
            seditTextPermanentAdress1,
            sRadioButtonTypeOfEmployee,
            sProofAddress,
            seditTextPermanentAdressCity,
            seditTextCurrentAddress1,
            seditTextCurrentAddressPincode,
            seditTextCurrentAddressCity,
            seditTextLastName,
            Email,
            seditTextCurrentAddress,
            Password,
            userid,
            Image;

    public static String name, id, amount,  product;

    public static int id_fuel = R.mipmap.atm;
    public static int income_edi,target_edi,yearlysave,year_edi,saving_edi;
    public static Set<String> set = new HashSet<String>();
    public static String arr[] ;
    public static float totalamountfood=0, totalamountfuel=0, totalamountshopping=0, totalamountbills=0,totalamountgroceries=0,totalamounthealth=0,totalamounttravel=0,totalamountother=0,totalamountentertainment=0;
    public static float totaltoday=0;
    public static float incomejan=0,incmoefeb=0,incomemarch=0,incomeapr=0,incomemay=0,incomejune=0,incomejuly=0,incomeaug=0,incomesep=0,incomeoct=0,incomenov=0,incomedec=0;

    public static String register = "http://cashiya.in/cashiya-api/register_new.php";

    public static String otp = "http://cashiya.in/cashiya-api/resendOTP.php";

    public static String infopro = "http://cashiya.in/cashiya-api/infopro.php";

    public static String leads = "http://cashiya.in/cashiya-api/leads.php";

    public static String register_signin = "http://cashiya.in/cashiya-api/register_signin.php?email=";

    public static float totaltransjan =0,totaltransfeb=0,totaltransmar=0,totaltransapril=0,totaltransmay=0,totaltransjune=0,totaltransjuly=0,totaltransaugust=0,totaltranssep=0,totaltransoct=0,totaltransnov=0,totaltransdec=0;
    public static float totaltransextra=0,totaltransdebit=0,totaltranspurchase=0,totaltranscredit=0,totalavailable=0;
    public static float totaltransjanbank =0,totaltransfebbank=0,totaltransmarbank=0,totaltransaprilbank=0,totaltransmaybank=0,totaltransjunebank=0,totaltransjulybank=0,totaltransaugustbank=0,totaltranssepbank=0,totaltransoctbank=0,totaltransnovbank=0,totaltransdecbank=0;
    public static float totalamountfoodweek=0, totalamountfuelweek=0, totalamountshoppingweek=0, totalamountbillsweek=0,totalamountgroceriesweek=0,totalamounthealthweek=0,totalamounttravelweek=0,totalamountotherweek=0,totalamountentertainmentweek=0,totalavailableweek=0;
    public static float totaltransextraweek=0,totaltransdebitweek=0,totaltranspurchaseweek=0,totaltranscreditweek=0;
    public static float janacno=0,febacno=0,maracno=0,apracno=0,mayacno=0,junacno=0,julacno=0,augacno=0,sepacno=0,octacno=0,novacno=0,decacno=0;
    public static float foodacno=0, fuelacno=0, shoppingacno=0, billacno=0,groacno=0,healthacno=0,travelacno=0,otheracno=0,enteracno=0,extraacno=0,debitacno=0,creditacno=0,purchaseacno=0,availacno=0;
    public static String arracno[],arrwacno[];
    public static float foodweekacno=0, fuelweekacno=0, shoppingweekacno=0, billweekacno=0,groweekacno=0,healthweekacno=0,travelweekacno=0,otherweekacno=0,enterweekacno=0,extraweekacno=0,debitweekacno=0,creditweekacno=0,purchaseweekacno=0,availweekacno=0;

    public static String rupya_exchange ="http://cashiya.in/cashiya-api/rupya_exchange_api.php";

    public static String cfl ="http://cashiya.in/cashiya-api/cfl_api1.php";

    public static String rbl ="http://cashiya.in/cashiya-api/soapandwsdl/rbl_api.php";

    public static String kotak ="http://cashiya.in/cashiya-api/soapandwsdl/kotak_api.php";

    public static String pincode ="http://cashiya.in/cashiya-api/pincode_list.php";

    public static String transaction_data ="http://cashiya.in/cashiya-api/banktransaction-api.php";

    public static String insurance_type ="http://cashiya.in/cashiya-api/insurance_api.php";

}
