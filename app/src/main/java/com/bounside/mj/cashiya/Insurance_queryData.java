package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

import shared_pref.SharedPref;
import shared_pref.UserInformation;

/**
 * Created by Tamanna on 7/28/2016.
 */
public class Insurance_queryData extends AppCompatActivity {
    TextView namei,emaili,contacti, TvDate, ser;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    String date, id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insurance_querydata);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Insurance Query Data");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        namei = (TextView) findViewById(R.id.tvnameins_insurancequery);
        emaili = (TextView) findViewById(R.id.tvmailins_insurancequery);
        contacti = (TextView) findViewById(R.id.tvcontactins_insurancequery);
        ser= (TextView) findViewById(R.id.tvservice_insurancequery);
        TvDate= (TextView) findViewById(R.id.tvdate_insurancequery);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    @Override
    protected void onResume() {
        super.onResume();
       new getdata().execute();
    }

    private class getdata extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Insurance_queryData.this);
            dialog.setCancelable(false);
            dialog.setMessage("Loading...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id",id));
            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.user_insurance_data, "POST", param);

            String message = "";
            try {
                JSONArray array = jobj.getJSONArray("products");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    SharedPref.namei_insurance = jsonObject.getString("name");
                    SharedPref.emaili_insurance = jsonObject.getString("email");
                    SharedPref.contacti_insurance = jsonObject.getString("phone");
                    date = jsonObject.getString("date");
                    SharedPref.ser_insurance = jsonObject.getString("type_of_insurance");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();

            namei.setText(SharedPref.namei_insurance);
            emaili.setText(SharedPref.emaili_insurance);
            contacti.setText(SharedPref.contacti_insurance);
            TvDate.setText(date);
            ser.setText(SharedPref.ser_insurance);

        }
    }
}
