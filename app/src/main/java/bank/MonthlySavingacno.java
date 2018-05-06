package bank;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.ShowBankSaving;

import java.util.ArrayList;
import java.util.HashMap;

import dynamicviewpager.MyCardsMonth;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 11/21/2016.
 */

public class MonthlySavingacno extends AppCompatActivity {

    SQLiteDatabase sql;
    ContentValues can;
    Bankmessagedb bbd = new Bankmessagedb(this);
    ListView card_list;
    ArrayList<HashMap<String, String>> lst;
    Cursor cursor;
    ListAdapter adapter;
    HashMap<String, String> hmap;
    String banl_namevalue;
    String accnname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.month_save_acno);

        card_list= (ListView) findViewById(R.id.accuntnmbrlistacno);

        Intent intent = getIntent();
        accnname = intent.getStringExtra("cardname");
        Log.i("nameaa",""+ accnname);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
        }

        lst = new ArrayList<HashMap<String, String>>();
        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        showdata1();

        String[] from = {"accuntnumber"};
        int[] to = {R.id.cardtitle};
        adapter = new SimpleAdapter(MonthlySavingacno.this, lst, R.layout.custom_cardview, from, to);

        card_list.setAdapter(adapter);

        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                HashMap<String,String> map =(HashMap<String,String>)card_list.getItemAtPosition(i);
                Log.i("selectedFromList",""+map);
                String bank=  map.get("accuntnumber");
                Log.i("bankssssssssssss",""+bank);

                Intent cards= new Intent(MonthlySavingacno.this, ShowBankSaving.class);
                cards.putExtra("accuntnumbers",bank);
                cards.putExtra("accuntname",accnname);
                startActivity(cards);
            }
        });
        Log.d("jhg",""+lst);
    }

    private void showdata1() {

        cursor = sql.rawQuery("SELECT DISTINCT accuntnumber FROM trans_msg where nameofbank='"+accnname+"' AND accuntnumber!='xxxxx'", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    hmap = new HashMap<String, String>();
                    banl_namevalue = cursor.getString(cursor.getColumnIndex("accuntnumber"));
                    hmap.put("accuntnumber", banl_namevalue);
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
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}