package com.bounside.mj.cashiya;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Harsh on 3/21/2016.
 */
public class AddCategory extends AppCompatActivity {
    EditText updatetype,updateamount,updatecalender;
    RadioButton bill1, food1, fuel1, groceries1, health1, shopping1, travel1, other1, entertainment1;
    RadioGroup radioGroup1;
    ImageView slip;
    TextView title, amount, date, cat, pic;
    ImageView save;
    String accounts, amounts, dates,cate,type;
    String category;
    ContentValues can;
    Bankmessagedb exp;
    SQLiteDatabase sql;
    private String selecteddat;
    Date date1=new Date();
    String newDateStr;
    Cursor cursor;

    String newtype,newcalender,month_name,month_name1,month_name2,newamount;
    float previousamount;
    Calendar myCalendar = Calendar.getInstance();
    TextView body,typess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcategory);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        ActionBar ac = getSupportActionBar();
        ac.setTitle("Edit");
        ac.setHomeButtonEnabled(true);
        ac.setDisplayHomeAsUpEnabled(true);
        cat = (TextView) findViewById(R.id.edittext1);
        updatetype = (EditText) findViewById(R.id.edittype);
        updateamount = (EditText) findViewById(R.id.editamount);
        updatecalender = (EditText) findViewById(R.id.editdate);
        typess = (TextView) findViewById(R.id.typesms);
        // pic = (TextView) findViewById(R.id.editText3);
        title = (TextView) findViewById(R.id.textView2111);
        amount = (TextView) findViewById(R.id.textView31111);
        date = (TextView) findViewById(R.id.textView4111);
        // slip = (ImageView) findViewById(R.id.img_slip);
        save = (ImageView) findViewById(R.id.submitcat);
        body = (TextView) findViewById(R.id.bodymessage);

        exp = new Bankmessagedb(this);
        sql = exp.getWritableDatabase();
        can = new ContentValues();
        //radio buttons
        bill1 = (RadioButton) findViewById(R.id.radio10);
        food1 = (RadioButton) findViewById(R.id.radio11);
        fuel1 = (RadioButton) findViewById(R.id.radio12);
        groceries1 = (RadioButton) findViewById(R.id.radio13);
        health1 = (RadioButton) findViewById(R.id.radio14);
        shopping1 = (RadioButton) findViewById(R.id.radio15);
        travel1 = (RadioButton) findViewById(R.id.radio16);
        other1 = (RadioButton) findViewById(R.id.radio17);
        entertainment1 = (RadioButton) findViewById(R.id.radio18);
        radioGroup1 = (RadioGroup) findViewById(R.id.radiocat);
//        pic.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(takePicture, 0);
//            }
//        });

        Intent intent = getIntent();
        accounts = intent.getStringExtra("account");
        amounts = intent.getStringExtra("amount");
        previousamount =Float.valueOf(amounts);

        dates = intent.getStringExtra("date");
        SimpleDateFormat month_dates = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");

        try {
            date1 = month_dates.parse(dates);
            Log.i("date1mm", "" + date1);
            SimpleDateFormat postFormater = new SimpleDateFormat("dd-MMM-yyyy");
            newDateStr = postFormater.format(date1);
            Log.i("newDateStr", "" + newDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("date1", "" + date1);
        SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy");

        month_name = month_date.format(date1);
        Log.i("monthprevios", "" + month_name);

        SimpleDateFormat month_year = new SimpleDateFormat("MM");
        month_name1 = month_year.format(date1);
        Log.i("monthprevios", "" + month_name1);

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        month_name2 = month_year1.format(date1);
        Log.i("monthprevios", "" + month_name2);
        //  cate = intent.getStringExtra("cat");
        type = intent.getStringExtra("type");
        title.setText(accounts);
        amount.setText(amounts);
        date.setText(dates);
        typess.setText(type);
        showbody();
        Log.i("vbyjhfgi",""+accounts);
        Log.i("vbyjhfgi",""+amounts);
        Log.i("vbyjhfgi",""+dates);
        //  Log.i("vbyjhfgi",""+cate);
        Log.i("vbyjhfgi",""+type);

//update query

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues();

                if (TextUtils.isEmpty(newtype)) {
                    // newtype = accounts;
                    updatetype.setError("Field cannot be left empty");

                }
                else if(TextUtils.isEmpty(newamount)) {
                    // newamount =previousamount;
                    updateamount.setError("Field cannot be left empty");
                }
                else if(TextUtils.isEmpty(newcalender)) {
                    //newcalender = dates;
                    updatecalender.setError("Field cannot be left empty");
                }else if(TextUtils.isEmpty(category)) {
                    Toast.makeText(AddCategory.this ,"Select Category", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.i("monthdataaaa", "" + newtype +".."+newamount+".."+category+".."+newcalender+".."+month_name+".."+month_name1+".."+month_name2);
                    String where = "nameofbank=? and amount=? and  type=? and  transdate=? ";
                    String[] whereArgs = new String[]{String.valueOf(accounts), String.valueOf(amounts)
                            , String.valueOf(type) , String.valueOf(dates)
                    };
                    can.put("nameofbank", newtype);
                    can.put("amount", newamount);
                    can.put("type", category);
                    can.put("transdate", newcalender);
                    can.put("changdate",month_name);
                    can.put("mnthdate",month_name1);
                    can.put("yrdata",month_name2);
                    can.put("category", "Added Expense");
                    sql.update("trans_msg", can, where, whereArgs);
                    Log.i("broadingfefawe", "updated new value "+".."+can + ".."+sql);
//                    updatetype.setText("");
//                    updateamount.setText("");
//                    updatecalender.setText("");
                    Toast.makeText(AddCategory.this, "Data saved", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddCategory.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }

            }
        });

        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio10:
                        category = bill1.getText().toString();
                        break;
                    case R.id.radio11:
                        category = food1.getText().toString();
                        break;
                    case R.id.radio12:
                        category = fuel1.getText().toString();
                        break;
                    case R.id.radio13:
                        category = groceries1.getText().toString();
                        break;
                    case R.id.radio14:
                        category = health1.getText().toString();
                        break;
                    case R.id.radio15:
                        category = shopping1.getText().toString();
                        break;
                    case R.id.radio16:
                        category = travel1.getText().toString();
                        break;
                    case R.id.radio17:
                        category = other1.getText().toString();
                        break;
                    case R.id.radio18:
                        category = entertainment1.getText().toString();
                        break;
                    default:
                        break;

                }
            }
        });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //  myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);


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
                updatecalender.setText(selecteddat);
            }

        };


        updatecalender.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddCategory.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }

    private void showbody() {
        cursor = sql.rawQuery("SELECT body FROM trans_msg WHERE amount ='"+amounts+"' AND nameofbank ='"+accounts+"' AND transdate ='"+dates+"' AND  type ='"+type+"'", null);

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
//                    String aa = cursor.getString(cursor.getColumnIndex("msgid"));
                    String bb =String.valueOf(cursor.getString(0));
                    Log.i("gfduytge",""+bb);

                    body.setText(""+bb);




                }while (cursor.moveToNext());
            }
        }


    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((resultCode == RESULT_OK) && (requestCode == 0)) {
            Bitmap takenImage = (Bitmap) data.getExtras().get("data");
            slip.setImageBitmap(takenImage);
        }
    }

    public void getValues() {
        //   try {
        //  newamount = Float.parseFloat(updateamount.getText().toString());
        newamount = updateamount.getText().toString();
//        } catch (NumberFormatException e) {
//            newamount = 0.0f;
//            Log.e("errorss", "" + e);
//
//        }

        newcalender = updatecalender.getText().toString();
        newtype  = updatetype.getText().toString();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

            case R.id.deletedata:
                datadelete();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void datadelete() {
        AlertDialog.Builder builder = new AlertDialog.Builder(
                AddCategory.this);
        builder.setTitle("Delete");
        builder.setMessage("Are you sure? You Want to Delete?");

        builder.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        String where = "transdate=? and nameofbank=? and amount=? and type=?";
                        String[] whereArgs = new String[]{String.valueOf(dates), String.valueOf(accounts),String.valueOf(amounts),String.valueOf(type)};
                        sql.delete("trans_msg", where, whereArgs);
                        Intent intent = new Intent(AddCategory.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);



                    }
                });
        builder.setNegativeButton("No",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog,
                                        int which) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.delete, menu);
        return true;
    }
}

