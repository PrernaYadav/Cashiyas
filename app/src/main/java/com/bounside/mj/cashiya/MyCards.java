package com.bounside.mj.cashiya;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 8/3/2016.
 */
public class MyCards extends AppCompatActivity{

    SQLiteDatabase sql;
    ContentValues can;
    Bankmessagedb bbd = new Bankmessagedb(this);
    ListView card_list;
    ArrayList<HashMap<String, String>> lst;
    Cursor cursor;
    ListAdapter adapter;
    HashMap<String, String> hmap;
    String banl_namevalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cards);

        card_list= (ListView) findViewById(R.id.cardlist);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("My Cards");
        }

        lst = new ArrayList<HashMap<String, String>>();
        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        showdata1();

        String[] from = {"nameofbank"};
        int[] to = {R.id.cardtitle};
        adapter = new SimpleAdapter(MyCards.this,
                lst, R.layout.custom_cardview, from, to);

        card_list.setAdapter(adapter);

        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map =(HashMap<String,String>)card_list.getItemAtPosition(i);
                Log.i("selectedFromList",""+map);
                String bank=  map.get("nameofbank");
                Log.i("bankssssssssssss",""+bank);
                Intent cards= new Intent(MyCards.this, MyCardData.class);
                cards.putExtra("cardname",bank);
                startActivity(cards);

            }
        });
        Log.d("jhg",""+lst);

    }

    private void showdata1() {

        cursor = sql.rawQuery("SELECT DISTINCT nameofbank FROM trans_msg where nameofbank='State Bank Of India' "+
                "OR nameofbank='Punjab National Bank' OR nameofbank='Yes Bank' OR nameofbank='Indusind Bank' OR nameofbank='ICICI Bank' OR nameofbank='Citi Bank' OR nameofbank='OBC Bank' OR nameofbank='HDFC Bank' OR nameofbank='Canara Bank' OR nameofbank='Kotak Bank' OR nameofbank='Union Bank' OR nameofbank='Bank of India' OR nameofbank='Kotak Bank' OR nameofbank='Other Bank'", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    hmap = new HashMap<String, String>();
                    banl_namevalue = cursor.getString(cursor.getColumnIndex("nameofbank"));
                    hmap.put("nameofbank", banl_namevalue);
                    lst.add(hmap);
                    Log.d("jhg",""+lst);
                } while (cursor.moveToNext());
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in1 = new Intent(MyCards.this,MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
