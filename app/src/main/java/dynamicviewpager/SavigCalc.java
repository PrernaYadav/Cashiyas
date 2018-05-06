package dynamicviewpager;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by JKB-2 on 5/13/2016.
 */
public class SavigCalc extends AppCompatActivity {
    EditText ETmonthlysaving, ETCurrentAge, ETRetirementAge, editTextInitialAmount, ETRate;
    TextView totalsave;
    LinearLayout l1;
    Button calculator;
    Pattern pattern;
    Matcher matcher;
    String StringMonthlySaving, StringCurrentAge, StringRetirementAge,StringRate, sInitialAmount;
    double monthlysaving, agenow2, agethen2, yearlyIncrementalSavings, timeDifference, finalSavings, doubleInitialAmount ;
    double prates=0.00;
    double inflationRate=5.00;
    double ratetime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saving_calc);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Savings Calculator");
        }

        editTextInitialAmount = (EditText) findViewById(R.id.editTextInitialAmount);
        ETRate = (EditText) findViewById(R.id.interset);
        ETmonthlysaving = (EditText) findViewById(R.id.sc_saving);
        ETCurrentAge = (EditText) findViewById(R.id.sc_agenow);
        ETRetirementAge = (EditText) findViewById(R.id.sc_agethen);
        totalsave = (TextView) findViewById(R.id.sc_savings);

        l1 = (LinearLayout) findViewById(R.id.linear_save);

        calculator = (Button) findViewById(R.id.sc_calc);

        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pattern = Pattern.compile("^\\d{1,2}(\\.\\d{1,2})?$");
                matcher = pattern.matcher(ETRate.getText().toString());
                getValues();

                if(Integer.parseInt(StringRetirementAge)<=Integer.parseInt(StringCurrentAge))
                {
                    ETRetirementAge.setError("Retirement age should be greater than Age Now");
                }
                else  if(!matcher.matches())
                {
                    ETRate.setError("Enter correct StringRate of interest i.e. eg.= 00.00");
                }
                else if (Double.parseDouble(StringRate)>20.00) {
                    ETRate.setError("Interest rate should be less than 20%");
                }
                else if(TextUtils.isEmpty(StringRate)) {
                    ETRate.setError("Field cannot be left empty");
                }
                else if (TextUtils.isEmpty(StringMonthlySaving)) {
                    ETmonthlysaving.setError("Field cannot be left empty");
                }
                else if(TextUtils.isEmpty(StringCurrentAge)) {
                    ETCurrentAge.setError("Field cannot be left empty");
                }
                else if(TextUtils.isEmpty(StringRetirementAge)) {
                    ETRetirementAge.setError("Field cannot be left empty");
                }
                else if (Double.parseDouble(StringRetirementAge)>65) {
                    ETRetirementAge.setError("Age should be less than 65 years");
                    Toast.makeText(SavigCalc.this,"Age should be less than 65 years", Toast.LENGTH_LONG).show();
                }
                else
                {
                    monthlysaving = Double.parseDouble(""+StringMonthlySaving);
                    agenow2 =  Double.parseDouble(""+StringCurrentAge);
                    agethen2 = Double.parseDouble(""+StringRetirementAge);
                    prates = Double.parseDouble(""+StringRate);
                    doubleInitialAmount = Integer.parseInt(""+sInitialAmount);
                    ratetime=prates/12;

                    yearlyIncrementalSavings = monthlysaving * 12;
                    timeDifference = agethen2 - agenow2;

                    for (int i = 0; i < timeDifference; i++) {

                        finalSavings =  (doubleInitialAmount + yearlyIncrementalSavings) * (1 + ((prates-inflationRate)/100));

                        doubleInitialAmount =  finalSavings;
                    }

                    int sFinalSavings = (int) doubleInitialAmount;

                    l1.setVisibility(View.VISIBLE);
                    totalsave.setText("Rs. "+sFinalSavings);
                }
            }

            private void getValues() {
                StringMonthlySaving = ETmonthlysaving.getText().toString();
                StringCurrentAge =  ETCurrentAge.getText().toString();
                StringRetirementAge = ETRetirementAge.getText().toString();
                StringRate =ETRate.getText().toString();
                sInitialAmount = editTextInitialAmount.getText().toString();
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
