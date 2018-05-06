package loan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.BankDetail;
import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import shared_pref.SharedPref;
import shared_pref.UserInformation;

/**
 * Created by hp on 2/10/2017.
 */

public class EditData extends AppCompatActivity {
    EditText name, txtPosition, tvaccount, tvadhars, txtResidenceType, txtLastName, tvpans, dob, contact, income, salary_stms, salryaccount_type, city, employment, amount, timeperiod, haveloan_status, haveloan, emi, idproof, addproof, incproof, officeproof, bankproof, ownershipproof, businessproof, enq_types;
    Button button_ok;
    TextView email, loantype, subcatory, txtState, txtCity, txtPermanentState, txtCompanyName, txtabc, txtPermanentCity, txtCompanyCity, txtCompanyState;
    TextView txtLaonType;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    LinearLayout have_laty;
    EditText amnt_loan, where_loan, txtCurrentEmployee, txtAddressProof, tenure_loan;
    EditText txtAddress, txtAddress1, txtGender, txtPincode, txtPermanentAddress, txtPermanentAddress1, txtPermanentAddressPincode;
    EditText txtCompanyAddress, txtComapnyAddress1, txtComanyPincode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editdata);

        initializeFields();
        Intent i = getIntent();
        SharedPref.random = i.getStringExtra("id");

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setTitle("Edit Loan Form");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        name.setText(UserInformation.UserFirstName);
        txtCompanyName.setText(UserInformation.seditTextComapnyName);
        txtCompanyAddress.setText(UserInformation.seditTextCompanyAddress);
        txtComapnyAddress1.setText(UserInformation.seditTextCompanyAddress1);
        txtCompanyState.setText(UserInformation.seditTextCompanyAddressState);
        txtCurrentEmployee.setText(UserInformation.sRadioButtonTypeOfEmployee);
        txtCompanyCity.setText(UserInformation.seditTextCompanyAddressCity);
        txtComanyPincode.setText(UserInformation.seditTextCompanyAddressPinCode);
        txtAddressProof.setText(UserInformation.sProofAddress);
        txtLaonType.setText(SharedPref.loantypes);
        txtAddress.setText(UserInformation.seditTextCurrentAddress);
        txtPosition.setText(UserInformation.seditTextPostition);
        txtGender.setText(SharedPref.gender);
        txtResidenceType.setText(SharedPref.sRadioButtonResidenceType);
        txtAddress1.setText(UserInformation.seditTextCurrentAddress1);
        txtState.setText(UserInformation.seditTextCurrentAddressState);
        txtCity.setText(UserInformation.seditTextCurrentAddressCity);
        txtPincode.setText(UserInformation.seditTextCurrentAddressPincode);
        txtPermanentAddress.setText(UserInformation.seditTextPermanentAdress);
        txtPermanentAddress1.setText(UserInformation.seditTextPermanentAdress1);
        txtPermanentState.setText(UserInformation.seditTextPermanentAdressState);
        txtabc.setText(UserInformation.seditTextComapnyName);
        txtPermanentCity.setText(UserInformation.seditTextPermanentAdressCity);
        txtPermanentAddressPincode.setText(UserInformation.seditTextPermanentAdressPincode);
        salryaccount_type.setText(SharedPref.sSpinnerAccountType);
        contact.setText(SharedPref.contactloan);
        dob.setText(SharedPref.dob_loan);
        city.setText(SharedPref.city);
        employment.setText(SharedPref.emptype);
        tvadhars.setText(SharedPref.Adharcard_loan);
        tvpans.setText(SharedPref.pancard_loan);
        loantype.setText(SharedPref.loantypes);
        subcatory.setText(SharedPref.subcatory_loan);
        timeperiod.setText(SharedPref.time);
        amount.setText(SharedPref.loanmant);
        haveloan.setText(SharedPref.loan_amnt);
        where_loan.setText(SharedPref.Sloan_where);
        amnt_loan.setText(SharedPref.Sloan_amnt);
        idproof.setText(SharedPref.id_category);
        addproof.setText(SharedPref.add_proof);
        incproof.setText(SharedPref.income_proof);
        officeproof.setText(SharedPref.office_proof);
        salary_stms.setText(SharedPref.bankstm);
        ownershipproof.setText(SharedPref.ownership);
        businessproof.setText(SharedPref.countinuity);
        tvaccount.setText(SharedPref.sSpinnerSelectBank);
        email.setText(UserInformation.Email);
        income.setText(SharedPref.seditTextMonthlyIncome);
        emi.setText(SharedPref.seditTextEMI);
        txtLastName.setText(UserInformation.UserLastName);

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

    @Override
    protected void onResume() {
        super.onResume();


        if (haveloan.getText().toString().equals("Yes")) {
            have_laty.setVisibility(View.VISIBLE);
        } else {
            have_laty.setVisibility(View.GONE);
        }


        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getvalue();

                new insertusermain().execute();
            }
        });
    }


    public void initializeFields() {
        txtabc = (TextView) findViewById(R.id.txtabc);
        txtCompanyName = (TextView) findViewById(R.id.txtCompanyName);
        txtCompanyAddress = (EditText) findViewById(R.id.txtCompanyAddress);
        txtComapnyAddress1 = (EditText) findViewById(R.id.txtComapnyAddress1);
        txtCompanyState = (TextView) findViewById(R.id.txtCompanyState);
        txtCompanyCity = (TextView) findViewById(R.id.txtCompanyCity);
        txtComanyPincode = (EditText) findViewById(R.id.txtComanyPincode);
        txtPosition = (EditText) findViewById(R.id.txtPosition);
        txtCurrentEmployee = (EditText) findViewById(R.id.txtCurrentEmployee);
        txtResidenceType = (EditText) findViewById(R.id.txtResidenceType);
        txtGender = (EditText) findViewById(R.id.txtGender);
        txtAddress = (EditText) findViewById(R.id.txtAddress);
        txtAddress1 = (EditText) findViewById(R.id.txtAddress1);
        txtAddressProof = (EditText) findViewById(R.id.txtAddressProof);
        txtPosition = (EditText) findViewById(R.id.txtPosition);
        txtState = (TextView) findViewById(R.id.txtState);
        txtLaonType = (TextView) findViewById(R.id.txtLaonType);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtPincode = (EditText) findViewById(R.id.txtPincode);
        txtPermanentAddress = (EditText) findViewById(R.id.txtPermanentAddress);
        txtPermanentAddress1 = (EditText) findViewById(R.id.txtPermanentAddress1);
        txtPermanentState = (TextView) findViewById(R.id.txtPermanentState);
        txtPermanentCity = (TextView) findViewById(R.id.txtPermanentCity);
        txtPermanentAddressPincode = (EditText) findViewById(R.id.txtPermanentAddressPincode);
        name = (EditText) findViewById(R.id.tvname);
        email = (TextView) findViewById(R.id.tvmail);
        contact = (EditText) findViewById(R.id.tvcontact);
        income = (EditText) findViewById(R.id.tvincome_loan);
        salryaccount_type = (EditText) findViewById(R.id.tvsalstm);
        tvaccount = (EditText) findViewById(R.id.tvaccount);
        city = (EditText) findViewById(R.id.tvresidence);
        employment = (EditText) findViewById(R.id.tvemployment);
        tvadhars = (EditText) findViewById(R.id.tvadhar);
        dob = (EditText) findViewById(R.id.tvdob);
        tvpans = (EditText) findViewById(R.id.tvpan);

        loantype = (TextView) findViewById(R.id.tvloan);
        subcatory = (TextView) findViewById(R.id.tvsubcategory);
        amount = (EditText) findViewById(R.id.tvloanamount);
        timeperiod = (EditText) findViewById(R.id.tvtime);
        haveloan = (EditText) findViewById(R.id.tvhaveloan);
        haveloan_status = (EditText) findViewById(R.id.tvhaveloan_satus);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        amnt_loan = (EditText) findViewById(R.id.viewhaveloan_amnt);
        haveloan_status.setText(SharedPref.sRadioButtonRepayment);
        where_loan = (EditText) findViewById(R.id.viewhaveloan_where);
        tenure_loan = (EditText) findViewById(R.id.viewhaveloan_tenure);
        emi = (EditText) findViewById(R.id.tvemi);
        tenure_loan.setText(SharedPref.seditTextLoanTenure);
        idproof = (EditText) findViewById(R.id.tvid);
        addproof = (EditText) findViewById(R.id.tvaddprf);
        incproof = (EditText) findViewById(R.id.tvincomprf);
        officeproof = (EditText) findViewById(R.id.tvofcadprf);
        ownershipproof = (EditText) findViewById(R.id.tvowner);
        businessproof = (EditText) findViewById(R.id.tvbusncon);
        salary_stms = (EditText) findViewById(R.id.tvbankstat);
        button_ok = (Button) findViewById(R.id.button_submit);
        have_laty = (LinearLayout) findViewById(R.id.view_haveloanlay);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(EditData.this);
        // builder.setCancelable(false);
        builder.setTitle("");
        builder.setMessage("Your application is not complete. Incomplete application would not get processed. Proceed exiting the form?");
        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {


                Intent in1 = new Intent(EditData.this, MainActivity.class);
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

    public void getvalue() {
        UserInformation.seditTextLastName = txtLastName.getText().toString();
        UserInformation.Email = email.getText().toString();
        SharedPref.dob_loan = dob.getText().toString();
        SharedPref.monthlyincome = income.getText().toString();
        SharedPref.sSpinnerAccountType = salryaccount_type.getText().toString();
        SharedPref.sSpinnerSelectBank = tvaccount.getText().toString();
        SharedPref.pancard_loan = tvpans.getText().toString();
        SharedPref.Adharcard_loan = tvadhars.getText().toString();
        SharedPref.gender = txtGender.getText().toString();
        SharedPref.sRadioButtonResidenceType = txtResidenceType.getText().toString();
        UserInformation.sProofAddress = txtAddressProof.getText().toString();
        SharedPref.loantypes = loantype.getText().toString();
        SharedPref.subcatory_loan = subcatory.getText().toString();
        SharedPref.loanmant = amount.getText().toString();
        SharedPref.time = timeperiod.getText().toString();
        SharedPref.seditTextLoanTenure = tenure_loan.getText().toString();
        SharedPref.sRadioButtonRepayment = haveloan_status.getText().toString();
        SharedPref.seditTextEMI = emi.getText().toString();
        UserInformation.seditTextCurrentAddress = txtAddress.getText().toString();
        UserInformation.seditTextCurrentAddress1 = txtAddress1.getText().toString();
        UserInformation.seditTextCurrentAddressState = txtState.getText().toString();
        UserInformation.seditTextCurrentAddressCity = txtCity.getText().toString();
        UserInformation.seditTextCurrentAddressPincode = txtPincode.getText().toString();
        UserInformation.seditTextPermanentAdress = txtPermanentAddress.getText().toString();
        UserInformation.seditTextPermanentAdress1 = txtPermanentAddress1.getText().toString();
        UserInformation.seditTextPermanentAdressState = txtPermanentState.getText().toString();
        UserInformation.seditTextPermanentAdressCity = txtPermanentCity.getText().toString();
        UserInformation.seditTextPermanentAdressPincode = txtPermanentAddressPincode.getText().toString();
        UserInformation.sRadioButtonTypeOfEmployee = txtCurrentEmployee.getText().toString();
        UserInformation.seditTextComapnyName = txtabc.getText().toString();
        UserInformation.seditTextPostition = txtPosition.getText().toString();
        UserInformation.seditTextCompanyAddress = txtCompanyAddress.getText().toString();
        UserInformation.seditTextCompanyAddress1 = txtComapnyAddress1.getText().toString();
        UserInformation.seditTextCompanyAddressState = txtCompanyState.getText().toString();
        UserInformation.seditTextCompanyAddressCity = txtCompanyCity.getText().toString();
        UserInformation.seditTextCompanyAddressPinCode = txtComanyPincode.getText().toString();

    }

    public class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(EditData.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("random", SharedPref.random));
            param.add(new BasicNameValuePair("fname", UserInformation.UserFirstName));
            param.add(new BasicNameValuePair("lname", UserInformation.UserLastName));
            param.add(new BasicNameValuePair("email", UserInformation.Email));
            param.add(new BasicNameValuePair("dob", SharedPref.dob_loan));
            param.add(new BasicNameValuePair("emp_month_income", SharedPref.monthlyincome));
            param.add(new BasicNameValuePair("emp_acc_type", SharedPref.sSpinnerAccountType));
            param.add(new BasicNameValuePair("emp_bank_name", SharedPref.sSpinnerSelectBank));
            param.add(new BasicNameValuePair("adhar_card", SharedPref.Adharcard_loan));
            param.add(new BasicNameValuePair("pan_card", SharedPref.pancard_loan));
            param.add(new BasicNameValuePair("gender", SharedPref.gender));
            param.add(new BasicNameValuePair("residence_type", SharedPref.sRadioButtonResidenceType));
            param.add(new BasicNameValuePair("curr_resi_proof", UserInformation.sProofAddress));
            param.add(new BasicNameValuePair("loan_type", SharedPref.loantypes));
            param.add(new BasicNameValuePair("subcategory_loan", SharedPref.subcatory_loan));
            param.add(new BasicNameValuePair("borrow", SharedPref.loanmant));
            param.add(new BasicNameValuePair("borrow_time", SharedPref.time));
            param.add(new BasicNameValuePair("emp_loan_tenure", SharedPref.seditTextLoanTenure));
            param.add(new BasicNameValuePair("emp_repay_complet", SharedPref.sRadioButtonRepayment));
            param.add(new BasicNameValuePair("emp_pay_last_emi", SharedPref.seditTextEMI));
            param.add(new BasicNameValuePair("curr_add_line1", UserInformation.seditTextCurrentAddress));
            param.add(new BasicNameValuePair("curr_add_line2", UserInformation.seditTextCurrentAddress1));
            param.add(new BasicNameValuePair("curr_state", UserInformation.seditTextCurrentAddressState));
            param.add(new BasicNameValuePair("curr_city", UserInformation.seditTextCurrentAddressCity));
            param.add(new BasicNameValuePair("curr_pincode", UserInformation.seditTextCurrentAddressPincode));
            param.add(new BasicNameValuePair("per_add_line1", UserInformation.seditTextPermanentAdress));
            param.add(new BasicNameValuePair("per_add_line2", UserInformation.seditTextPermanentAdress1));
            param.add(new BasicNameValuePair("per_state", UserInformation.seditTextPermanentAdressState));
            param.add(new BasicNameValuePair("per_city", UserInformation.seditTextPermanentAdressCity));
            param.add(new BasicNameValuePair("per_pincode", UserInformation.seditTextPermanentAdressPincode));
            param.add(new BasicNameValuePair("emp_compny_name", UserInformation.seditTextComapnyName));
            param.add(new BasicNameValuePair("emp_posi", UserInformation.seditTextPostition));
            param.add(new BasicNameValuePair("emp_off_add1", UserInformation.seditTextCompanyAddress));
            param.add(new BasicNameValuePair("emp_off_add2", UserInformation.seditTextCompanyAddress1));
            param.add(new BasicNameValuePair("emp_off_state", UserInformation.seditTextCompanyAddressState));
            param.add(new BasicNameValuePair("emp_off_city", UserInformation.seditTextCompanyAddressCity));
            param.add(new BasicNameValuePair("emp_off_pincode", UserInformation.seditTextCompanyAddressPinCode));

            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.loaninsurance_edit, "POST", param);
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
            dialog.dismiss();
            Intent i = new Intent(EditData.this, BankDetail.class);
            i.putExtra("id", SharedPref.random);
            startActivity(i);

        }
    }
}
