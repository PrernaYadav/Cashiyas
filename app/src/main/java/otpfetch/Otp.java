package otpfetch;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.SplashScreen;

import shared_pref.UserInformation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by JKB-2 on 3/25/2016.
 */
public class Otp extends Activity implements View.OnClickListener {
    EditText phone;
    Button submit;
    String successs = null, Phone = null;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp);

        phone = (EditText) findViewById(R.id.editText);
        submit = (Button) findViewById(R.id.button);

    }

    @Override
    public void onResume() {
        super.onResume();
        submit.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button:
                submituser();

                break;

            default:
                break;
        }
    }

    /*********** submit user *********/
    private void submituser() {

//        UserInformation.Phone = phone.getText().toString();
        Phone = phone.getText().toString();

        if (Phone.length() > 10) {

            new insertusermain().execute();

        } else {
            Toast.makeText(Otp.this, "Required field missing or Not Proper",
                    Toast.LENGTH_SHORT).show();
        }
    }


    public class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Otp.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("mobile", Phone));

            JSONObject jobj = jsonParser.makeHttpRequest(UserInformation.otp, "POST", param);
            String message = "";
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

            if(dialog!=null && dialog.isShowing())
            dialog.dismiss();

            Toast.makeText(Otp.this, "" + result, Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}