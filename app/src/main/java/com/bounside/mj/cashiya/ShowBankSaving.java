package com.bounside.mj.cashiya;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 9/1/2016.
 */
public class ShowBankSaving extends AppCompatActivity  {

    String mnthname,nameofbank,accountnumber;
    SQLiteDatabase sql;
    ContentValues can;
    public Calendar calendar;
    String addadys;

    float totalsave=0,totalextra=0;
    private BarChart barCharttarget;
    Spinner spinner;
    String text;

    String[] IntegerArray = new String[] {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    String targetdata[] = {"Credited", "Debited", "Total Saving","Extra Expense"};

    Bankmessagedb bbd = new Bankmessagedb(this);

    TextView monthname,credited,debited,save,extra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showsaving);

        spinner = (Spinner) findViewById(R.id.spinnermonths);

        // Spinner click listener

        monthname = (TextView) findViewById(R.id.monthtext);
        credited = (TextView) findViewById(R.id.monthtextcredited);
        debited = (TextView) findViewById(R.id.monthtextdebited);
        save = (TextView) findViewById(R.id.monthtextsave);
        extra = (TextView) findViewById(R.id.monthtextextra);


        barCharttarget = (BarChart) findViewById(R.id.chartbank);

        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        Calendar cal=Calendar.getInstance();
        SimpleDateFormat month_date = new SimpleDateFormat("MMM");
        mnthname = month_date.format(cal.getTime());


        Intent intent = getIntent();
        Log.i("nameaa",""+mnthname);

        nameofbank = intent.getStringExtra("accuntname");
        Log.i("nameofbank",""+ mnthname);

        accountnumber = intent.getStringExtra("accuntnumber");


        float credit =showdatacredit(nameofbank,accountnumber,mnthname);
        float debit=showdatadebit(nameofbank,accountnumber,mnthname);
        showsaving(credit,debit);
        calculatetargets(credit,debit,totalsave,totalextra);

        monthname.setText(""+mnthname);
        credited.setText(""+credit);
        debited.setText(""+debit);
        save.setText(""+totalsave);
        extra.setText(""+totalextra);
    }

    private void calculatetargets(float c,float d, float s,float e) {
        float c1 = c;
        float d1= d;
        float s1= s;
        float e1 = e;

        float[] data = {
                c1,
                d1,
                s1,
                e1

        };
        ArrayList<BarEntry> entries = new ArrayList<>();
        int count = data.length;
        for (int i = 0; i < count; i++) {
            entries.add(new BarEntry(data[i], i));
            Log.i("broading", " percb " + data[i]);

        }
        BarDataSet dataset1 = new BarDataSet(entries, "");
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);


        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            labels.add(targetdata[i]);
            Log.i("broading", " percb " + targetdata[i]);

        }
        BarData data1 = new BarData(labels, dataset1);
        Log.i("broading", " percb " + data1);

        barCharttarget.setData(data1);
        barCharttarget.setDescription("");
        barCharttarget.animateXY(2000, 2000);
        barCharttarget.setTouchEnabled(false);
        barCharttarget.getAxisLeft().setDrawGridLines(false);
        barCharttarget.getXAxis().setDrawGridLines(false);
        barCharttarget.invalidate();
    }


    public float showdatacredit(String aa,String bb,String cc){
        float totalcredited=0;
        String bankname=aa;
        String accntnmbr=bb;
        String month=cc;
        calendar= Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        addadys = month_year1.format(calendar.getTime());

        Log.i("hfvvyujh",""+addadys);
        String i ="Apr";
        String query ="SELECT amount FROM trans_msg WHERE changdate LIKE '%-"+month+"-"+addadys+"'  AND type ='CREDITED.'  AND nameofbank = '"+bankname+"' AND accuntnumber ='"+accntnmbr+"' ORDER BY changdate";
        Log.i("query1",""+query);
        Cursor c = sql.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg","  jantotal " +amnt+ ".."+temp);
                totalcredited = totalcredited + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  totalapril " +totalcredited);
        return totalcredited;

    }

    public float showdatadebit(String aa,String bb,String cc){
        float totaldebited=0;
        String bankname=aa;
        String accntnmbr=bb;
        String month=cc;
        calendar= Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        addadys = month_year1.format(calendar.getTime());

        Log.i("hfvvyujh",""+addadys);
        String i ="Apr";
        String query ="SELECT amount FROM trans_msg WHERE changdate LIKE '%-"+month+"-"+addadys+"'  AND (type ='DEBITED.' OR type = 'PURCHASE.')  AND nameofbank = '"+bankname+"' AND accuntnumber ='"+accntnmbr+"' ORDER BY changdate";
        Log.i("query",""+query);
        Cursor c = sql.rawQuery(query, null);

        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg","  jantotal " +amnt+ ".."+temp);
                totaldebited = totaldebited + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  totalapril " +totaldebited);
        return totaldebited;

    }

    public float showsaving(float cc, float dd) {
        float cc1 = cc;
        float dd1 = dd;
        totalsave =0;
        totalextra=0;
        totalsave = cc1-dd1;
        if(totalsave<0)
        {
            totalextra =Math.abs(totalsave);
            totalsave =0;
        }
//        else
//        {
//            totalextra =0;
//        }

        return totalsave;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.month_spinner, menu);

        MenuItem item = menu.findItem(R.id.spinnermonths);
        spinner = (Spinner) MenuItemCompat.getActionView(item);
        ArrayAdapter<String> adapterdgt = new ArrayAdapter<String>(ShowBankSaving.this,
                android.R.layout.simple_spinner_dropdown_item, IntegerArray);
        adapterdgt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapterdgt);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String aa = parent.getItemAtPosition(position).toString();
                Log.i("dddd",""+aa);
                Log.i("dddd",""+position);

                float credit1= showdatacredit(nameofbank,accountnumber,aa);
                float debit1= showdatadebit(nameofbank,accountnumber,aa);
                showsaving(credit1,debit1);
                calculatetargets(credit1,debit1,totalsave,totalextra);

                monthname.setText(""+aa);
                credited.setText(""+credit1);
                debited.setText(""+debit1);
                save.setText(""+totalsave);
                extra.setText(""+totalextra);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        return true;
    }

}