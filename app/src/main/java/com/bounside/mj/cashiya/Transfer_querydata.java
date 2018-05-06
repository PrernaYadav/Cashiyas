package com.bounside.mj.cashiya;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import shared_pref.SharedPref;

/**
 * Created by Neeraj Sain on 9/3/2016.
 */
public class Transfer_querydata extends AppCompatActivity {
    TextView enqtype,name,gender,dob,email,adhar,pancard,city,contact,ExistingLoan,ouutstandingloan,intersest,loanbank,grossincome,netincome;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    String id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transfer_querydata);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Loan Transfer Query Data");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        enqtype = (TextView) findViewById(R.id.transenq);
        name = (TextView) findViewById(R.id.transname);
        gender = (TextView) findViewById(R.id.transgender);
        dob = (TextView) findViewById(R.id.transdob);
        email = (TextView) findViewById(R.id.transemail);
        city= (TextView) findViewById(R.id.transcity);
        contact= (TextView) findViewById(R.id.transcontact);
        ExistingLoan= (TextView) findViewById(R.id.transloanstart);
        adhar = (TextView) findViewById(R.id.transoutstandblnc);
        pancard = (TextView) findViewById(R.id.pancard_viewtrans);
        ouutstandingloan = (TextView) findViewById(R.id.outstandloan_tenure);
        intersest= (TextView) findViewById(R.id.transinterestrate);
        loanbank= (TextView) findViewById(R.id.transloanbank);
        grossincome= (TextView) findViewById(R.id.transgross);
        netincome= (TextView) findViewById(R.id.transincome);

        Intent intent = getIntent();
        id=intent.getStringExtra("id");

    }

    @Override
    protected void onResume() {
        super.onResume();
        new getdata().execute();
    }

    public class getdata extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Transfer_querydata.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();
            param.add(new BasicNameValuePair("id",id));
            JSONObject jobj = jsonParser.makeHttpRequest(
                    SharedPref.user_transfer_data, "POST", param);
            String message = "";
            try {
                JSONArray array = jobj.getJSONArray("products");
                Log.i("fghfghfgh", "" + array);
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);

                    SharedPref.enqtypetransfer = jsonObject.getString("type_transfer");
                    SharedPref.nametransfer = jsonObject.getString("name");
                    SharedPref.gendertransfer = jsonObject.getString("gender");
                    SharedPref.dobtransfer = jsonObject.getString("dob");
                    SharedPref.emailtransfer = jsonObject.getString("email");
                    SharedPref.citytransfer = jsonObject.getString("city");
                    SharedPref.contacttransfer = jsonObject.getString("contact");
                    SharedPref.panview = jsonObject.getString("pancard");
                    SharedPref.adharview = jsonObject.getString("adharcard");
                    SharedPref.ExistingLoantransfer = jsonObject.getString("existing_loan_start");
                    SharedPref.ouutstandingloantransfer = jsonObject.getString("outstanding_loan_tenure");
                    SharedPref.intersesttransfer = jsonObject.getString("interest_rate");
                    SharedPref.loanbanktransfer = jsonObject.getString("existing_loan_bank");
                    SharedPref.grossincometransfer = jsonObject.getString("gross_income");
                    SharedPref.netincometransfer = jsonObject.getString("monthly_income");


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

            enqtype.setText( SharedPref.enqtypetransfer);
            name.setText(SharedPref.nametransfer);
            gender.setText(SharedPref.gendertransfer);
            dob.setText(SharedPref.dobtransfer);
            email.setText(SharedPref.emailtransfer);
            city.setText(SharedPref.citytransfer);
            contact.setText(SharedPref.contacttransfer);
            ExistingLoan.setText(SharedPref.ExistingLoantransfer);
            pancard.setText(SharedPref.panview);
            adhar.setText(SharedPref.adharview);
            ouutstandingloan.setText(SharedPref.ouutstandingloantransfer);
            intersest.setText(SharedPref.intersesttransfer);
            loanbank.setText(SharedPref.loanbanktransfer);
            grossincome.setText(SharedPref.grossincometransfer);
            netincome.setText(SharedPref.netincometransfer);

        }
    }
}
