package com.bounside.mj.cashiya;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.Movie;
import adapter.MoviesAdapter;
import sqlitedatabase.Bankmessagedb;

public class Trans extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String dbmoney;
    private List<Movie> movieList;
    String Numbervalue;
    int id = R.mipmap.ic_launcher;

    List<String> numbers = new ArrayList<String>();

    SQLiteDatabase sql;
    ContentValues can;
    Bankmessagedb bbd = new Bankmessagedb(this);
    Cursor cursor;
    Movie movie;
    ImageView ii;
    TextView tt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trans);

        ActionBar ac = getSupportActionBar();
        ac.setHomeButtonEnabled(true);
        ac.setDisplayHomeAsUpEnabled(true);

        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

//        ii = (ImageView) findViewById(R.id.cardimg);
//        tt = (TextView) findViewById(R.id.cardtitle);

        showdata();

    }

    private void showdata() {
        movieList = new ArrayList<>();

        cursor = sql.rawQuery("SELECT * FROM trans_msg ORDER BY yrdata desc,mnthdate desc,changdate  desc", null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
//                    String aa = cursor.getString(cursor.getColumnIndex("msgid"));
                    String bb = cursor.getString(cursor.getColumnIndex("nameofbank"));
                    String cc = cursor.getString(cursor.getColumnIndex("body"));
                    String dd = cursor.getString(cursor.getColumnIndex("transdate"));
                    String ee= cursor.getString(cursor.getColumnIndex("category"));
                    String ff= cursor.getString(cursor.getColumnIndex("type"));
                    String gg= cursor.getString(cursor.getColumnIndex("changdate"));
                    String hh= cursor.getString(cursor.getColumnIndex("amount"));

//                    if(cc.contains("Rs"))
//                    {
//                         //splitText = cc.split("Rs");
//                        dbmoney = String.valueOf(getdebitedAmount(cc));
//                    }
//                    else if(cc.contains("INR"))
//                    {
//                        dbmoney = String.valueOf(getdebitedAmounts(cc));
//                    }
//                    else if(cc.contains(""))
//                    {
//                        dbmoney =hh;
//                    }
//                    else
//                    {
//                        dbmoney = "0.0";
//                    }

                    //     dbmoney = String.valueOf(getdebitedAmount(cc));
                    //  Log.i("aa",""+aa );
                    Log.i("aa",""+bb );
                    Log.i("aa",""+cc );
                    Log.i("aa",""+dd );
                    Log.i("aa",""+ee );
                    Log.i("adsda",""+ff );
                    Log.i("aa",""+gg );

                    Log.i("adda",""+dbmoney );

                    int count = cursor.getCount();
                    Log.i("count",""+count );

                    //category function

                    id = gettypecat(ff);
                    Log.i("movidde",""+id );

                    // movie = new Movie(aa, bb, dd,id);
                    movie = new Movie(bb, hh, dd,id,ff);
                    Log.i("movie",""+movie );
                    movieList.add(movie);
                    mAdapter = new MoviesAdapter(movieList);
                    Log.i("movieList",""+mAdapter );
                    recyclerView.setAdapter(mAdapter);
                    Log.i("recyclerView",""+recyclerView );
                    mAdapter.notifyDataSetChanged();

                }while (cursor.moveToNext());
            }
        }
    }

    private int gettypecat(String ff) {
        switch (ff) {
            case "Bills":
                id = R.mipmap.bill_aftr;
                break;

            case "Food":
                id = R.mipmap.food_aftr;
                break;

            case "Fuel":
                id = R.mipmap.fuel_aftr;
                break;

            case "Groceries":
                id = R.mipmap.grocery_aftr;
                break;

            case "Health":
                id = R.mipmap.health_aftr;
                break;

            case "Shopping":
                id = R.mipmap.shoping_aftr;
                break;

            case "Travel":
                id = R.mipmap.travel_aftr;
                break;

            case "Other":
                id = R.mipmap.other_aftr;
                break;

            case "Entertainment":
                id = R.mipmap.entertainment_aftr;
                break;

            case "PURCHASE.":
                id = R.mipmap.purchase_aftr;
                break;

            case "EXTRA":
                id = R.mipmap.extra_aftr;
                break;

            case "DEBITED.":
                id = R.mipmap.debited_aftr;
                break;

            default:
                id = R.mipmap.atm;
                break;
        }
        return id;
    }

    public double getdebitedAmount(String Body) {

        Log.e("Body", "" + Body);
        double debited_amount = 0;
        String splitText[] = Body.split("Rs");
        String matchertext = null;
        if (splitText.length >= 0 ) {
            matchertext = splitText[1];
            Log.e("matchertext1", "" + matchertext);
        } else {
            matchertext = Body;
            Log.e("matchertext2", "" + matchertext);
        }
        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+?");

        Matcher m = p.matcher(matchertext);
        while (m.find()) {
            numbers.add(m.group());
        }

        if (numbers.size() > 0) {
            Numbervalue = numbers.get(0);

            Log.e("amount", "" + numbers.get(0));


        }
        if (Numbervalue != null) {
            debited_amount = Double.valueOf(Numbervalue.replaceAll("[,]",""));
            // debited_amount = Double.parseDouble(Numbervalue);
            //Toast.makeText(MainActivity.this, ""+accountNumber, Toast.LENGTH_SHORT).show();
            //Log.i("account", "" + accountNumber);
        }
        numbers.clear();
        Log.e("debited_amount", "" + debited_amount);
        return debited_amount;

    }

    public double getdebitedAmounts(String Body) {

        Log.e("Body", "" + Body);
        double debited_amount = 0;
        String splitText[] = Body.split("INR");
        String matchertext = null;
        if (splitText.length >= 0 ) {
            matchertext = splitText[1];
            Log.e("matchertext1", "" + matchertext);
        } else {
            matchertext = Body;
            Log.e("matchertext2", "" + matchertext);
        }
        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+?");

        Matcher m = p.matcher(matchertext);
        while (m.find()) {
            numbers.add(m.group());
        }

        if (numbers.size() > 0) {
            Numbervalue = numbers.get(0);

            Log.e("amount", "" + numbers.get(0));


        }
        if (Numbervalue != null) {
            debited_amount = Double.valueOf(Numbervalue.replaceAll("[,]",""));

        }
        numbers.clear();
        Log.e("debited_amount", "" + debited_amount);
        return debited_amount;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menucard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cards) {
            Intent in1 = new Intent(Trans.this,MyCards.class);
            startActivity(in1);


        }
        else  if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(Trans.this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.putExtra("EXIT", true);
        startActivity(in);
    }

}
