package dynamicviewpager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shared_pref.SharedPref;

/**
 * Created by Neeraj Sain on 10/11/2016.
 */

public class EmiCalc extends AppCompatActivity {
        EditText pamount,prate,ptime;
        Float  pamounts=0.0f;
    Float prates=0.0f;
    Float ptimes=0.0f;
    double totalamonts;
    double totalrates;
    double totalemis;
    Float ratetime,ratetime1,ratetime2;
    double c1;
    double c2;
    double c3;
    float monthsemi;
    Pattern pattern;
    Matcher matcher;
        TextView totalamont,totalrate,totalemi;
    String amount,rate,time;
         Button calculate;
    LinearLayout l1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emicalc);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("EMI Calculator");
        }

        pamount = (EditText) findViewById(R.id.principlamunt);
        prate = (EditText) findViewById(R.id.interset);
        ptime = (EditText) findViewById(R.id.loantime);
        totalamont = (TextView) findViewById(R.id.ans_amountpayable);
        totalrate = (TextView) findViewById(R.id.ans_interst);
        totalemi = (TextView) findViewById(R.id.ans_emimonth);
        calculate = (Button) findViewById(R.id.emi_calc);
        l1 = (LinearLayout) findViewById(R.id.linear_emi);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getvalue();

                pattern = Pattern.compile("^\\d{1,2}(\\.\\d{1,2})?$");
                matcher = pattern.matcher(prate.getText().toString());

                 if(!matcher.matches())
                {
                    prate.setError("Enter correct rate of interest i.e. eg.= 00.00");
                    Toast.makeText(EmiCalc.this,"Enter correct rate of interest i.e. example= 00.00", Toast.LENGTH_LONG).show();
                }
               else if (TextUtils.isEmpty(amount)) {
                    pamount.setError("Field cannot be left empty");

                }
                 else if (Double.parseDouble(rate)>20.00) {
                     prate.setError("Interest rate should be less than 20%");
                     Toast.makeText(EmiCalc.this,"Interest rate should be less than 20%", Toast.LENGTH_LONG).show();
                 }

                 else if (Double.parseDouble(time)>30) {
                     ptime.setError("Age should be less than 30 years");
                     Toast.makeText(EmiCalc.this,"Age should be less than 30 years", Toast.LENGTH_LONG).show();
                 }

                else if( (amount.startsWith("0")))
                {
                    pamount.setError("Enter valid Principle Amount");
                    Toast.makeText(EmiCalc.this, " Enter valid Principle Amount", Toast.LENGTH_SHORT).show();
                }

                else if(TextUtils.isEmpty(rate)) {
                    prate.setError("Field cannot be left empty");
                }
                else if(TextUtils.isEmpty(time)) {
                    ptime.setError("Field cannot be left empty");
                }
                else {
                    pamounts = Float.parseFloat(amount);
                    prates = Float.parseFloat(rate);
                    ptimes = Float.parseFloat(time);
                    calculateemi();
                    l1.setVisibility(View.VISIBLE);

                     int totalAmount = (int)totalamonts;
                     int totalRate = (int)(totalrates);
                     int totalEMI = (int)(totalemis);

                    totalamont.setText("Rs. " + totalAmount);
                    totalrate.setText("Rs. " + totalRate);
                    totalemi.setText("Rs. " + totalEMI);
                }
            }
        });
    }

    private void calculateemi() {

        monthsemi =ptimes*12;

        ratetime = prates/100;
        ratetime1 = ratetime/12;
        ratetime2 = 1 +ratetime1;

        c1 = Math.pow(ratetime2, monthsemi);

        c2 = c1 -1 ;
        c3= c1/c2;

        totalemis = pamounts * ratetime1* c3;
        DecimalFormat df = new DecimalFormat("#.#####");
        totalemis= Double.parseDouble(df.format(totalemis));
        totalamonts = totalemis*monthsemi;
        totalamonts= Double.parseDouble(df.format(totalamonts));
        totalrates = totalamonts - pamounts;
        totalrates= Double.parseDouble(df.format(totalrates));
    }

    private void getvalue() {
        amount =pamount.getText().toString();
        rate =prate.getText().toString();
        time =ptime.getText().toString();

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


