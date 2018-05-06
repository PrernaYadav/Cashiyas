package bank;

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

import com.bounside.mj.cashiya.R;

/**
 * Created by Neeraj Sain on 11/24/2016.
 */

public class Personalloancalc extends AppCompatActivity{

    EditText salary,emi;
    LinearLayout emi_lay,ans,emi_click;
    Button Calc,yesemi,Calcem;
    TextView aannss;
    boolean b;
    int amountsal,amountemi;
    int showndata,showndata1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_loan_eligi_calc);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Loan Eligibility Calculator");
        }
        //        ac.setHomeAsUpIndicator(R.drawable.gohome1);
        salary = (EditText) findViewById(R.id.salary_calc);
        emi = (EditText) findViewById(R.id.emiamount_calc);
        emi_lay = (LinearLayout) findViewById(R.id.emi_amount_lay);
        emi_click = (LinearLayout) findViewById(R.id.payemi_click);
        yesemi = (Button) findViewById(R.id.yes_emi);

        Calc = (Button) findViewById(R.id.personal_calcs);
        Calcem = (Button) findViewById(R.id.personal_calce);

        ans = (LinearLayout) findViewById(R.id.answer);
        aannss = (TextView) findViewById(R.id.answeramount);

        yesemi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emi_lay.setVisibility(View.VISIBLE);
                Calc.setVisibility(View.GONE);
                Calcem.setVisibility(View.VISIBLE);
                emi_click.setVisibility(View.GONE);

            }
        });

        Calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(salary.getText().toString())) {
                    salary.setError("Field cannot be left empty");

                }

                else {
                    amountsal = Integer.parseInt(salary.getText().toString());
                    showndata = amountsal * 20;
                    ans.setVisibility(View.VISIBLE);
                    aannss.setText("" + showndata);
                }
            }
        });

        Calcem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getvalue();
                if (TextUtils.isEmpty(salary.getText().toString())) {
                    salary.setError("Field cannot be left empty");

                }
                else    if (TextUtils.isEmpty(emi.getText().toString())) {
                    emi.setError("Field cannot be left empty");

                }
                else if (amountsal<amountemi) {
                    emi.setError(" EMI should  not be greater than Your Income");
                    Toast.makeText(Personalloancalc.this,"EMI should  not be greater than Your Income", Toast.LENGTH_LONG).show();
                }
                else {
                    showndata1 = amountsal - amountemi;
                    showndata = showndata1 * 20;
                    ans.setVisibility(View.VISIBLE);
                    aannss.setText("Your Eligibility is :" + showndata);
                }
            }
        });
    }

    public  void getvalue()
    {
        amountsal = Integer.parseInt(salary.getText().toString());
        amountemi = Integer.parseInt(emi.getText().toString());
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
