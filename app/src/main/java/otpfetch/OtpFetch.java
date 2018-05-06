package otpfetch;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.SplashScreen;
import shared_pref.UserInformation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sqlitedatabase.LoginData;

/**
 * Created by Harsh on 3/29/2016.
 */
public class OtpFetch extends Activity implements View.OnClickListener {
    TextView resend, otp;
    String otps;
    String s;
    Intent intent;

    SQLiteDatabase sql;
    ContentValues can = new ContentValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otpfetch);

        resend = (TextView) findViewById(R.id.otpresend);
        otp = (TextView) findViewById(R.id.otptxt);

        LoginData db = new LoginData(this);
        sql = db.getWritableDatabase();

        resend.setOnClickListener(this);
        intent = getIntent();

        String msg = intent.getStringExtra("message");
        s = intent.getStringExtra("invoked");
        Log.i("broading", "intent " + s);
        Log.i("broading", "intent " + msg);

        if (msg != null) {
            String nmber = msg.replaceAll("[^0-9]+", " ");
            Log.i("broading", "otp --" + nmber);
            otp.setText("" + nmber);

            otps = otp.getText().toString();
            Pattern p = Pattern.compile("([0-9])");
            Matcher m = p.matcher(otps);

            if (m.find()) {
                can.put("FName", UserInformation.UserFirstName);
                can.put("LName", UserInformation.UserLastName);
                can.put("Email", UserInformation.Email);
                can.put("Contactno", UserInformation.Phone);

                sql.insert("DetailsUser", null, can);

                startActivity(new Intent(OtpFetch.this, SplashScreen.class));
                overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                finish();
            }
        } else {

//            Toast.makeText(OtpFetch.this, "Unable to fetch otp... please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void receivedSms(String message) {

        try {
            Log.i("broading", "inside " + message.contains("is"));
            if (message.contains("is")) {
                Log.i("broadingsss", "message  " + message);
            }
            try {
                Log.i("broadingsss", "message  " + message);
                otp.setText(message);
                otps = otp.getText().toString();

                Pattern p = Pattern.compile("([0-9])");
                Matcher m = p.matcher(otps);

                if (m.find()) {

                    startActivity(new Intent(OtpFetch.this, SplashScreen.class));
                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
                    Log.i("broading", " otp fetch user");
                    finish();

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {

            case R.id.otpresend:
                startActivity(new Intent(OtpFetch.this, Otp.class));

                break;
        }
    }

    @Override
    public void onBackPressed() {

    }
}