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
public class Loan_queryList extends AppCompatActivity implements
        AdapterView.OnItemClickListener {

    ListAdapter adapter;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    ArrayList<HashMap<String, String>> list;
    ListView loanlist;

    ArrayList<NameValuePair> param;
    String id, mDate,amnt_borrow,loan_type;
    LoginData db = new LoginData(this);
    SQLiteDatabase sql;
    ContentValues can ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_querylist);

        loanlist = (ListView) findViewById(R.id.list_loanquery);
        sql = db.getReadableDatabase();
        can = new ContentValues();
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Loan Query List");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        loanlist.setOnItemClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        displayData();
       new getloanlist().execute();
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

    private class getloanlist extends AsyncTask<String, Void, SimpleAdapter> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            param = new ArrayList<NameValuePair>();
            list = new ArrayList<HashMap<String, String>>();
            dialog = new ProgressDialog(Loan_queryList.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected SimpleAdapter doInBackground(String... params) {

            param.add(new BasicNameValuePair("email",UserInformation.Email ));


            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.user_loan_list, "POST", param);

            if (jobj != null) {
                try {

                    JSONArray array = jobj.getJSONArray("products");
                    Log.i("products", "" + array);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        mDate=jsonObject.getString("date");
                        amnt_borrow=jsonObject.getString("borrow_amnt");
                        loan_type=jsonObject.getString("loan_type");
//                        UserInformation.ids=jsonObject.getString("id");
                        id = jsonObject.getString("id");

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("user_name",mDate);
                        map.put("borrow_amnt",amnt_borrow);
                        map.put("loan_type",loan_type);
                        map.put("id",id);

                        list.add(map);
                        Log.i("list", "" + list);

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
            String[] from = {"loan_type", "user_name","borrow_amnt", "id"};
            int[] to = {R.id.type_loan, R.id.membername_loan, R.id.amnt_loan, R.id.id_list};

            adapter = new SimpleAdapter(Loan_queryList.this, list, R.layout.loanlist_structure, from, to);
            loanlist.setAdapter(adapter);

            if (dialog!=null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        String id_ = list.get(position).get("id");

        Intent i = new Intent(Loan_queryList.this, Loan_queryData.class);
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