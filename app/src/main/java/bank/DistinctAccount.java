package bank;

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

import com.bounside.mj.cashiya.MyCardData;
import com.bounside.mj.cashiya.MyCards;
import com.bounside.mj.cashiya.R;

import java.util.ArrayList;
import java.util.HashMap;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 11/16/2016.
 */

public class DistinctAccount extends AppCompatActivity {

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
        setContentView(R.layout.distinct_accunt);

        card_list= (ListView) findViewById(R.id.accuntnmbrlist);

        Intent intent = getIntent();
        accnname = intent.getStringExtra("cardname");
        Log.i("Account Name",""+ accnname);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Account Summary");
        }

        lst = new ArrayList<HashMap<String, String>>();
        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        showdata1();

        String[] from = {"accuntnumber"};
        int[] to = {R.id.cardtitle};

        adapter = new SimpleAdapter(DistinctAccount.this, lst, R.layout.custom_cardview, from, to);

        card_list.setAdapter(adapter);

        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    HashMap<String,String> map =(HashMap<String,String>)card_list.getItemAtPosition(i);
                    Log.i("selectedFromList",""+map);
                    String accountNo=  map.get("accuntnumber");
                    Log.i("accountNossssssssssss",""+accountNo);

                    Intent cards= new Intent(DistinctAccount.this, AccountNumberDetails.class);
//                    Intent cards= new Intent(DistinctAccount.this, MyCardData.class);
                    cards.putExtra("accuntnumber",accountNo);
                    cards.putExtra("accuntname",accnname);

                    startActivity(cards);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d("lst",""+lst);
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
                    Log.d("ListAccountNumbers",""+lst);
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