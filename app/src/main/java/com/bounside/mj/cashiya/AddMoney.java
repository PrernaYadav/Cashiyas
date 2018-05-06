package com.bounside.mj.cashiya;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by MJ on 3/17/16.
 */
public class AddMoney extends AppCompatActivity {
    EditText amntet, itemet, dateet;
    TextView cashspent;
    RadioGroup radioGroup;
    TextView cat;
    RadioButton bill, food, fuel, groceries, health, shopping, travel, other, entertainment;
    ImageView add;
    // ExpenseData exp;
    Bankmessagedb exp;
    SQLiteDatabase sql;
    String item, amount, date, category,month_name,month_name1,month_name2;
    float amnt = 0;
    ContentValues can;
    Calendar myCalendar = Calendar.getInstance();
    private String selecteddat;
    private int selectedmonth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_money);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        try {
            ActionBar ac = getSupportActionBar();
            if (ac != null) {
                ac.setTitle("Add Expense");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        ac.setHomeButtonEnabled(true);
//        ac.setDisplayHomeAsUpEnabled(true);
        exp = new Bankmessagedb(this);
        amntet = (EditText) findViewById(R.id.amntet);
        cashspent = (TextView) findViewById(R.id.cashspent);
        itemet = (EditText) findViewById(R.id.itemet);
        dateet = (EditText) findViewById(R.id.dateet);
        add = (ImageView) findViewById(R.id.add);
        cat = (TextView) findViewById(R.id.category);

        bill = (RadioButton) findViewById(R.id.radio1);
        food = (RadioButton) findViewById(R.id.radio2);
        fuel = (RadioButton) findViewById(R.id.radio3);
        groceries = (RadioButton) findViewById(R.id.radio4);
        health = (RadioButton) findViewById(R.id.radio5);
        shopping = (RadioButton)findViewById(R.id.radio6);
        travel = (RadioButton)findViewById(R.id.radio7);
        other = (RadioButton) findViewById(R.id.radio8);
        entertainment = (RadioButton) findViewById(R.id.radio9);

        sql = exp.getWritableDatabase();
        can = new ContentValues();

        radioGroup = (RadioGroup) findViewById(R.id.radio);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio1:
                        category = bill.getText().toString();
                        //  Toast.makeText(getContext(), "radio1" + category, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio2:
                        category = food.getText().toString();
                        break;
                    case R.id.radio3:
                        category = fuel.getText().toString();
                        break;
                    case R.id.radio4:
                        category = groceries.getText().toString();
                        break;
                    case R.id.radio5:
                        category = health.getText().toString();
                        break;
                    case R.id.radio6:
                        category = shopping.getText().toString();
                        break;
                    case R.id.radio7:
                        category = travel.getText().toString();
                        break;
                    case R.id.radio8:
                        category = other.getText().toString();
                        break;
                    case R.id.radio9:
                        category = entertainment.getText().toString();
                        break;
                    default:
                        break;
                }
            }
        });


        add.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                getValues();
                if (TextUtils.isEmpty(amount)) {
                    amntet.setError("Field cannot be left empty");
                }
                else if(TextUtils.isEmpty(item)) {
                    itemet.setError("Field cannot be left empty");
                }
                else if(TextUtils.isEmpty(date)) {
                    dateet.setError("Field cannot be left empty");
                }else if(TextUtils.isEmpty(category)) {
                    Toast.makeText(AddMoney.this ,"Select Category", Toast.LENGTH_SHORT).show();
                }
                else {
                    can.put("nameofbank", item);
                    can.put("amount", amount);
                    can.put("transdate", selecteddat);
                    can.put("type", category);
                    can.put("changdate",month_name);
                    can.put("mnthdate",month_name1);
                    can.put("yrdata",month_name2);
                    can.put("category", "Added Expense");
                    can.put("body", " ");
                    Log.e("errorssdd", "" + can);
                    long id = sql.insert("trans_msg", null, can);
                    Log.e("sql", "" + sql);
                    amntet.setText("");
                    itemet.setText("");
                    dateet.setText("");
                    Toast.makeText(AddMoney.this, "Data saved", Toast.LENGTH_SHORT).show();
                }
            }
        });

        amntet.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    amnt = Float.parseFloat(amntet.getText().toString());
                } catch (NumberFormatException e) {
                    amnt = 0;
                    Log.e("errorss", "" + e);

                }
                cashspent.setText("" + amnt);
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //  myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
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
                selecteddat = sdf.format(myCalendar.getTime());
                dateet.setText(selecteddat);
            }
        };

        dateet.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddMoney.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    public void getValues() {

        item = itemet.getText().toString();
        amount = amntet.getText().toString();
        date = dateet.getText().toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent in = new Intent(AddMoney.this,MainActivity.class);
//        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        in.putExtra("EXIT", true);
//        startActivity(in);
    }
}












