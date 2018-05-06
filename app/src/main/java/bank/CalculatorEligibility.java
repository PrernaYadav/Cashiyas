package bank;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 11/22/2016.
 */

public class CalculatorEligibility extends AppCompatActivity {

    LinearLayout eligibilityLayout;
    Button eligibilityCalculatorBtn;
    TextView eligibilityAmntTv;
    TextView eligibilityEMITv;
    EditText ETmonthlyIncome;
    String sMonthlyIncome, sMonthlyExpense;
    
    double dMonthlyIncome;
    private EditText ETmonthlyExpense;

    double netSavings = 0.0;
    private double dMonthlyExpense;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_loan_eligibility_calc);


        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Home Loan Eligibility");
        }

        initializeFields();

        eligibilityCalculatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getValues();

                calculateEligibility();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void initializeFields() {

        eligibilityLayout = (LinearLayout) findViewById(R.id.home_loan_eligibility_layout);
        eligibilityCalculatorBtn = (Button) findViewById(R.id.home_loan_eligibility_calculate_btn);
        eligibilityAmntTv = (TextView) findViewById(R.id.home_loan_result_amount);
        eligibilityEMITv = (TextView) findViewById(R.id.home_loan_result_EMI);
        ETmonthlyIncome = (EditText) findViewById(R.id.et_monthly_income);
        ETmonthlyExpense = (EditText) findViewById(R.id.et_monthly_expense);
    }

    public void getValues() {

        sMonthlyIncome = ETmonthlyIncome.getText().toString();
        sMonthlyExpense = ETmonthlyExpense.getText().toString();
    }

    public void calculateEligibility()  {

        if (!sMonthlyIncome.equals("")) {
            dMonthlyIncome = Double.parseDouble(sMonthlyIncome);
        }
        if (!sMonthlyExpense.equals("")) {
            dMonthlyExpense = Double.parseDouble(sMonthlyExpense);
        }

        if (dMonthlyIncome < dMonthlyExpense) {
            Toast.makeText(this, "Expense could not be greater than income", Toast.LENGTH_SHORT).show();
        }
        else {
            netSavings = dMonthlyIncome - dMonthlyExpense;

            double dElgibilityValue = (netSavings * 55000) / 780;
            double dEmi = netSavings * 0.45;

            eligibilityAmntTv.setText("Loan Amount :  Rs. " + (int) dElgibilityValue);
            eligibilityEMITv.setText("EMI :  Rs. " + (int) dEmi);
            eligibilityLayout.setVisibility(View.VISIBLE);
        }
    }
}
