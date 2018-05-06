package bank;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.SplashScreen;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sqlitedatabase.ExpenseData;

/**
 * Created by Neeraj Sain on 11/8/2016.
 */

public class Billsreminder extends Activity {
    SQLiteDatabase sql;
    ContentValues can;
    ExpenseData bbd = new ExpenseData(this);

    Cursor cursor,cursor1;
    Button proceed;
    String dbmoney;
    boolean b,c;
    List<String> numbers = new ArrayList<String>();
    List<String> numbers1 = new ArrayList<String>();
    String Numbervalue,billdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billsreminder);

        sql = bbd.getWritableDatabase();
        can = new ContentValues();


        proceed = (Button) findViewById(R.id.billsnext);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetSms().execute();
            }
        });
    }


    private class GetSms extends AsyncTask<String, Void, String> {
        ProgressDialog pd;

        int pdstatus=0;

        Handler mHandler = new Handler();

        // Map<String, String> mp = new HashMap<String, String>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

// set the drawable as progress drawable


            pd = new ProgressDialog(Billsreminder.this);
            pd.setCancelable(false);
            pd.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String abc = "bank";
            Uri myMessage = Uri.parse("content://sms");
            ContentResolver cr = Billsreminder.this.getContentResolver();
            Cursor cursor = cr.query(myMessage, new String[]{"_id", "address", "date",
                    "body"}, null, null, null);
            startManagingCursor(cursor);
            if (cursor != null) {
                cursor.moveToLast();
                if (cursor.getCount() > 0) {

                    do {
                        String Number = cursor.getString(cursor
                                .getColumnIndex("address"));
                        String Body = cursor.getString(cursor
                                .getColumnIndex("body"));

                        String _id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));

                        String dat = cursor.getString(cursor.getColumnIndexOrThrow("date"));

                        String body1 = Body.toUpperCase();
                        if(body1.contains("DUE ON") && body1.contains("BILL"))
                        {
                            if (Body.contains("Rs")) {
                                //splitText = cc.split("Rs");
                                dbmoney = String.valueOf(getdebitedAmount(Body));
                            }
                            else {
                                dbmoney = "0.0";
                            }

                            String billdate= getbilldate(Body);
                            Log.i("billdate", "" + billdate);
//                            String[] calend = billdate.split(".");
//
//                            int month = Integer.parseInt(calend[1]);
//                            Log.i("month", "" + month);
//                            int year = Integer.parseInt(calend[2]);
//                            Log.i("year", "" + year);

                            Long timestamp = Long.parseLong(billdate);
                            Calendar calendar = Calendar.getInstance();
                            calendar.setTimeInMillis(timestamp);
                            Date finaldate = calendar.getTime();
                            Log.i("finaldate", "" + finaldate);
                            String smsDate = finaldate.toString();
                            Log.i("smsDate", "" + smsDate);
                            calendar.setTime(finaldate);


                            SimpleDateFormat month_year = new SimpleDateFormat("MM");
                            String month_name1 = month_year.format(calendar.getTime());
                            Log.i("month", "" + month_name1);


                            SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                            String month_name2 = month_year1.format(calendar.getTime());
                            Log.i("month", "" + month_name2);
                            Log.i("vfgyh",""+Body);



                            SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy");
                            String month_name = month_date.format(calendar.getTime());
                            Log.i("month", "" + month_name);

                            String vendorname=getvendorname(Body);

                            b = checkduplicacy(_id);
                            if (!b) {
                                can.put("msgids", _id);
                                can.put("billsdscr", Body);
                                can.put("amount", dbmoney);
                                can.put("billdate", billdate);
                                can.put("vendorname", vendorname);
                                can.put("billscat", "Bills");
                            }

                        }
                    } while (cursor.moveToPrevious());
                }
//                cursor.close();
            }
            return abc;

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            pd.dismiss();
            Intent in = new Intent(Billsreminder.this, SplashScreen.class);
            startActivity(in);
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

            Log.i("address", s);

        }
    }

    private Boolean checkduplicacy(String idd) {
        Log.i("idd", idd);
        cursor = sql.rawQuery("SELECT * FROM billspayment where msgids = '"+idd+"'", null);
        return (cursor != null) && (cursor.getCount() > 0);

    }

    private String getvendorname(String body) {

        String bodyss = body.toUpperCase();
        String namevendr="";

        if(bodyss.contains("AIRTEL"))
        {
            namevendr = "Airtel";

        }

        else if(bodyss.contains("IDEA")){
            namevendr = "Idea";
        }
        else if(bodyss.contains("VODAFONE")){
            namevendr ="Vodafone";
        }
        return namevendr;
    }

    private String getbilldate(String body) {
        String matchertext = body;
        Pattern p = Pattern.compile("\\d{2}\\.\\d{2}\\.\\d{2}");

        Matcher m = p.matcher(matchertext);

        while (m.find()) {
            numbers1.add(m.group());
        }

        if (numbers1.size() > 0) {
            billdd = numbers1.get(0);

            Log.e("amount", "" + numbers1.get(0));


        }
        numbers1.clear();
        Log.e("billdd", "" + billdd);
        return billdd;
    }

    public double getdebitedAmount(String Body) {

        Log.e("Body", "" + Body);
        double debited_amount = 0;
        String splitText[] = Body.split("Rs");
        String matchertext = null;
        if (splitText.length >= 0 ) {
            matchertext = splitText[1];
            Log.e("matchertext1", "" + matchertext);
        } else {
            matchertext = Body;
            Log.e("matchertext2", "" + matchertext);
        }
        Pattern p = Pattern.compile("-?\\d+(,\\d+)*?\\.?\\d+?");

        Matcher m = p.matcher(matchertext);
        while (m.find()) {
            numbers.add(m.group());
        }

        if (numbers.size() > 0) {
            Numbervalue = numbers.get(0);

            Log.e("amount", "" + numbers.get(0));


        }
        if (Numbervalue != null) {
            debited_amount = Double.valueOf(Numbervalue.replaceAll("[,]",""));
        }
        numbers.clear();
        Log.e("debited_amount", "" + debited_amount);
        return debited_amount;

    }
}
