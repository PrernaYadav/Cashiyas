package com.bounside.mj.cashiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import shared_pref.UserInformation;
import sqlitedatabase.ExpenseData;

/**
 * Created by Harsh on 4/12/2016.
 */
public class MyTargets extends AppCompatActivity {
    final String TAG1 = "income";
    ExpenseData ed = new ExpenseData(this);
    EditText income_ed, target_ed, year_ed, saving_ed;
    TextView ans_tv;
    Button btn;
    final String TAG = "saving";
    String  ans_tvs;
    int year_edi, saving_edi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.targets);

        ActionBar ac = getSupportActionBar();
//        ac.setHomeButtonEnabled(true);
//        ac.setDisplayHomeAsUpEnabled(true);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Log.i(TAG, "" + ed.getIncome());
        income_ed = (EditText) findViewById(R.id.income_ed);
        target_ed = (EditText) findViewById(R.id.target_ed);
        year_ed = (EditText) findViewById(R.id.year_ed);
        saving_ed = (EditText) findViewById(R.id.saving_ed);
        ans_tv = (TextView) findViewById(R.id.ans_tv);
        btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();
                Intent in = new Intent(MyTargets.this,ShowTargets.class);
                startActivity(in);
            }
        });
    }

    public void getValues() {

        UserInformation.income_edi = Integer.parseInt(income_ed.getText().toString());
        UserInformation.target_edi = Integer.parseInt(target_ed.getText().toString());
        UserInformation.year_edi = Integer.parseInt(year_ed.getText().toString());
        UserInformation.saving_edi = Integer.parseInt(saving_ed.getText().toString());
        UserInformation.yearlysave= year_edi * saving_edi * 12;
        ans_tvs = ans_tv.getText().toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in = new Intent(MyTargets.this,MainActivity.class);
//        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        in.putExtra("EXIT", true);
//        startActivity(in);
    }
}
