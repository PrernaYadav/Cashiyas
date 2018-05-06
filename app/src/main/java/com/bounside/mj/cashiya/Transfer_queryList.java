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
 * Created by Neeraj Sain on 9/3/2016.
 */
public class Transfer_queryList extends AppCompatActivity implements
        AdapterView.OnItemClickListener {
    TextView tv;
    ListAdapter adapter;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    ArrayList<HashMap<String, String>> list;
    ListView transferlist;
    ContentValues can;
    ArrayList<NameValuePair> param;
    String id,mDate,insurance_type;
    LoginData db = new LoginData(this);
    SQLiteDatabase sql;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_query_list);

        transferlist = (ListView) findViewById(R.id.list_transferquery);
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Transfer Query List");
            ac.setDisplayHomeAsUpEnabled(true);
        }
        can = new ContentValues();
        sql = db.getReadableDatabase();
        transferlist.setOnItemClickListener(this);
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
            dialog = new ProgressDialog(Transfer_queryList.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.setIndeterminate(false);
            dialog.show();
        }

        @Override
        protected SimpleAdapter doInBackground(String... params) {
            param.add(new BasicNameValuePair("email",UserInformation.Email));
            Log.i("iou0",""+UserInformation.Email);
            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.user_transfer_list, "POST", param);

            if (jobj != null) {
                try {

                    JSONArray array = jobj.getJSONArray("products");
                    Log.i("array", "" + array);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject jsonObject = array.getJSONObject(i);
                        id =jsonObject.getString("id");
                        mDate=jsonObject.getString("date");
                        insurance_type=jsonObject.getString("type_transfer");

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("user_name",mDate);
                        map.put("type_insurance",insurance_type);
                        map.put("id",id);
                        list.add(map);
                        Log.i("List", "" + list);

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

            String[] from = {"user_name", "type_insurance"};
            int[] to = {R.id.membername_insurance, R.id.type_insurance};
            adapter = new SimpleAdapter(Transfer_queryList.this,
                    list, R.layout.insurancelist_structure, from, to);
            transferlist.setAdapter(adapter);

            if(dialog!=null && dialog.isShowing())
            {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String id_ = list.get(position).get("id");

        Intent i = new Intent(Transfer_queryList.this, Transfer_querydata.class);
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