package bank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import LanguagePreference.SavePreference;
import shared_pref.UserInformation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sqlitedatabase.Bankmessagedb;
import sqlitedatabase.LoginData;

/**
 * Created by Rasika on 11/14/2016.
 */
public class ProfileCreated extends Activity {
    Jsonparse jsonParser = new Jsonparse(this);
    JSONObject jobj_arr = new JSONObject();
    List<String> lst = new ArrayList<String>();
    List<String> lst1 = new ArrayList<String>();
    private List<String> list = new ArrayList<>();
    ProgressDialog dialog;

    Button okl;
    Bankmessagedb db = new Bankmessagedb(this);
    LoginData ld = new LoginData(this);
    SQLiteDatabase sql, sql1;
    ContentValues can;
    List<Map> list_msg = new ArrayList<>();
    String Body_message;
    String Type;
    String Amounts;
    String Transaction_date;
    LinkedHashMap<String, String> hmap;
    String Email_id;
    ArrayList<String> List = new ArrayList<String>();
    String s;
    ArrayList<String> mylistss = new ArrayList<String>();
    ArrayList<String> mylistss_Type = new ArrayList<String>();
    ArrayList<String> mylistss_Amounts = new ArrayList<String>();
    ArrayList<String> mylistss_Transaction_date = new ArrayList<String>();
    private SavePreference savePreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storetransaction);

        sql = db.getReadableDatabase();
        sql1 = ld.getReadableDatabase();
        can = new ContentValues();
        displayData();
        displayData_id();

        okl = (Button) findViewById(R.id.movetonext_mainactivity);

        okl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new saveBankTxnMessages().execute();

            }
        });

        Intent intent = new Intent(ProfileCreated.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

        savePreference = new SavePreference(ProfileCreated.this);

    }


    private void displayData() {

        Cursor mCursor = sql.rawQuery("SELECT * FROM trans_msg ORDER BY yrdata desc,mnthdate desc,changdate desc,msgtime desc", null);

        if (mCursor.moveToFirst()) {
            do {
                String bb = mCursor.getString(mCursor.getColumnIndex("nameofbank"));
                String cc = mCursor.getString(mCursor.getColumnIndex("body"));
                String dd = mCursor.getString(mCursor.getColumnIndex("transdate"));
                String ee = mCursor.getString(mCursor.getColumnIndex("category"));
                String ff = mCursor.getString(mCursor.getColumnIndex("type"));
                String gg = mCursor.getString(mCursor.getColumnIndex("changdate"));
                String hh = mCursor.getString(mCursor.getColumnIndex("amount"));
                String iii = mCursor.getString(mCursor.getColumnIndex("accunttype"));
                String jj = mCursor.getString(mCursor.getColumnIndex("accuntnumber"));
                Body_message = "Name of Bank=" + bb + "finishbank" + " Transaction Date=" + dd + "finishtrans" +
                        "Category=" + ee + "finishcat" + "Type=" + ff + "finishtype" + "Amount=" + hh + "finishamount" + "accountno=" + jj + "finishacno" +
                        "Body=" + cc + "finishbody" + "Account Type=" + iii + ".....";

                Log.i("dataget", "" + Body_message);
                mylistss.add(Body_message);
                Log.i("dataget", "grtr" + mylistss);

            } while (mCursor.moveToNext());
        }


//        mCursor.close();
    }

    private void displayData_id() {

        Cursor mCursor = sql1.rawQuery("SELECT * FROM DetailsUser", null);


        if (mCursor.moveToFirst()) {
            do {
                Email_id = mCursor.getString(mCursor.getColumnIndex("Email"));
                Log.i("datagetemailid", "" + Email_id);

            } while (mCursor.moveToNext());
        }

//        mCursor.close();
    }


    public class saveBankTxnMessages extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = new ProgressDialog(ProfileCreated.this);
//            dialog.setCancelable(false);
//            dialog.setMessage("Processing...");
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("nameofbank", "" + mylistss));
            Log.i("message", "..........." + mylistss);
            param.add(new BasicNameValuePair("email_id", "" + Email_id));
            Log.i("message", "..........." + Email_id);

            Log.i("tag", "In Controller" + param);


            JSONObject jobj = jsonParser.makeHttpRequest(UserInformation.transaction_data, "POST", param);
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
//            dialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        startActivity(intent);
        finishAffinity();
        System.exit(0);
    }
}
