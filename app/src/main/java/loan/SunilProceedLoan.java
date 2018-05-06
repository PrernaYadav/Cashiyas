package loan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLPeerUnverifiedException;

import shared_pref.SharedPref;
import shared_pref.UserInformation;

/**
 * Created by hp on 1/3/2017.
 */
public class SunilProceedLoan extends AppCompatActivity implements View.OnClickListener {

    LinearLayout linearLayoutSalaried, linearLayoutSelfEmployed, linearLayoutYes, linearLayout7, linearLayoutEMI, linearLayoutPosition, linearLayoutJoined;

    EditText editTextPostition,
            editTextCompanyAddress,
            editTextCompanyAddress1,
            editTextCompanyAddressPinCode,
            editTextJoingDate,
            editTextStd,
            editTextPersonalStd, editTextPrsonalPhone,
            editTextSelfCompanyPhone,
            editTextSelfCompanyEstablishedDate,
            editTextMonthlyIncome, editTextAmount, editTextLoanTenure,
            editTextEMI;

    AutoCompleteTextView
            companyname,
            editTextCompanyAddressState,
            editTextCompanyAddressCity;

    String seditTextPersonalStd,
            seditTextPrsonalPhone,
            seditTextStd,
            seditTextJoingDate,
            sediTextYear,
            seditTextMonth,
            seditTextSelfCompanyPhone,
            seditTextSelfCompanyEstablishedDate,
            seditTextAmount,
            sRadioButtonProof,
            sRadioButtonContinuity,
            sRadioButtonRepayment,
            sRadioButtonLoanTaken,
            sRadioButtonMailingAddress;

    RadioGroup radioGroupTypeOfEmployee,
            radioGroupProof,
            radioGroupSelfProof,
            radioButtonContinue,
            radioGroupRePayment,
            radioGroupLoanTaken,
            radioGroupMailingAddress;

    RadioButton radioButtonSalaried,
            radioButtonSelfEmployed,
            radioButtonSalarySlip,
            radioButtonAccountStatement,
            radioButtonITR,
            radioButtonRegistrationCertificate,
            radioButtonPanCard,
            radioButtonYes,
            radioButtonNo,
            radioButtonRepaymentYes,
            radioButtonRepaymentNo,
            radioButtonLoanTakenYes,
            radioButtonLoanTakenNo,
            radioButtonOffice,
            radioButtonCurrent,
            radioButtonPermanent;

    ProgressDialog pDialog;
    AutoCompleteTextView sunilcompanyname;
    List<String> list1 = new ArrayList<String>();
    List<String> list3 = new ArrayList<String>();
    List<String> list5 = new ArrayList<String>();
    List<String> list6 = new ArrayList<String>();
    List<String> list7 = new ArrayList<String>();
    String id, loanType;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    Spinner ediTextYear, editTextMonth, spinnerTypeOfCompany, spinnerSalaryMode,
            spinnerQualification, spinnerAccountType, spinnerFromWhere, spinnerSelectBank;
    String sSpinnerTypeOfCompany, sSpinnerQualification, sSpinnerFromWhere;
    Button btnSubmitSalaried;
    String month_name, month_name1, month_name2, selecteddatejoining, selecteddateestd;
    Calendar myCalendar = Calendar.getInstance();
    private DatePickerDialog joiningDatePickerDialogAlert;
    private DatePickerDialog companyEstabishmentDatePickerDialogAlert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sunil_loanuser);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        initView();
        initProgressBar();
        getAccountType();
        getSelectBank();
        getLoanFrom();
        getQualification();
        getTypeOfComapny();
        getModeOfSalary();
        getYear();
        getMonth();
        getValue();
    }

    private void getValue() {
        Intent i = getIntent();
        id = i.getStringExtra("id");
        loanType = i.getStringExtra("loantype");
    }

    private void initView() {
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setTitle(SharedPref.loantypes);
        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (ac != null) {
            ac.setDisplayHomeAsUpEnabled(true);
        }

        companyname = (AutoCompleteTextView) findViewById(R.id.companyname);
        linearLayoutPosition = (LinearLayout) findViewById(R.id.linearLayoutPosition);
        linearLayout7 = (LinearLayout) findViewById(R.id.linearLayout7);
        sunilcompanyname = (AutoCompleteTextView) findViewById(R.id.sunilcompanyname);
        linearLayoutJoined = (LinearLayout) findViewById(R.id.linearLayoutJoined);
        editTextPostition = (EditText) findViewById(R.id.editTextPostition);
        editTextCompanyAddress = (EditText) findViewById(R.id.editTextCompanyAddress);
        editTextCompanyAddress1 = (EditText) findViewById(R.id.editTextCompanyAddress1);
        editTextCompanyAddressState = (AutoCompleteTextView) findViewById(R.id.editTextCompanyAddressState);
        editTextCompanyAddressCity = (AutoCompleteTextView) findViewById(R.id.editTextCompanyAddressCity);
        editTextCompanyAddressPinCode = (EditText) findViewById(R.id.editTextCompanyAddressPinCode);
        editTextPersonalStd = (EditText) findViewById(R.id.editTextPersonalStd);
        editTextPrsonalPhone = (EditText) findViewById(R.id.editTextPrsonalPhone);
        editTextJoingDate = (EditText) findViewById(R.id.editTextJoingDate);
        editTextStd = (EditText) findViewById(R.id.editTextStd);
        editTextSelfCompanyPhone = (EditText) findViewById(R.id.editTextSelfCompanyPhone);
        editTextSelfCompanyEstablishedDate = (EditText) findViewById(R.id.editTextSelfCompanyEstablishedDate);
        editTextMonthlyIncome = (EditText) findViewById(R.id.editTextMonthlyIncome);
        editTextAmount = (EditText) findViewById(R.id.editTextAmount);
        editTextLoanTenure = (EditText) findViewById(R.id.editTextLoanTenure);
        editTextEMI = (EditText) findViewById(R.id.editTextEMI);
        radioGroupTypeOfEmployee = (RadioGroup) findViewById(R.id.radioGroupTypeOfEmployee);
        radioGroupProof = (RadioGroup) findViewById(R.id.radioGroupProof);
        radioGroupSelfProof = (RadioGroup) findViewById(R.id.radioGroupSelfProof);
        radioButtonContinue = (RadioGroup) findViewById(R.id.radioButtonContinue);
        radioGroupRePayment = (RadioGroup) findViewById(R.id.radioGroupRePayment);
        radioButtonSalaried = (RadioButton) findViewById(R.id.radioButtonSalaried);
        radioButtonSelfEmployed = (RadioButton) findViewById(R.id.radioButtonSelfEmployed);
        radioButtonSalarySlip = (RadioButton) findViewById(R.id.radioButtonSalarySlip);
        radioButtonAccountStatement = (RadioButton) findViewById(R.id.radioButtonAccountStatement);
        radioButtonITR = (RadioButton) findViewById(R.id.radioButtonITR);
        radioButtonRegistrationCertificate = (RadioButton) findViewById(R.id.radioButtonRegistrationCertificate);
        radioButtonPanCard = (RadioButton) findViewById(R.id.radioButtonPanCard);
        radioButtonYes = (RadioButton) findViewById(R.id.radioButtonYes);
        radioButtonNo = (RadioButton) findViewById(R.id.radioButtonNo);
        radioButtonRepaymentYes = (RadioButton) findViewById(R.id.radioButtonRepaymentYes);
        radioButtonRepaymentNo = (RadioButton) findViewById(R.id.radioButtonRepaymentNo);
        linearLayoutSalaried = (LinearLayout) findViewById(R.id.linearLayoutSalaried);
        linearLayoutSelfEmployed = (LinearLayout) findViewById(R.id.linearLayoutSelfEmployed);
        linearLayoutYes = (LinearLayout) findViewById(R.id.linearLayoutYes);
        linearLayoutEMI = (LinearLayout) findViewById(R.id.linearLayoutEMI);
        btnSubmitSalaried = (Button) findViewById(R.id.btnSubmitSalaried);
        radioGroupLoanTaken = (RadioGroup) findViewById(R.id.radioGroupLoanTaken);
        radioButtonLoanTakenYes = (RadioButton) findViewById(R.id.radioButtonLoanTakenYes);
        radioButtonLoanTakenNo = (RadioButton) findViewById(R.id.radioButtonLoanTakenNo);
        radioGroupMailingAddress = (RadioGroup) findViewById(R.id.radioGroupMailingAddress);
        radioButtonOffice = (RadioButton) findViewById(R.id.radioButtonOffice);
        radioButtonCurrent = (RadioButton) findViewById(R.id.radioButtonCurrent);
        radioButtonPermanent = (RadioButton) findViewById(R.id.radioButtonPermanent);

        btnSubmitSalaried.setOnClickListener(this);

        ArrayAdapter<String> ada1 = new ArrayAdapter<String>(SunilProceedLoan.this,
                android.R.layout.select_dialog_singlechoice, list1);
        editTextCompanyAddressCity.setThreshold(2);
        editTextCompanyAddressCity.setAdapter(ada1);
        editTextCompanyAddressCity.setTextColor(Color.BLACK);
        ArrayAdapter<String> adacompanyname = new ArrayAdapter<String>(SunilProceedLoan.this,
                android.R.layout.select_dialog_singlechoice, list6);
        companyname.setThreshold(2);
        companyname.setAdapter(adacompanyname);
        companyname.setTextColor(Color.BLACK);

        ArrayAdapter<String> ada3 = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.select_dialog_singlechoice, list3);
        editTextCompanyAddressState.setThreshold(2);
        editTextCompanyAddressState.setAdapter(ada3);
        editTextCompanyAddressState.setTextColor(Color.BLACK);

        ArrayAdapter<String> ada5 = new ArrayAdapter<String>(SunilProceedLoan.this,
                android.R.layout.select_dialog_singlechoice, list5);
        companyname.setThreshold(1);
        companyname.setAdapter(ada5);
        companyname.setTextColor(Color.BLACK);
        ArrayAdapter<String> ada12 = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.select_dialog_singlechoice, list7);
        sunilcompanyname.setThreshold(1);
        sunilcompanyname.setAdapter(ada12);
        sunilcompanyname.setTextColor(Color.BLACK);
        new searchexample3().execute(SharedPref.state_api);
        new doServerCall().execute(SharedPref.company_name);
        new searchexample1().execute(SharedPref.city_api);
        new searchexample8().execute(SharedPref.company_name);
        new searchexample5().execute(SharedPref.company_name);

        radioGroupRePayment.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroupRePayment.findViewById(checkedId);
                int index = radioGroupRePayment.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button
                        linearLayoutEMI.setVisibility(View.GONE);
                        editTextEMI.setText("");
                        break;
                    case 1: // secondbutton
                        linearLayoutEMI.setVisibility(View.VISIBLE);

                        break;
                }
            }
        });
        radioGroupLoanTaken.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroupLoanTaken.findViewById(checkedId);
                int index = radioGroupLoanTaken.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button

                        linearLayout7.setVisibility(View.VISIBLE);

                        break;
                    case 1: // secondbutton

                        linearLayout7.setVisibility(View.GONE);
                        editTextAmount.setText("");
                        editTextLoanTenure.setText("");
                        radioButtonRepaymentYes.setChecked(false);
                        radioButtonRepaymentNo.setChecked(false);

                        break;
                }
            }
        });
        radioGroupTypeOfEmployee.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroupTypeOfEmployee.findViewById(checkedId);
                int index = radioGroupTypeOfEmployee.indexOfChild(radioButton);
                switch (index) {
                    case 0: // first button

                        linearLayoutSalaried.setVisibility(View.VISIBLE);
                        linearLayoutSelfEmployed.setVisibility(View.GONE);
                        linearLayoutJoined.setVisibility(View.VISIBLE);
                        linearLayoutPosition.setVisibility(View.VISIBLE);
                        break;
                    case 1: // secondbutton
                        linearLayoutSalaried.setVisibility(View.VISIBLE);
                        linearLayoutSelfEmployed.setVisibility(View.VISIBLE);
                        linearLayoutJoined.setVisibility(View.GONE);
                        linearLayoutPosition.setVisibility(View.GONE);
                        break;
                }
            }
        });

        radioGroupProof.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroupProof.findViewById(checkedId);
                int index = radioGroupProof.indexOfChild(radioButton);
                // Add logic here
                switch (index) {
                    case 0: // first button


                        break;
                    case 1: // secondbutton

                        break;
                }
            }
        });

        final DatePickerDialog.OnDateSetListener dateJoining = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                SimpleDateFormat month_date = new SimpleDateFormat("dd");
                month_name = month_date.format(myCalendar.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar.getTime());
                Log.i("month", "" + month_name2);

                companyJoiningDate();
            }

            private void companyJoiningDate() {
                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddatejoining = sdf.format(myCalendar.getTime());
                editTextJoingDate.setText(selecteddatejoining);

                Log.i("Joining Date", selecteddatejoining);
            }
        };

        final DatePickerDialog.OnDateSetListener dateEstd = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                SimpleDateFormat month_date = new SimpleDateFormat("dd");
                month_name = month_date.format(myCalendar.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar.getTime());
                Log.i("month", "" + month_name2);

                companyEstablishedDate();
            }


            private void companyEstablishedDate() {

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddateestd = sdf.format(myCalendar.getTime());
                editTextSelfCompanyEstablishedDate.setText(selecteddateestd);

                Log.i("Estd Date", selecteddateestd);

            }
        };


        editTextJoingDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                joiningDatePickerDialogAlert =
                new DatePickerDialog(SunilProceedLoan.this, dateJoining, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                joiningDatePickerDialogAlert.getDatePicker().setMaxDate(System.currentTimeMillis());
                joiningDatePickerDialogAlert.show();

            }
        });

        editTextSelfCompanyEstablishedDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                companyEstabishmentDatePickerDialogAlert =
                new DatePickerDialog(SunilProceedLoan.this, dateEstd, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH));
                companyEstabishmentDatePickerDialogAlert.getDatePicker().setMaxDate(System.currentTimeMillis());
                companyEstabishmentDatePickerDialogAlert.show();
            }
        });
    }


    public void initProgressBar() {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
    }

    /**
     * show dialog
     */
    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    /**
     * hide dialog.
     */
    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void getAccountType() {
        spinnerAccountType = (Spinner) findViewById(R.id.spinnerAccountType);
        List<String> accountList = new ArrayList<>();
        accountList.add("Select");
        accountList.add("Savings Account");
        accountList.add("Salary Account");
        accountList.add("Current Account");
        accountList.add("Others");
        ArrayAdapter<String> accountdataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, accountList);

        spinnerAccountType.setAdapter(accountdataAdapter1);
    }

    public void getSelectBank() {
        spinnerSelectBank = (Spinner) findViewById(R.id.spinnerSelectBank);
        List<String> bankList = new ArrayList<>();
        bankList.add("Select");
        bankList.add("AXIS");
        bankList.add("HDFC");
        bankList.add("ICICI");
        bankList.add("SBI");
        bankList.add("Kotak Mahindra ");
        bankList.add("IDFC ");
        bankList.add("Other ");
        ArrayAdapter<String> bankdataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, bankList);

        spinnerSelectBank.setAdapter(bankdataAdapter1);
    }

    public void getYear() {
        ediTextYear = (Spinner) findViewById(R.id.ediTextYear);
        List<String> bankList = new ArrayList<>();
        bankList.add("Select");
        bankList.add("0");
        bankList.add("1");
        bankList.add("2");
        bankList.add("3");
        bankList.add("4");
        bankList.add("5 ");
        bankList.add("6 ");
        bankList.add("7 ");
        bankList.add("8 ");
        bankList.add("9 ");
        bankList.add("10 ");
        bankList.add("between than 10 to 15 ");
        bankList.add("more than 15 to 20 ");
        ArrayAdapter<String> bankdataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, bankList);

        ediTextYear.setAdapter(bankdataAdapter1);
    }

    public void getMonth() {
        editTextMonth = (Spinner) findViewById(R.id.editTextMonth);
        List<String> bankList = new ArrayList<>();
        bankList.add("Select");
        bankList.add("0");
        bankList.add("1");
        bankList.add("2");
        bankList.add("3");
        bankList.add("4");
        bankList.add("5 ");
        bankList.add("6 ");
        bankList.add("7 ");
        bankList.add("8 ");
        bankList.add("9 ");
        bankList.add("10 ");
        bankList.add("11 ");

        ArrayAdapter<String> bankdataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, bankList);

        editTextMonth.setAdapter(bankdataAdapter1);
    }


    public void getLoanFrom() {
        spinnerFromWhere = (Spinner) findViewById(R.id.spinnerFromWhere);
        List<String> loanBankList = new ArrayList<>();
        loanBankList.add("Select");
        loanBankList.add("AXIS");
        loanBankList.add("HDFC");
        loanBankList.add("ICICI");
        loanBankList.add("SBI");
        loanBankList.add("Kotak Mahindra ");
        loanBankList.add("IDFC ");
        loanBankList.add("Other ");
        ArrayAdapter<String> loandataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, loanBankList);

        spinnerFromWhere.setAdapter(loandataAdapter1);
    }

    public void getQualification() {
        spinnerQualification = (Spinner) findViewById(R.id.spinnerQualification);
        List<String> qualificationList = new ArrayList<>();
        qualificationList.add("Select");
        qualificationList.add("10th Pass");
        qualificationList.add("12th Pass");
        qualificationList.add("Graduate");
        qualificationList.add("Post Graduate");
        qualificationList.add("Doctorate");
        qualificationList.add("Professional");

        ArrayAdapter<String> qualificationdataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, qualificationList);

        spinnerQualification.setAdapter(qualificationdataAdapter1);
    }

    public void getTypeOfComapny() {
        spinnerTypeOfCompany = (Spinner) findViewById(R.id.spinnerTypeOfCompany);
        List<String> companyTypeList = new ArrayList<>();
        companyTypeList.add("Select");
        companyTypeList.add("PVT LTD");
        companyTypeList.add("PUBLIC");
        companyTypeList.add("PARTNERSHIP");
        companyTypeList.add("GOVT. SECTOR");

        ArrayAdapter<String> companyListdataAdapter1 = new ArrayAdapter<>(this,
                R.layout.spinner_item, companyTypeList);

        spinnerTypeOfCompany.setAdapter(companyListdataAdapter1);
    }

    public void getModeOfSalary() {
        spinnerSalaryMode = (Spinner) findViewById(R.id.spinner_salary_mode);
        List<String> salaryModeList = new ArrayList<>();
        salaryModeList.add("Select");
        salaryModeList.add("By Cheque");
        salaryModeList.add("By Cash");
        salaryModeList.add("Account Transfer");

        ArrayAdapter<String> salaryModeAdapter = new ArrayAdapter<>(this,
                R.layout.spinner_item, salaryModeList);

        spinnerSalaryMode.setAdapter(salaryModeAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnSubmitSalaried) {
            onButtonAddClick();
        }
    }

    private void onButtonAddClick() {
        sediTextYear = ediTextYear.getSelectedItem().toString();
        seditTextMonth = editTextMonth.getSelectedItem().toString();
        sSpinnerTypeOfCompany = spinnerTypeOfCompany.getSelectedItem().toString();
        SharedPref.salaryModeValue = spinnerSalaryMode.getSelectedItem().toString();
        sSpinnerQualification = spinnerQualification.getSelectedItem().toString();
        SharedPref.sSpinnerAccountType = spinnerAccountType.getSelectedItem().toString();
        sSpinnerFromWhere = spinnerFromWhere.getSelectedItem().toString();
        SharedPref.sSpinnerSelectBank = spinnerSelectBank.getSelectedItem().toString();
        UserInformation.seditTextComapnyName = sunilcompanyname.getText().toString();
        UserInformation.seditTextPostition = editTextPostition.getText().toString();
        UserInformation.seditTextCompanyAddress = editTextCompanyAddress.getText().toString();
        UserInformation.seditTextCompanyAddress1 = editTextCompanyAddress1.getText().toString();
        UserInformation.seditTextCompanyAddressState = editTextCompanyAddressState.getText().toString();
        UserInformation.seditTextCompanyAddressCity = editTextCompanyAddressCity.getText().toString();
        UserInformation.seditTextCompanyAddressPinCode = editTextCompanyAddressPinCode.getText().toString();
        seditTextJoingDate = editTextJoingDate.getText().toString();
        seditTextStd = editTextStd.getText().toString();
        seditTextSelfCompanyPhone = editTextSelfCompanyPhone.getText().toString();
        seditTextPersonalStd = editTextPersonalStd.getText().toString();
        seditTextPrsonalPhone = editTextPrsonalPhone.getText().toString();

        seditTextSelfCompanyEstablishedDate = editTextSelfCompanyEstablishedDate.getText().toString();
        SharedPref.seditTextMonthlyIncome = editTextMonthlyIncome.getText().toString();
        seditTextAmount = editTextAmount.getText().toString();
        SharedPref.seditTextLoanTenure = editTextLoanTenure.getText().toString();
        SharedPref.seditTextEMI = editTextEMI.getText().toString();

        if (radioButtonSalaried.isChecked()) {
            UserInformation.sRadioButtonTypeOfEmployee = "Salaried";
        }
        if (radioButtonSelfEmployed.isChecked()) {
            UserInformation.sRadioButtonTypeOfEmployee = "Self Employed";
        }
        if (radioButtonSalarySlip.isChecked()) {
            sRadioButtonProof = "Salary Slip";
        }
        if (radioButtonAccountStatement.isChecked()) {
            sRadioButtonProof = "Account Statement";
        }
        if (radioButtonITR.isChecked()) {
            sRadioButtonProof = "ITR";
        }
        if (radioButtonRegistrationCertificate.isChecked()) {
            sRadioButtonProof = "Registration Certificate";
        }
        if (radioButtonPanCard.isChecked()) {
            sRadioButtonProof = "Pan Card";
        }
        if (radioButtonYes.isChecked()) {
            sRadioButtonContinuity = "Yes";
        }
        if (radioButtonNo.isChecked()) {
            sRadioButtonContinuity = "No";
        }
        if (radioButtonRepaymentYes.isChecked()) {
            sRadioButtonRepayment = "Yes";
        }
        if (radioButtonRepaymentNo.isChecked()) {
            sRadioButtonRepayment = "No";
        }
        if (radioButtonLoanTakenNo.isChecked()) {
            sRadioButtonLoanTaken = "No";
        }
        if (radioButtonLoanTakenYes.isChecked()) {
            sRadioButtonLoanTaken = "Yes";
        }
        if (radioButtonOffice.isChecked()) {
            sRadioButtonMailingAddress = "Office";
        }
        if (radioButtonPermanent.isChecked()) {
            sRadioButtonMailingAddress = "Permanent";
        }
        if (radioButtonCurrent.isChecked()) {
            sRadioButtonMailingAddress = "Current";
        }
        validatePostAddFields();
    }

    private void validatePostAddFields() {

        try {

            if (radioGroupTypeOfEmployee.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select your type of employment", Toast.LENGTH_SHORT).show();

            } else if (TextUtils.isEmpty(UserInformation.seditTextComapnyName)) {
                sunilcompanyname.setError("Please select name of your company");
                sunilcompanyname.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(UserInformation.seditTextCompanyAddress)) {
                editTextCompanyAddress.setError("Field cannot be empty");
                editTextCompanyAddress.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(UserInformation.seditTextCompanyAddress1)) {
                editTextCompanyAddress1.setError("Field cannot be empty");
                editTextCompanyAddress1.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(UserInformation.seditTextCompanyAddressCity)) {
                editTextCompanyAddressCity.setError("Field cannot be empty");
                editTextCompanyAddressCity.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(UserInformation.seditTextCompanyAddressState)) {
                editTextCompanyAddressState.setError("Field cannot be empty");
                editTextCompanyAddressState.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(UserInformation.seditTextCompanyAddressPinCode)) {
                editTextCompanyAddressPinCode.setError("Field cannot be empty");
                editTextCompanyAddressPinCode.requestFocusFromTouch();

            } else if (UserInformation.seditTextCompanyAddressPinCode.length()<6) {
                editTextCompanyAddressPinCode.setError("Please enter correct pincode");
                editTextCompanyAddressPinCode.requestFocusFromTouch();

            } else if (radioButtonSalaried.isChecked() && TextUtils.isEmpty(seditTextJoingDate)) {
                Toast.makeText(this, "Please enter the joining date", Toast.LENGTH_SHORT).show();
                editTextJoingDate.requestFocusFromTouch();

            } else if (radioButtonSalaried.isChecked() && (radioGroupProof.getCheckedRadioButtonId() == -1)) {
                Toast.makeText(this, "Please select proof with respect to the company", Toast.LENGTH_SHORT).show();

            } else if (radioButtonSalaried.isChecked() && (TextUtils.isEmpty(UserInformation.seditTextPostition))) {
                editTextPostition.setError("Please enter your position");
                editTextPostition.requestFocusFromTouch();

            } else if (radioButtonSelfEmployed.isChecked() && TextUtils.isEmpty(seditTextSelfCompanyEstablishedDate)) {
                Toast.makeText(this, "Please enter the date of establishment of the company", Toast.LENGTH_SHORT).show();
                editTextSelfCompanyEstablishedDate.requestFocusFromTouch();

            } else if (radioButtonSelfEmployed.isChecked() && radioGroupSelfProof.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select proof with respect to the company", Toast.LENGTH_SHORT).show();

            } else if (radioButtonSalaried.isChecked() && sediTextYear.equalsIgnoreCase("select")) {
                TextView errorText = (TextView) ediTextYear.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
//                errorText.setText("Please Select experience in years");//changes the selected item text to this
                errorText.setText("Experience in years");//changes the selected item text to this

                ediTextYear.requestFocusFromTouch();

            } else if (radioButtonSalaried.isChecked() && seditTextMonth.equalsIgnoreCase("select")) {
                TextView errorText = (TextView) editTextMonth.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
//                errorText.setText("Please Select experience in months");//changes the selected item text to this
                errorText.setText("experience in months");//changes the selected item text to this

                editTextMonth.requestFocusFromTouch();

            } else if (spinnerSalaryMode.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerSalaryMode.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please Select Mode of Salary");//changes the selected item text to this

                spinnerSalaryMode.requestFocusFromTouch();

            } else if (spinnerTypeOfCompany.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {
                TextView errorText = (TextView) spinnerTypeOfCompany.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please Select Type of Company");//changes the selected item text to this

                spinnerTypeOfCompany.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(seditTextStd)) {
//                editTextStd.setError("Please enter the std code");
                editTextStd.setError("std code");
                editTextStd.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(seditTextSelfCompanyPhone)) {
                editTextSelfCompanyPhone.setError("Please enter the phone no.");
                editTextSelfCompanyPhone.requestFocusFromTouch();

            } else if (seditTextSelfCompanyPhone.length()<7 && seditTextSelfCompanyPhone.length()>10) {
                editTextSelfCompanyPhone.setError("Please enter some valid phone no.");
                editTextSelfCompanyPhone.requestFocusFromTouch();

            } else if (spinnerQualification.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {

                TextView errorText = (TextView) spinnerQualification.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please Select Qualification");//changes the selected item text to this

                spinnerQualification.requestFocusFromTouch();

            } else if (TextUtils.isEmpty(SharedPref.seditTextMonthlyIncome)) {
                editTextMonthlyIncome.setError("Please enter your monthly income");
                editTextMonthlyIncome.requestFocusFromTouch();

            } else if (spinnerAccountType.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {

                TextView errorText = (TextView) spinnerAccountType.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please Select Account Type");//changes the selected item text to this
                spinnerAccountType.requestFocusFromTouch();

            } else if (spinnerSelectBank.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {

                TextView errorText = (TextView) spinnerSelectBank.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please Select a Bank");//changes the selected item text to this

                spinnerSelectBank.requestFocusFromTouch();


            } else if (radioButtonContinue.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select if you have continuity of business", Toast.LENGTH_SHORT).show();
            } else if (radioGroupLoanTaken.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select if you have taken loan in the past", Toast.LENGTH_SHORT).show();

            } else if (radioButtonLoanTakenYes.isChecked() && (sSpinnerFromWhere.equals("Select"))) {
                TextView errorText = (TextView) spinnerFromWhere.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);
                errorText.setText("Please Select From Where");
                spinnerAccountType.requestFocusFromTouch();

            } else if (radioButtonLoanTakenYes.isChecked() && TextUtils.isEmpty(seditTextAmount)) {
                editTextAmount.setError("Please Enter the Amount");
                editTextAmount.requestFocusFromTouch();

            } else if (radioButtonLoanTakenYes.isChecked() && TextUtils.isEmpty(SharedPref.seditTextLoanTenure)) {
                editTextLoanTenure.setError("Please Enter the loan tenure");
                editTextLoanTenure.requestFocusFromTouch();

            } else if (radioButtonLoanTakenYes.isChecked() && radioGroupRePayment.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select whether loan repayment is done", Toast.LENGTH_SHORT).show();

            } else if (radioButtonLoanTakenYes.isChecked() && radioButtonRepaymentNo.isChecked() && SharedPref.seditTextEMI.isEmpty()){
                Toast.makeText(this, "Please fill the EMI that you pay", Toast.LENGTH_SHORT).show();

            } else if (radioGroupMailingAddress.getCheckedRadioButtonId() == -1) {
                Toast.makeText(this, "Please select mailing address", Toast.LENGTH_SHORT).show();

            } else {
                new sendDataToServer().execute();
            }
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(SunilProceedLoan.this);
        // builder.setCancelable(false);
        builder.setTitle("");
        builder.setMessage("Your application is not complete. Incomplete application would not get processed. Proceed exiting the form?");
        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in1 = new Intent(SunilProceedLoan.this, MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in1);
                finish();
            }
        });
        builder.setNegativeButton("No",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert=builder.create();
        alert.show();
    }

    private class searchexample1 extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost(arg0[0]);

                HttpResponse hr = hc.execute(hp);

                int status = hr.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity he = hr.getEntity();
                    String data = EntityUtils.toString(he);

                    JSONArray array1 = new JSONArray(data);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject ob2 = array1.getJSONObject(i);
                        String skill = ob2.getString("name");

                        list1.add(skill);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> ad7 = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.simple_dropdown_item_1line, list1);

            editTextCompanyAddressCity.setAdapter(ad7);
            editTextCompanyAddressCity.setThreshold(2);

        }
    }

    private class doServerCall extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost(arg0[0]);

                HttpResponse hr = hc.execute(hp);

                int status = hr.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity he = hr.getEntity();
                    String data = EntityUtils.toString(he);

                    JSONArray array1 = new JSONArray(data);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject ob2 = array1.getJSONObject(i);
                        String skill = ob2.getString("company_name");
                        list6.add(skill);
                    }
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> ad7 = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.simple_dropdown_item_1line, list6);

            companyname.setAdapter(ad7);
            companyname.setThreshold(2);
        }
    }

    private class searchexample8 extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost(arg0[0]);

                HttpResponse hr = hc.execute(hp);

                int status = hr.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity he = hr.getEntity();
                    String data = EntityUtils.toString(he);

                    JSONArray array1 = new JSONArray(data);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject ob2 = array1.getJSONObject(i);
                        String skill = ob2.getString("company_name");

                        list7.add(skill);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> ad7 = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.simple_dropdown_item_1line, list7);

            sunilcompanyname.setAdapter(ad7);
            sunilcompanyname.setThreshold(2);

        }
    }

    private class searchexample3 extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost(arg0[0]);

                HttpResponse hr = hc.execute(hp);

                int status = hr.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity he = hr.getEntity();
                    String data = EntityUtils.toString(he);

                    JSONArray array1 = new JSONArray(data);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject ob2 = array1.getJSONObject(i);
                        String skill = ob2.getString("name");

                        list3.add(skill);
                    }
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);


            ArrayAdapter<String> adeditTextPermanentAdressState = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.simple_dropdown_item_1line, list3);

            editTextCompanyAddressState.setAdapter(adeditTextPermanentAdressState);
            editTextCompanyAddressState.setThreshold(2);

        }
    }

    private class searchexample5 extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost(arg0[0]);

                HttpResponse hr = hc.execute(hp);

                int status = hr.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity he = hr.getEntity();
                    String data = EntityUtils.toString(he);

                    JSONArray array1 = new JSONArray(data);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject ob2 = array1.getJSONObject(i);
                        String skill = ob2.getString("company_name");

                        list5.add(skill);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> ed = new ArrayAdapter<String>(SunilProceedLoan.this, android.R.layout.simple_dropdown_item_1line, list5);

            companyname.setAdapter(ed);
            companyname.setThreshold(2);
        }
    }

    private class sendDataToServer extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SunilProceedLoan.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("random", SharedPref.random));
//
            param.add(new BasicNameValuePair("loan_type", loanType));
            param.add(new BasicNameValuePair("curr_emp", UserInformation.sRadioButtonTypeOfEmployee));
            param.add(new BasicNameValuePair("emp_compny_name", UserInformation.seditTextComapnyName));
            param.add(new BasicNameValuePair("emp_posi", UserInformation.seditTextPostition));
            param.add(new BasicNameValuePair("city", SharedPref.city));
            param.add(new BasicNameValuePair("emp_off_add1", UserInformation.seditTextCompanyAddress));
            param.add(new BasicNameValuePair("emp_off_add2", UserInformation.seditTextCompanyAddress1));
            param.add(new BasicNameValuePair("emp_off_state", UserInformation.seditTextCompanyAddressState));
            param.add(new BasicNameValuePair("emp_off_city", UserInformation.seditTextCompanyAddressCity));
            param.add(new BasicNameValuePair("emp_off_pincode", UserInformation.seditTextCompanyAddressPinCode));
            param.add(new BasicNameValuePair("emp_join_date_curr", seditTextJoingDate));
            param.add(new BasicNameValuePair("gps_location", SharedPref.Location_gps));
            param.add(new BasicNameValuePair("per_std", seditTextPersonalStd));
            param.add(new BasicNameValuePair("per_phone", seditTextPrsonalPhone));
            param.add(new BasicNameValuePair("total_exp_year", sediTextYear));
            param.add(new BasicNameValuePair("total_exp_month", seditTextMonth));
            param.add(new BasicNameValuePair("emp_off_proof", sRadioButtonProof));
            param.add(new BasicNameValuePair("type_of_company", sSpinnerTypeOfCompany));
            param.add(new BasicNameValuePair("salary_mode", SharedPref.salaryModeValue));
            param.add(new BasicNameValuePair("emp_off_phone", seditTextSelfCompanyPhone));
            param.add(new BasicNameValuePair("emp_off_std", seditTextStd));
            param.add(new BasicNameValuePair("emp_compny_estd", seditTextSelfCompanyEstablishedDate));
            param.add(new BasicNameValuePair("emp_quali", sSpinnerQualification));
            param.add(new BasicNameValuePair("emp_month_income", SharedPref.seditTextMonthlyIncome));
            param.add(new BasicNameValuePair("emp_continuity", sRadioButtonContinuity));
            param.add(new BasicNameValuePair("emp_acc_type", SharedPref.sSpinnerAccountType));
            param.add(new BasicNameValuePair("emp_bank_name", SharedPref.sSpinnerSelectBank));
            param.add(new BasicNameValuePair("emp_past_loan", sRadioButtonLoanTaken));
            param.add(new BasicNameValuePair("emp_past_loan_where", sSpinnerFromWhere));
            param.add(new BasicNameValuePair("emp_past_loan_amount", seditTextAmount));
            param.add(new BasicNameValuePair("emp_loan_tenure", SharedPref.seditTextLoanTenure));
            param.add(new BasicNameValuePair("emp_repay_complet", sRadioButtonRepayment));
            param.add(new BasicNameValuePair("emp_pay_last_emi", SharedPref.seditTextEMI));
            param.add(new BasicNameValuePair("emp_mail_add", sRadioButtonMailingAddress));

            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.loaninsurance5, "POST", param);
            String message = "";
            try {
                message = jobj.getString("msg");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("result", "" + result);

            if (dialog != null && dialog.isShowing())
                dialog.dismiss();

            Intent i = new Intent(SunilProceedLoan.this, SubmitLoanQuery.class);

            i.putExtra("id", id);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}


