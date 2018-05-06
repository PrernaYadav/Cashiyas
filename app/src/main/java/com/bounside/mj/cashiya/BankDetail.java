package com.bounside.mj.cashiya;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import shared_pref.SharedPref;
import shared_pref.UserInformation;
import sqlitedatabase.Datab_notify;

/**
 * Created by hp on 2/9/2017.
 */

public class BankDetail extends AppCompatActivity {

    Message msg = new Message();

    // URL to get contacts JSON
    private static String bank_detail_url = SharedPref.bank_condition;

    public static ImageView imageView;
    String id;
    String imageUrl;
    Jsonparse jsonp = new Jsonparse(BankDetail.this);
    ArrayList<HashMap<String, String>> bankList;
    private String TAG = MainActivity.class.getSimpleName();
    private ProgressDialog pDialog;
    private ProgressDialog pd;
    private ListView lv;

    private ContentValues can;
    SQLiteDatabase sql;
    Datab_notify dn;

    private String messagerxch = "";
    private String messagecfl = "";
    private String messagerbl = "";
    private String statusrxch = "";
    private String statuscfl = "";
    private String statusrbl = "";
    private String statuskotak = "";
    private String referenceCodekotak = "";
    private String eligigbleLoanAmtKotak = "";
    private String roikotak = "";
    private String status1 = "";
    private String final_status = "";
    String errorCode = "",
            description = "",
            ROI = "",
            maxAllowedLoanAmount = "",
            FinnOneId = "";
    private String messageBank;
    private String errorCodeKotak;
    private String errorInfoKotak;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bankdetail);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setTitle("Eligible for Banks");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        can = new ContentValues();
        dn = new Datab_notify(this);
        sql = dn.getWritableDatabase();
        bankList = new ArrayList<>();

        lv = (ListView) findViewById(R.id.list_bank_details);

        imageView = (ImageView) findViewById(R.id.list_item_image);

        lv.setOnItemClickListener(new ListItemClickHandler());
        new GetBanks().execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
    }

    private class GetBanks extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(BankDetail.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(bank_detail_url + "?random=" + SharedPref.random);

            Log.i(TAG, "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("product");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        String status = c.getString("status");

                        if (status.equals("0")) {

                            messageBank = c.getString("msg");

                            msg.arg1 = 1;
                            handler.sendMessage(msg);

                        } else {

                            id = c.getString("id");
                            String name = c.getString("bank_name");
                            String interest_rate = c.getString("interest_rate");

                            imageUrl = c.getString("image");

                            HashMap<String, String> bank = new HashMap<>();

                            // adding each child node to HashMap key => value
                            bank.put("id", id);
                            bank.put("name", name);
                            bank.put("interest_rate", interest_rate);
                            bank.put("image_url", imageUrl);

                            /*try {
                                Picasso.with(getBaseContext())
                                        .load(imageUrl)
                                        .into(imageView);

                                // adding bank to bank list
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Log.i("ExceptionMessage", e.getMessage());
                            }
*/
                            bankList.add(bank);

                        }
                    }

                } catch (final JSONException e) {
                    Log.e(TAG, "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),
                                    "Json parsing error: " + e.getMessage(),
                                    Toast.LENGTH_LONG)
                                    .show();
                        }
                    });
                }

            } else {
                Log.e(TAG, "Couldn't get json from server.");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Couldn't get json from server. Check LogCat for possible errors!",
                                Toast.LENGTH_LONG)
                                .show();
                    }
                });
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (pDialog != null && pDialog.isShowing())
                pDialog.dismiss();
            /*** Updating parsed JSON data into ListView * */
            ListAdapter adapter = new SimpleAdapter(
                    BankDetail.this,
                    bankList,
                    R.layout.list_item,
                    new String[]{"id", "name", "interest_rate"},
                    new int[]{R.id.id, R.id.name, R.id.bankinterest});

            if (imageView != null) {
                try {
                    Glide.with(getBaseContext())
                            .load(imageUrl)
                            .into(imageView);
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("ExceptionMessage", e.getMessage());     // ExceptionMessage: Target must not be null.
                }
            }
            lv.setAdapter(adapter);

        }
    }

    Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            if (msg.arg1 == 1) {

//                Toast.makeText(BankDetail.this, "No data for this option", Toast.LENGTH_LONG).show();
//                finish();
                new AlertDialog.Builder(BankDetail.this)
                        .setTitle("")
                        .setMessage("" + messageBank)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(BankDetail.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                startActivity(i);
                            }
                        })
                        .show();
            }
            return false;
        }
    });

    private class ListItemClickHandler implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {

            if (adapter.getItemAtPosition(position).toString().contains("Rupya Exchange")) {
                new getRupyaExchange().execute();
//                bankList.remove(adapter.getItemAtPosition(position));
                bankList.remove(adapter.getSelectedItem());

//                adapter.removeViewAt(position);

//                bankList.notify();

            }
            if (adapter.getItemAtPosition(position).toString().contains("Capital First")) {
                new getCFL().execute();
                bankList.remove(adapter.getSelectedItem());

                lv.removeViewAt(position);

//                bankList.notify();
            }
            if (adapter.getItemAtPosition(position).toString().contains("RBL")) {
                new getRBL().execute();
                bankList.remove(adapter.getSelectedItem());
//                              lv.removeViewAt(position);

            }
            if (adapter.getItemAtPosition(position).toString().contains("kotak")) {
                new getKotak().execute();
                bankList.remove(adapter.getSelectedItem());
//                              lv.removeViewAt(position);

            }
        }

        private class getRupyaExchange extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = new ProgressDialog(BankDetail.this);
                pDialog.setCancelable(false);
                pDialog.setMessage("Processing Loan Request for rupaiya exchange...");
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("id", id));

                JSONObject jobj = jsonp.makeHttpRequest(
                        UserInformation.rupya_exchange, "POST", param);

                try {
                    // Getting JSON Array node
                    JSONArray responseBody = jobj.getJSONArray("product");
                    JSONObject jsonobj = responseBody.getJSONObject(0);
                    statusrxch = jsonobj.getString("status");
                    messagerxch = jsonobj.getString("message");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return statusrxch;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                pDialog.dismiss();
                if (pd != null && pd.isShowing()) {
                    pd.dismiss();
                }
                if (result.equals("true")) {

                    new AlertDialog.Builder(BankDetail.this)
                            .setTitle("Loan Submission Response")
                            .setMessage("Loan Application Approved for Rupaiya Exchange")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(BankDetail.this, BankDetail.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(i);

//                                    bankList.remove(bankList.);

                                }
                            })
                            .show();

                    SharedPref.random = null;

                } else if (result.equals("false")) {

                    new AlertDialog.Builder(BankDetail.this)
                            .setTitle("Loan Submission Response")
                            .setMessage("Loan Application Rejected by Rupaiya Exchange" + "\n" + messagerxch)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(i);
                                }
                            })
                            .show();
                    SharedPref.random = null;
                }
//            else
//                Toast.makeText(BankDetail.this, "Error in submission... Please try again", Toast.LENGTH_SHORT).show();

            }
        }


        private class getCFL extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(BankDetail.this);
                pDialog.setCancelable(false);
                pDialog.setMessage("Processing Loan Request for cfl bank...");
                pDialog.setCanceledOnTouchOutside(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("id", id));

                JSONObject jobj = jsonp.makeHttpRequest(
                        UserInformation.cfl, "POST", param);

                try {
                    JSONArray responseBody = jobj.getJSONArray("product");
                    JSONObject jsonobj = responseBody.getJSONObject(0);
                    status1 = jsonobj.getString("status1");
                    statuscfl = jsonobj.getString("status");
                    final_status = jsonobj.getString("final_Status");
                    errorCode = jsonobj.getString("errorCode");
                    description = jsonobj.getString("description");
                    messagecfl = jsonobj.getString("message");
                    ROI = jsonobj.getString("ROI");
                    maxAllowedLoanAmount = jsonobj.getString("maxAllowedLoanAmount");
                    FinnOneId = jsonobj.getString("FinnOneId");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return status1;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (result.equals("")) {

                    if (final_status.equals("ACCEPT")) {

                        if (pDialog != null && pDialog.isShowing())
                            pDialog.dismiss();
                        pDialog = null;

                        new AlertDialog.Builder(BankDetail.this)
                                .setTitle("Loan Submission Response CFL")
                                .setMessage("Loan Application " + final_status + " for Capital First /n" + errorCode
                                        + "/n" + description + "/n" + statuscfl + "/n" + ROI + maxAllowedLoanAmount + "/n" + FinnOneId)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                    Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                    startActivity(i);
//                                    finish();
                                    }
                                })
                                .show();
                        SharedPref.random = null;

                    } else if (final_status.equals("REJECT")) {

                        if (pDialog != null && pDialog.isShowing())
                            pDialog.dismiss();

                        new AlertDialog.Builder(BankDetail.this)
                                .setTitle("Loan Submission Response")
                                .setMessage("Loan Application Rejected by Capital First" + "\n" + messagecfl)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                    Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                    startActivity(i);
//                                    finish();
                                    }
                                })
                                .show();
                        SharedPref.random = null;
                    }
                } else {

                    if (pDialog != null && pDialog.isShowing())
                        pDialog.dismiss();
                    pDialog = null;
                    new AlertDialog.Builder(BankDetail.this)
                            .setTitle("Loan Submission Response CFL")
                            .setMessage("Loan Application Rejected by CFL Bank")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                    Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                    startActivity(i);
//                                    finish();
                                }
                            })
                            .show();
                    SharedPref.random = null;
                }

//                else
//                  Toast.makeText(BankDetail.this, "Error in submission... Please try again", Toast.LENGTH_SHORT).show();
            }
        }

        private class getRBL extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                pDialog = new ProgressDialog(BankDetail.this);
                pDialog.setCancelable(false);
                pDialog.setMessage("Processing Loan Request for RBL...");
                pDialog.setCanceledOnTouchOutside(true);
                pDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

                param.add(new BasicNameValuePair("id", id));

                JSONObject jobj = jsonp.makeHttpRequest(UserInformation.rbl, "POST", param);

                try {
                    JSONArray responseBody = jobj.getJSONArray("product");
//                JSONArray responseBody = jobj.getJSONArray("");

                    Log.i(TAG, "Response from RBL_url: " + responseBody);

                    JSONObject jsonobj = responseBody.getJSONObject(0);
                    statusrbl = jsonobj.getString("Status");
                    final_status = jsonobj.getString("EligibilityDes");
                    messagerbl = jsonobj.getString("message");

                } catch (Exception e) {
                    e.printStackTrace();
                }

                return statusrbl;
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);

                if (pDialog != null && pDialog.isShowing())
                    pDialog.dismiss();
                pDialog = null;

                Log.i("RBL", "" + result);

                if (result != null) {
                    Log.i("RBL", "" + result);

                    if (statusrbl.equals("0")) {
                        new AlertDialog.Builder(BankDetail.this)
                                .setTitle("Loan Submission Response RBL")
                                .setMessage("Loan Application Rejected" + "\n" + final_status + "\n" + messagerbl)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(i);
//                                    finish();
                                    }
                                })
                                .show();
                        SharedPref.random = null;
                    }

                    else{
                        new AlertDialog.Builder(BankDetail.this)
                                .setTitle("Loan Submission Response RBL")
                                .setMessage("Loan Application Accepted" + "\n" + "Eligibility Description:" + "\n" + final_status + "\n" + messagerbl)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(i);
//                                    finish();
                                    }
                                })
                                .show();
                        SharedPref.random = null;

                    }
                }
            }
        }
    }

    private class getKotak extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BankDetail.this);
            pDialog.setCancelable(false);
            pDialog.setMessage("Processing Loan Request for Kotak Mahindra Bank...");
            pDialog.setCanceledOnTouchOutside(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("id", id));

            JSONObject jobj = jsonp.makeHttpRequest(UserInformation.kotak, "POST", param);

            try {
                JSONArray responseBody = jobj.getJSONArray("product");
//                JSONArray responseBody = jobj.getJSONArray("");

                Log.i(TAG, "Response from Kotak_url: " + responseBody);

                JSONObject jsonobj = responseBody.getJSONObject(0);
                statuskotak = jsonobj.getString("Status");
//                    final_status = jsonobj.getString("EligibilityDes");

                if (statuskotak.equals("0")) {
                    errorCodeKotak = jsonobj.getString("Errorcode");
                    errorInfoKotak = jsonobj.getString("Errorinfo");
                } else {
                    referenceCodekotak = jsonobj.getString("ReferenceCode");
                    errorInfoKotak = jsonobj.getString("EligLnAmt");
                    roikotak = jsonobj.getString("ROI");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return statuskotak;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (pDialog != null && pDialog.isShowing())
                pDialog.dismiss();
            pDialog = null;

            Log.i("KOTAK", "" + result);

            if (result != null) {
                Log.i("KOTAK", "" + result);
                if (result.equals("0")) {
                    new AlertDialog.Builder(BankDetail.this)
                            .setTitle("Loan Submission Response Kotak Mahindra Bank")
                            .setMessage("Your loan request has been rejected by Kotak Mahindra Bank\n" + errorCodeKotak + "\n" + errorInfoKotak)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(i);
//                                    finish();
                                }
                            })
                            .show();
                    SharedPref.random = null;
                } else {

                    new AlertDialog.Builder(BankDetail.this)
                            .setTitle("Loan Submission Response Kotak Mahindra Bank")
                            .setMessage("Loan request accepted with reference code #" + referenceCodekotak + "\nEligible Loan Amount Rs." + eligigbleLoanAmtKotak + "\nRate of Interest" + roikotak)
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
//                                Intent i = new Intent(BankDetail.this, MainActivity.class);
//                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                                startActivity(i);
//                                    finish();
                                }
                            })
                            .show();
                    SharedPref.random = null;
                }
            }
        }
    }


    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder builder = new AlertDialog.Builder(BankDetail.this);
        // builder.setCancelable(false);
        builder.setTitle("");
        builder.setMessage("Your application is not complete. Incomplete application would not get processed. Proceed exiting the form?");
        builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent in1 = new Intent(BankDetail.this, MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in1);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
