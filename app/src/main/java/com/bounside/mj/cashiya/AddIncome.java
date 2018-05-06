package com.bounside.mj.cashiya;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by JKB-2 on 4/11/2016.
 */
public class AddIncome extends AppCompatActivity {

    TextView tvamount, tvdate, tvspent;
    Spinner spcat;
    int amnt = 0;
    Bankmessagedb ed;
    SQLiteDatabase sql;
    ContentValues can;
    private String[] arraySpinner;
    ImageView addincome;
    ScrollView sv;
    Calendar myCalendar = Calendar.getInstance();
    final String TAG = "addincome";
    private String selecteddate,month_name,month_name1,month_name2;
    private int selectedmonth,selectedyr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_income);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        ed = new Bankmessagedb(this);
        sql = ed.getWritableDatabase();
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Add Income");
        }
        can = new ContentValues();
//        ac.setHomeButtonEnabled(true);
//        ac.setDisplayHomeAsUpEnabled(true);

        tvamount = (TextView) findViewById(R.id.amnti);
        tvdate = (TextView) findViewById(R.id.datei);
        tvspent = (TextView) findViewById(R.id.cashspent1);
        spcat = (Spinner) findViewById(R.id.spi);
        this.arraySpinner = new String[]{
                "Salary", "Rent", "Sale"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddIncome.this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinner);
        spcat.setAdapter(adapter);
        sv = (ScrollView) findViewById(R.id.sv);
        addincome = (ImageView) findViewById(R.id.add1);


        addincome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amount = tvamount.getText().toString();
                String date = tvdate.getText().toString();
                String cat = spcat.getSelectedItem().toString();
                Log.i(TAG, " amnt " + amount + " date " + date + " cat " + cat);

                can.put("amount", amount);
                can.put("transdate", selecteddate);
                can.put("nameofbank", cat);
                can.put("changdate",month_name);
                can.put("mnthdate",month_name1);
                can.put("yrdata",month_name2);
                can.put("category", "Added Income");
                can.put("type","CREDITED.");

                long id =     sql.insert("trans_msg", null, can);
                Log.i("cancan", "" + can);
                Log.e("sql", "" + sql);
                Snackbar snackbar = Snackbar.make(sv, "Data Saved", Snackbar.LENGTH_SHORT);

                snackbar.show();
                tvamount.setText("");
                tvdate.setText("");

            }
        });


        tvamount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    amnt = Integer.parseInt(tvamount.getText().toString());
                } catch (NumberFormatException e) {
                    amnt = 0;
                    Log.e("errorss", "" + e);

                }
                tvspent.setText("" + amnt);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                selectedmonth = monthOfYear;


                Log.i("month", "" + month_name2);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy");
                month_name = month_date.format(myCalendar.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar.getTime());
                Log.i("month", "" + month_name2);
                updateLabel();
            }

            private void updateLabel() {

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddate = sdf.format(myCalendar.getTime());
                tvdate.setText(selecteddate);
            }
        };

        tvdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddIncome.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in = new Intent(AddIncome.this,MainActivity.class);
//        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        in.putExtra("EXIT", true);
//        startActivity(in);
    }

}
