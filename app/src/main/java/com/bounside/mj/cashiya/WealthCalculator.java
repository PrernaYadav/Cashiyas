package com.bounside.mj.cashiya;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 2/2/2017.
 */

public class WealthCalculator extends AppCompatActivity {
    EditText editTextAnnualIncome, editTextPercentageofIncomeRequired, editTextInflationRate, editTextPensionIfany, editTextIntrestonSaving, editTextYear;
    Button btnSubmit;
    String seditTextAnnualIncome, seditTextPercentageofIncomeRequired, seditTextInflationRate, seditTextPensionIfany, seditTextIntrestonSaving, seditTextYear;
    double doubleeditTextAnnualIncome, doubleeditTextPercentageofIncomeRequired,
            doubleeditTextInflationRate, doubleeditTextPensionIfany,
            doubleeditTextIntrestonSaving, doubleeditTextYear, savingss2;
    TextView totalsave;
    LinearLayout l1;
    double a, b, c, d, e;
    Pattern pattern, pattern1;
    Double i;
    Matcher matcher, matcher1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wealthcalculator);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Retirement Calculator");
        }

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        editTextYear = (EditText) findViewById(R.id.editTextYear);
        editTextAnnualIncome = (EditText) findViewById(R.id.editTextAnnualIncome);
        editTextPercentageofIncomeRequired = (EditText) findViewById(R.id.editTextPercentageofIncomeRequired);
        editTextInflationRate = (EditText) findViewById(R.id.editTextInflationRate);
        editTextPensionIfany = (EditText) findViewById(R.id.editTextPensionIfany);
        editTextIntrestonSaving = (EditText) findViewById(R.id.editTextIntrestonSaving);
        totalsave = (TextView) findViewById(R.id.sc_savings);
        l1 = (LinearLayout) findViewById(R.id.linear_save);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pattern1 = Pattern.compile("^\\d{1,2}(\\.\\d{1,2})?$");
                matcher1 = pattern1.matcher(editTextInflationRate.getText().toString());
                pattern = Pattern.compile("^\\d{1,2}(\\.\\d{1,2})?$");
                matcher = pattern.matcher(editTextIntrestonSaving.getText().toString());
                getValue();


                if (TextUtils.isEmpty(seditTextAnnualIncome)) {
                    editTextAnnualIncome.setError("Field cannot be left empty");
                } else if (TextUtils.isEmpty(seditTextPercentageofIncomeRequired)) {
                    editTextPercentageofIncomeRequired.setError("Field cannot be left empty");
                } else if (!matcher1.matches()) {
                    editTextInflationRate.setError("Enter correct rate of interest i.e. eg.= 00.00");
                    Toast.makeText(WealthCalculator.this, "Enter correct rate of interest i.e. example= 00.00", Toast.LENGTH_LONG).show();
                } else if (Double.parseDouble(seditTextInflationRate) > 20.00) {
                    editTextInflationRate.setError("Interest rate should be less than 20%");
                    Toast.makeText(WealthCalculator.this, "Interest rate should be less than 20%", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(seditTextInflationRate)) {
                    editTextInflationRate.setError("Field cannot be left empty");
                } else if (TextUtils.isEmpty(seditTextPensionIfany)) {
                    editTextPensionIfany.setError("Field cannot be left empty");
                } else if (!matcher.matches()) {
                    editTextIntrestonSaving.setError("Enter correct rate of interest i.e. eg.= 00.00");
                    Toast.makeText(WealthCalculator.this, "Enter correct rate of interest i.e. example= 00.00", Toast.LENGTH_LONG).show();
                } else if (Double.parseDouble(seditTextIntrestonSaving) > 20.00) {
                    editTextIntrestonSaving.setError("Interest rate should be less than 20%");
                    Toast.makeText(WealthCalculator.this, "Interest rate should be less than 20%", Toast.LENGTH_LONG).show();
                } else if (TextUtils.isEmpty(seditTextIntrestonSaving)) {
                    editTextIntrestonSaving.setError("Field cannot be left empty");
                } else if (TextUtils.isEmpty(seditTextYear)) {
                    editTextYear.setError("Field cannot be left empty");

                } else {
                    doubleeditTextAnnualIncome = Double.parseDouble("" + seditTextAnnualIncome);
                    doubleeditTextInflationRate = Double.parseDouble("" + seditTextInflationRate);
                    doubleeditTextPensionIfany = Double.parseDouble("" + seditTextPensionIfany);
                    doubleeditTextYear = Double.parseDouble("" + seditTextYear);
                    doubleeditTextIntrestonSaving = Double.parseDouble("" + seditTextIntrestonSaving);
                    doubleeditTextPercentageofIncomeRequired = Double.parseDouble("" + seditTextPercentageofIncomeRequired);

                    a = doubleeditTextAnnualIncome * doubleeditTextPercentageofIncomeRequired / 100;

                    d =  Math.pow((1 + doubleeditTextInflationRate / 100), doubleeditTextYear);

                    b = a * d;
                    c = b - doubleeditTextPensionIfany;
                    savingss2 = (c * 100) / doubleeditTextIntrestonSaving;

                    l1.setVisibility(View.VISIBLE);

                    int savings = (int) savingss2;
                    totalsave.setText("" + savings);
                }
            }

            private void getValue() {
                seditTextAnnualIncome = editTextAnnualIncome.getText().toString();
                seditTextPercentageofIncomeRequired = editTextPercentageofIncomeRequired.getText().toString();
                seditTextInflationRate = editTextInflationRate.getText().toString();
                seditTextPensionIfany = editTextPensionIfany.getText().toString();
                seditTextIntrestonSaving = editTextIntrestonSaving.getText().toString();
                seditTextYear = editTextYear.getText().toString();
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

}
