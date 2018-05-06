package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import shared_pref.SharedPref;
import shared_pref.UserInformation;

/**
 * Created by Tamanna on 7/28/2016.
 */
public class Loan_queryData extends AppCompatActivity {
       Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    TextView name, email,subcatory, tvadhars,tvpans,dob, date, income, bankAccount,bankAccountType, loantype, amount,
            tvCurrentlyEmployedAs, timeperiod;
    TextView tvLoanReplacement,tvEmiAmount;
    private TextView tvGender , tvResType, tvAddProof, tvCurrentPosition, tvCompanyAddress, tvCompanyAddress1;
    private TextView tvCurrentAddress, tvCurrentAddress1, tvCurrentState,tvCurrentCity,tvCurrentPincode;
    private TextView tvPermanentAddress, tvPermanentAddress1, tvPermanentState,tvPermanentCity,tvPermanentPincode;
    private TextView tvCompanyState,tvCompanyCity,tvCompanyPincode, tvSalaryMode, tvCompanyName ;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_querydata);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Loan Query Data");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        initializeFields();
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void onResume() {
        super.onResume();
        new getdata().execute();
    }

    public void initializeFields() {

        name = (TextView) findViewById(R.id.loanquerydata_tvname);
        email = (TextView) findViewById(R.id.loanquerydata_tvmail);
        dob = (TextView) findViewById(R.id.loanquerydata_tvdob);
        income = (TextView) findViewById(R.id.loanquerydata_tvincome_loan);
        bankAccountType = (TextView) findViewById(R.id.loanquerydata_bank_account_type);
        bankAccount = (TextView) findViewById(R.id.loanquerydata_tvaccount);
        tvpans=(TextView) findViewById(R.id.loanquerydata_tvpan);
        tvadhars=(TextView) findViewById(R.id.loanquerydata_tvadhar);
        tvGender=(TextView) findViewById(R.id.loanquerydata_txtGender);
        tvResType=(TextView) findViewById(R.id.loanquerydata_txtResidenceType);
        tvAddProof=(TextView) findViewById(R.id.loanquerydata_txtAddressProof);

        //////////////////////////current address////////////////////////

        
        //////////////////Loan details///////////////////////////
        loantype = (TextView) findViewById(R.id.loanquerydata_tvloan);
        subcatory=(TextView) findViewById(R.id.loanquerydata_tvsubcategory);
        amount = (TextView) findViewById(R.id.loanquerydata_tvloanamount);
        timeperiod = (TextView) findViewById(R.id.loanquerydata_tvtime);

        ////////////////////////////////employee details///////////////////
        tvCurrentlyEmployedAs = (TextView) findViewById(R.id.loanquerydata_txtCurrentEmployee);
//        tvCompanyName = (TextView) findViewById(R.id.loanquerydata_companyname);
        tvCurrentPosition = (TextView) findViewById(R.id.loanquerydata_txtPosition);
        tvCompanyAddress = (TextView) findViewById(R.id.loanquerydata_txtCompanyAddress);
        tvCompanyAddress1 = (TextView) findViewById(R.id.loanquerydata_txtComapnyAddress1);
        tvCompanyState = (TextView) findViewById(R.id.loanquerydata_txtCompanyState);
        tvCompanyCity = (TextView) findViewById(R.id.loanquerydata_txtCompanyCity);
        tvCompanyPincode = (TextView) findViewById(R.id.loanquerydata_txtCompanyPincode);

        tvSalaryMode = (TextView) findViewById(R.id.loanquerydata_txtSalaryMode);
        tvCompanyName = (TextView) findViewById(R.id.loanquerydata_companyname);

        tvLoanReplacement = (TextView) findViewById(R.id.loanquerydata_tvhaveloan_satus);
        tvEmiAmount = (TextView) findViewById(R.id.loanquerydata_tvemi);

        //////////////current address///////////////////

        tvCurrentAddress = (TextView) findViewById(R.id.loanquerydata_txtAddress);
        tvCurrentAddress1 = (TextView) findViewById(R.id.loanquerydata_txtAddress1);
        tvCurrentState = (TextView) findViewById(R.id.loanquerydata_txtState);
        tvCurrentCity = (TextView) findViewById(R.id.loanquerydata_txtCity);
        tvCurrentPincode = (TextView) findViewById(R.id.loanquerydata_txtPincode);

        //////////////permanent address///////////////////

        tvPermanentAddress = (TextView) findViewById(R.id.loanquerydata_txtPermanentAddress);
        tvPermanentAddress1 = (TextView) findViewById(R.id.loanquerydata_txtPermanentAddress1);
        tvPermanentState = (TextView) findViewById(R.id.loanquerydata_txtPermanentState);
        tvPermanentCity = (TextView) findViewById(R.id.loanquerydata_txtPermanentCity);
        tvPermanentPincode = (TextView) findViewById(R.id.loanquerydata_txtPermanentAddressPincode);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in = new Intent(Loan_queryData.this, MainActivity.class);
//        startActivity(in);
        finish();
    }

    private class getdata extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Loan_queryData.this);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id",id));
            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.user_loan_data, "POST", param);
            String message = "";
            try {

                JSONArray array = jobj.getJSONArray("products");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Log.i("JSON array", ""+jsonObject);

                    SharedPref.Sname = jsonObject.getString("name");
                    SharedPref.Semail = jsonObject.getString("email");
                    SharedPref.SDate = jsonObject.getString("date");
                    SharedPref.Sincome = jsonObject.getString("emp_month_income");
                    SharedPref.Sdob = jsonObject.getString("dob");
                    SharedPref.SbankAccountType = jsonObject.getString("emp_acc_type");
                    SharedPref.SBankName = jsonObject.getString("emp_bank_name");
                    SharedPref.Stvadhars = jsonObject.getString("adhar_card");
                    SharedPref.Stvpans = jsonObject.getString("pan_card");
                    SharedPref.Sloantype = jsonObject.getString("loan_type");
                    SharedPref.Ssubcatory = jsonObject.getString("subcategory_loan");
                    SharedPref.Samount = jsonObject.getString("borrow");
                    SharedPref.Stimeperiod = jsonObject.getString("borrow_time");
                    SharedPref.SResType = jsonObject.getString("residence_type");
                    SharedPref.SAddproof = jsonObject.getString("curr_resi_proof");
                    SharedPref.SGender = jsonObject.getString("gender");

                    SharedPref.SCurrentlyEmployedAs = jsonObject.getString("curr_emp");
                    SharedPref.SCurrentPosition = jsonObject.getString("emp_posi");
                    SharedPref.SCompanyName = jsonObject.getString("compny_name");
                    SharedPref.SSalaryMode = jsonObject.getString("salary_mode");
                    SharedPref.SCompanyAddress = jsonObject.getString("emp_off_add1");
                    SharedPref.SCompanyAddress1 = jsonObject.getString("emp_off_add2");
                    SharedPref.SCompanyState = jsonObject.getString("emp_off_state");
                    SharedPref.SCompanyCity = jsonObject.getString("emp_off_city");
                    SharedPref.SCompanyPincode = jsonObject.getString("emp_off_pincode");

                    SharedPref.SCurrentAddress = jsonObject.getString("curr_add_line1");
                    SharedPref.SCurrentAddress1 = jsonObject.getString("curr_add_line2");
                    SharedPref.SCurrentState = jsonObject.getString("curr_state");
                    SharedPref.SCurrentCity = jsonObject.getString("curr_city");
                    SharedPref.SCurrentPincode = jsonObject.getString("curr_pincode");

                    SharedPref.SPermanentAddress = jsonObject.getString("per_add_line1");
                    SharedPref.SPermanentAddress1 = jsonObject.getString("per_add_line2");
                    SharedPref.SPermanentState = jsonObject.getString("per_state");
                    SharedPref.SPermanentCity = jsonObject.getString("per_city");
                    SharedPref.SPermanentPincode = jsonObject.getString("per_pincode");

                    SharedPref.SPermanentPincode = jsonObject.getString("per_pincode");
                    SharedPref.Semi = jsonObject.getString("emp_pay_last_emi");

                }
            }
                catch(Exception e) {
                    e.printStackTrace();
                }
                return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

            name.setText(UserInformation.UserFirstName + " " + UserInformation.UserLastName);
            email.setText(SharedPref.Semail);
            income.setText(SharedPref.Sincome);
            dob.setText(SharedPref.Sdob);
            tvAddProof.setText(SharedPref.SAddproof);
            tvResType.setText(SharedPref.SResType);
            tvGender.setText(SharedPref.SGender);
//            date.setText(SharedPref.SDate);
            bankAccountType.setText(SharedPref.SbankAccountType);
            bankAccount.setText(SharedPref.SBankName);
            tvadhars.setText(SharedPref.Stvadhars);
            tvpans.setText(SharedPref.Stvpans);
            loantype.setText(SharedPref.Sloantype);
            subcatory.setText(SharedPref.Ssubcatory);
            amount.setText(SharedPref.Samount);
            timeperiod.setText(SharedPref.Stimeperiod);

            tvCurrentlyEmployedAs.setText(SharedPref.SCurrentlyEmployedAs);
            tvCompanyName.setText(SharedPref.SCompanyName);
            tvCurrentPosition.setText(SharedPref.SCurrentPosition);

            tvSalaryMode.setText(SharedPref.SSalaryMode);
            tvSalaryMode.setText(SharedPref.SSalaryMode);

            tvCompanyAddress.setText(SharedPref.SCompanyAddress);
            tvCompanyAddress1.setText(SharedPref.SCompanyAddress1);
            tvCompanyState.setText(SharedPref.SCompanyState);
            tvCompanyCity.setText(SharedPref.SCompanyCity);
            tvCompanyPincode.setText(SharedPref.SCompanyPincode);

            tvCurrentAddress.setText(SharedPref.SCurrentAddress);
            tvCurrentAddress1.setText(SharedPref.SCurrentAddress1);
            tvCurrentState.setText(SharedPref.SCurrentState);
            tvCurrentCity.setText(SharedPref.SCurrentCity);
            tvCurrentPincode.setText(SharedPref.SCurrentPincode);

            tvPermanentAddress.setText(SharedPref.SPermanentAddress);
            tvPermanentAddress1.setText(SharedPref.SPermanentAddress1);
            tvPermanentState.setText(SharedPref.SPermanentState);
            tvPermanentCity.setText(SharedPref.SPermanentCity);
            tvPermanentPincode.setText(SharedPref.SPermanentPincode);

            tvEmiAmount.setText(SharedPref.Semi);
        }
    }
}
