package acunt;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.JSONParser;
import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import otpfetch.OtpFetch;
import shared_pref.SharedPref;
import shared_pref.UserInformation;

/**
 * Created by hp on 2/8/2017.
 */

public class CreateNewProfile extends AppCompatActivity {
    RadioButton radioButtonReceivedOtponRegisteredNumber, radioButtonReceivedOtponUpdatedNumber;
    RadioGroup radioGroupAlreadyRegistered;
    String a = UserInformation.Email;
    private static String url;
    Button btnSend1, btnSend;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    TextView userPhoneNo;
    EditText updatePhoneNumber;
    String sUpadtedNumber;
    JSONArray user = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup3);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Create New Profile");
        }

        radioGroupAlreadyRegistered = (RadioGroup) findViewById(R.id.radioGroupAlreadyRegistered);
        radioButtonReceivedOtponRegisteredNumber = (RadioButton) findViewById(R.id.radioButtonReceivedOtponRegisteredNumber);
        radioButtonReceivedOtponUpdatedNumber = (RadioButton) findViewById(R.id.radioButtonReceivedOtponUpdatedNumber);
        btnSend1 = (Button) findViewById(R.id.btnSend1);
        btnSend = (Button) findViewById(R.id.btnSend);
        updatePhoneNumber = (EditText) findViewById(R.id.updatePhoneNumber);

        btnSend1.setVisibility(View.GONE);
        updatePhoneNumber.setVisibility(View.GONE);

        btnSend.setVisibility(View.GONE);

        radioGroupAlreadyRegistered.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.radioButtonReceivedOtponRegisteredNumber) {
                    new getPhoneNo().execute();
                    btnSend1.setVisibility(View.VISIBLE);
                    updatePhoneNumber.setVisibility(View.GONE);
                    btnSend.setVisibility(View.GONE);
                    userPhoneNo.setVisibility(View.VISIBLE);

                } else if (checkedId == R.id.radioButtonReceivedOtponUpdatedNumber) {
                    btnSend.setVisibility(View.VISIBLE);
                    updatePhoneNumber.setVisibility(View.VISIBLE);
                    userPhoneNo.setVisibility(View.GONE);
                    btnSend1.setVisibility(View.GONE);
                }
            }

        });

        btnSend1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new sendOtp().execute();
            }
        });
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sUpadtedNumber = updatePhoneNumber.getText().toString();
                if (updatePhoneNumber.length() > 10 || updatePhoneNumber.length() < 10) {
                    Toast.makeText(CreateNewProfile.this, "Mobile number must be 10 number", Toast.LENGTH_LONG).show();
                } else {
                    new sendOtpOnNewNo().execute();
                }
            }

        });
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(dialog!=null && dialog.isShowing())  {
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dialog!=null && dialog.isShowing())  {
            dialog.dismiss();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog!=null && dialog.isShowing())  {
            dialog.dismiss();
        }
    }

    private class getPhoneNo extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            userPhoneNo = (TextView) findViewById(R.id.uid);

            pDialog = new ProgressDialog(CreateNewProfile.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(UserInformation.register_signin + a);
            return json;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if(pDialog!=null && pDialog.isShowing())
            pDialog.dismiss();

            try {
                user = json.getJSONArray("product");
                JSONObject c = user.getJSONObject(0);

                String id = c.getString("phone");
                userPhoneNo.setText(id);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class sendOtp extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            dialog = new ProgressDialog(CreateNewProfile.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("email", UserInformation.Email));

            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.sendotponregisternumber, "POST", param);
            String message = "";
            try {
                message = jobj.getString("msg");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("result", "" + result);

            try {
                dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }

            startActivity(new Intent(CreateNewProfile.this, OtpFetch.class));
        }
    }

    private class sendOtpOnNewNo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(CreateNewProfile.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("email", UserInformation.Email));
            param.add(new BasicNameValuePair("phone", sUpadtedNumber));

            Log.i("Email", "" + UserInformation.Email);

            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.sendotponupdatenumber, "POST", param);
            String message = "";
            String successs = "";
            try {
                message = jobj.getString("msg");
                successs = jobj.getString("status");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.i("result", "" + result);
            dialog.dismiss();

            Intent i = new Intent(CreateNewProfile.this, OtpFetch.class);

            startActivity(i);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}