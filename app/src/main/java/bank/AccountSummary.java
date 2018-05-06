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
import android.widget.Toast;

import com.bounside.mj.cashiya.R;

import java.util.ArrayList;
import java.util.HashMap;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 11/17/2016.
 */

public class AccountSummary extends AppCompatActivity {

    private SQLiteDatabase sql;
    private final Bankmessagedb bbd = new Bankmessagedb(this);
    private ListView card_list;
    private ArrayList<HashMap<String, String>> lst;
    private Cursor cursor;
    private ListAdapter adapter;
    private HashMap<String, String> hmap;
    private String banl_namevalue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.banks_name);

        card_list = (ListView) findViewById(R.id.banknamelist);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Account Summary");
        }

        lst = new ArrayList<HashMap<String, String>>();
        sql = bbd.getWritableDatabase();

        showdata1();

        String[] from = {"nameofbank"};
        int[] to = {R.id.cardtitle};
        adapter = new SimpleAdapter(AccountSummary.this, lst, R.layout.custom_cardview, from, to);

        card_list.setAdapter(adapter);

        card_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                try {
                    if (!card_list.getAdapter().isEmpty()) {

                        try {
                            HashMap<String, String> map = (HashMap<String, String>) card_list.getItemAtPosition(i);
                            Log.i("selectedFromList", "" + map);
                            String bank = map.get("nameofbank");
                            Log.i("bankssssssssssss", "" + bank);
                            Intent cards = new Intent(AccountSummary.this, DistinctAccount.class);
                            cards.putExtra("cardname", bank);
                            startActivity(cards);

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(AccountSummary.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        Log.d("list", "" + lst);

    }

    private void showdata1() {

        cursor = sql.rawQuery("SELECT DISTINCT nameofbank FROM trans_msg", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    hmap = new HashMap<String, String>();
                    banl_namevalue = cursor.getString(cursor.getColumnIndex("nameofbank"));
                    hmap.put("nameofbank", banl_namevalue);
                    lst.add(hmap);
                    Log.d("list", "" + lst);
                } while (cursor.moveToNext());
            }
        }
// cursor.close();
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
