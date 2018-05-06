package bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.bounside.mj.cashiya.Insurance_queryList;
import com.bounside.mj.cashiya.Loan_queryList;
import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.Transfer_queryList;

/**
 * Created by Neeraj Sain on 11/30/2016.
 */

public class YourQueries extends AppCompatActivity {

    Button loan,transfer,insurance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_queries);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Your Queries");
        }
        loan = (Button) findViewById(R.id.loan_queryuser);
        transfer = (Button) findViewById(R.id.transfer_queryuser);
        insurance = (Button) findViewById(R.id.insurance_queryuser);

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(YourQueries.this, Loan_queryList.class);
                startActivity(i1);
            }
        });

        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(YourQueries.this, Transfer_queryList.class);
                startActivity(i1);
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(YourQueries.this, Insurance_queryList.class);
                startActivity(i1);
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
