package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
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
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import shared_pref.SharedPref;
import shared_pref.UserInformation;
import sqlitedatabase.LoginData;

/**
 * Created by Tamanna on 7/28/2016.
 */
public class Insurance_queryList extends AppCompatActivity implements
        AdapterView.OnItemClickListener {
    ListAdapter adapter;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    ArrayList<HashMap<String, String>> list;
    ListView loanlist;
    ContentValues can;
    ArrayList<NameValuePair> param;
    String id,mDate,insurance_type;
    LoginData db = new LoginData(this);
    SQLiteDatabase sql;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insurance_querylist);

        loanlist = (ListView) findViewById(R.id.list_insurancequery);
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Insurance Query List");
            ac.setDisplayHomeAsUpEnabled(true);
        }
        can = new ContentValues();
        sql = db.getReadableDatabase();
        loanlist.setOnItemClickListener(this);
        displayData();

    }


    @Override
    protected void onResume() {
        super.onResume();
        displayData();
        new getinsurancelist().execute();
    }

    private void displayData() {

        Cursor mCursor = sql.rawQuery("SELECT * FROM DetailsUser", null);


        if (mCursor.moveToFirst()) {
            do {
                UserInformation.UserFirstName = mCursor.getString(mCursor.getColumnIndex("FName"));
                UserInformation.UserLastName = mCursor.getString(mCursor.getColumnIndex("LName"));
                UserInformation.Email = mCursor.getString(mCursor.getColumnIndex("Email"));

            } while (mCursor.moveToNext());
        }

//        mCursor.close();
    }

    private class getinsurancelist extends AsyncTask<String, Void, SimpleAdapter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            param = new ArrayList<NameValuePair>();
            list = new ArrayList<HashMap<String, String>>();
            dialog = new ProgressDialog(Insurance_queryList.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected SimpleAdapter doInBackground(String... params) {
            param.add(new BasicNameValuePair("email",UserInformation.Email));
            Log.i("iou0",""+UserInformation.Email);
            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.user_insurance_list,"POST", param);

            if (jobj != null) {
                try {

                    JSONArray array = jobj.getJSONArray("products");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        id = jsonObject.getString("id");
                        mDate=jsonObject.getString("date");
                        insurance_type=jsonObject.getString("type_of_insurance");

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("date",mDate);
                        map.put("type_insurance",insurance_type);
                        map.put("id",id);
                        list.add(map);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(SimpleAdapter result) {
            super.onPostExecute(result);
            String[] from = {"date", "type_insurance", "id"};
            int[] to = {R.id.membername_insurance, R.id.type_insurance, R.id.id_list_insurance};
            adapter = new SimpleAdapter(Insurance_queryList.this,
                    list, R.layout.insurancelist_structure, from, to);
            loanlist.setAdapter(adapter);
            dialog.dismiss();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String id_ = list.get(position).get("id");
        Intent i = new Intent(Insurance_queryList.this, Insurance_queryData.class);
        i.putExtra("id", id_);
        startActivity(i);
        finish();
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