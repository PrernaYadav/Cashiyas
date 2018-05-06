package loan;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bounside.mj.cashiya.BankDetail;
import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import shared_pref.SharedPref;
import shared_pref.UserInformation;


/**
 * Created by JKB-2 on 4/1/2016.
 */
public class SubmitLoanQuery extends AppCompatActivity {
    TextView name, email, subcatory, txtPosition, tvaccount, tvadhars, txtLaonType, txtResidenceType, txtLastName, tvpans, dob, contact, income, salary_stms, salryaccount, salryaccount_type, city, employment, loantype, amount, timeperiod, haveloan_status, haveloan, haveemi, emi, idproof, addproof, incproof, officeproof, bankproof, ownershipproof, businessproof, enq_types;
    Button button_edit, button_ok;
    LinearLayout have_laty;
    TextView amnt_loan, txtabc, where_loan, txtCurrentEmployee, txtAddressProof, tenure_loan;
    TextView txtAddress, txtAddress1, txtGender, txtState, txtCity, txtPincode, txtPermanentAddress, txtPermanentAddress1, txtPermanentState, txtPermanentCity, txtPermanentAddressPincode;
    TextView txtCompanyName, txtSalaryMode, txtCompanyAddress, txtComapnyAddress1, txtCompanyState, txtCompanyCity, txtComanyPincode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submit_loan_query);

        initializeFields();

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setTitle("Loan Form Preview");
            ac.setDisplayHomeAsUpEnabled(true);
        }

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
        txtSalaryMode.setText(SharedPref.salaryModeValue);

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

        button_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SubmitLoanQuery.this, EditData.class);
                i.putExtra("id", SharedPref.random);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SubmitLoanQuery.this);

                alertDialogBuilder.setMessage("Please Ensure your Name and DOB matches with the Name and DOB on entered PAN card details before Proceeding.");
                // set alert dialog message
                alertDialogBuilder
                        .setTitle("Do you want to proceed")  // Set the message to display.
                        .setCancelable(false) //  Sets whether the dialog is cancelable or not.
// Set a listener to be invoked when the positive button of the dialog is pressed.
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if yes button is clicked, close current activity
                                Intent i = new Intent(SubmitLoanQuery.this, BankDetail.class);
                                i.putExtra("id", SharedPref.random);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);

                                finish();


                            }
                        });

//Set a listener to be invoked when the negative button of the dialog is pressed
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if no button is clicked, just close the dialog box and do nothing
                        dialog.cancel();
                    }
                });

                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();
                // Display alert dialog
                alertDialog.show();
            }
        });
    }

    public void initializeFields() {
        txtabc = (TextView) findViewById(R.id.txtabc);
        txtCompanyName = (TextView) findViewById(R.id.txtCompanyName);
        txtSalaryMode = (TextView) findViewById(R.id.txtSalaryMode);
        txtCompanyAddress = (TextView) findViewById(R.id.txtCompanyAddress);
        txtComapnyAddress1 = (TextView) findViewById(R.id.txtComapnyAddress1);
        txtCompanyState = (TextView) findViewById(R.id.txtCompanyState);
        txtCompanyCity = (TextView) findViewById(R.id.txtCompanyCity);
        txtComanyPincode = (TextView) findViewById(R.id.txtComanyPincode);
        txtCompanyName.setText(UserInformation.seditTextComapnyName);
        txtCompanyAddress.setText(UserInformation.seditTextCompanyAddress);
        txtComapnyAddress1.setText(UserInformation.seditTextCompanyAddress1);
        txtCompanyState.setText(UserInformation.seditTextCompanyAddressState);
        txtPosition = (TextView) findViewById(R.id.txtPosition);
        txtCompanyCity.setText(UserInformation.seditTextCompanyAddressCity);
        txtComanyPincode.setText(UserInformation.seditTextCompanyAddressPinCode);
        txtCurrentEmployee = (TextView) findViewById(R.id.txtCurrentEmployee);
        txtResidenceType = (TextView) findViewById(R.id.txtResidenceType);
        txtGender = (TextView) findViewById(R.id.txtGender);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtAddress1 = (TextView) findViewById(R.id.txtAddress1);
        txtAddressProof = (TextView) findViewById(R.id.txtAddressProof);
        txtPosition = (TextView) findViewById(R.id.txtPosition);
        txtAddressProof.setText(UserInformation.sProofAddress);
        txtState = (TextView) findViewById(R.id.txtState);
        txtCurrentEmployee.setText(UserInformation.sRadioButtonTypeOfEmployee);
        txtLaonType = (TextView) findViewById(R.id.txtLaonType);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtLaonType.setText(SharedPref.loantypes);
        txtPincode = (TextView) findViewById(R.id.txtPincode);
        txtPermanentAddress = (TextView) findViewById(R.id.txtPermanentAddress);
        txtPermanentAddress1 = (TextView) findViewById(R.id.txtPermanentAddress1);
        txtPermanentState = (TextView) findViewById(R.id.txtPermanentState);
        txtPermanentCity = (TextView) findViewById(R.id.txtPermanentCity);
        txtPermanentAddressPincode = (TextView) findViewById(R.id.txtPermanentAddressPincode);
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
        name = (TextView) findViewById(R.id.tvname);
        email = (TextView) findViewById(R.id.tvmail);
        contact = (TextView) findViewById(R.id.tvcontact);
        income = (TextView) findViewById(R.id.tvincome_loan);
        salryaccount_type = (TextView) findViewById(R.id.tvsalstm);
        salryaccount_type.setText(SharedPref.sSpinnerAccountType);
        tvaccount = (TextView) findViewById(R.id.tvaccount);
        tvaccount.setText(SharedPref.sSpinnerSelectBank);
        city = (TextView) findViewById(R.id.tvresidence);
        employment = (TextView) findViewById(R.id.tvemployment);
        tvadhars = (TextView) findViewById(R.id.tvadhar);
        dob = (TextView) findViewById(R.id.tvdob);
        tvpans = (TextView) findViewById(R.id.tvpan);
        email.setText(UserInformation.Email);
        income.setText(SharedPref.seditTextMonthlyIncome);
        loantype = (TextView) findViewById(R.id.tvloan);
        subcatory = (TextView) findViewById(R.id.tvsubcategory);
        amount = (TextView) findViewById(R.id.tvloanamount);
        timeperiod = (TextView) findViewById(R.id.tvtime);
        haveloan = (TextView) findViewById(R.id.tvhaveloan);
        haveloan_status = (TextView) findViewById(R.id.tvhaveloan_satus);
        txtLastName = (TextView) findViewById(R.id.txtLastName);
        amnt_loan = (TextView) findViewById(R.id.viewhaveloan_amnt);
        haveloan_status.setText(SharedPref.sRadioButtonRepayment);
        where_loan = (TextView) findViewById(R.id.viewhaveloan_where);
        tenure_loan = (TextView) findViewById(R.id.viewhaveloan_tenure);
        emi = (TextView) findViewById(R.id.tvemi);
        tenure_loan.setText(SharedPref.seditTextLoanTenure);
        idproof = (TextView) findViewById(R.id.tvid);
        addproof = (TextView) findViewById(R.id.tvaddprf);
        incproof = (TextView) findViewById(R.id.tvincomprf);
        officeproof = (TextView) findViewById(R.id.tvofcadprf);
        ownershipproof = (TextView) findViewById(R.id.tvowner);
        businessproof = (TextView) findViewById(R.id.tvbusncon);
        salary_stms = (TextView) findViewById(R.id.tvbankstat);
        emi.setText(SharedPref.seditTextEMI);
        txtLastName.setText(UserInformation.seditTextLastName);

        name.setText(UserInformation.UserFirstName);

        //buttons
        button_edit = (Button) findViewById(R.id.button_edit);
        button_ok = (Button) findViewById(R.id.button_submit);
        have_laty = (LinearLayout) findViewById(R.id.view_haveloanlay);

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(SubmitLoanQuery.this);
        // builder.setCancelable(false);
        builder.setTitle("");
        builder.setMessage("Your application is not complete. Incomplete application would not get processed. Proceed exiting the form?");
        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent in1 = new Intent(SubmitLoanQuery.this, MainActivity.class);
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
}
