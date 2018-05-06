package com.bounside.mj.cashiya;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.os.Handler;
import android.widget.Button;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bank.ProfileCreated;
import sqlitedatabase.Bankmessagedb;
import sqlitedatabase.Datab_notify;

public class SplashScreen extends Activity {

    ArrayList<String> sms_id = new ArrayList<>();
    ArrayList<String> sms_num = new ArrayList<>();
    ArrayList<String> sms_Name = new ArrayList<>();
    ArrayList<String> sms_dt = new ArrayList<>();
    ArrayList<String> sms_body = new ArrayList<>();
    List<String> numbers = new ArrayList<String>();
    String Numbervalue,Numbervalue1,Numbervalue2;
    Banklists lists= new Banklists();
    boolean b,c;
    String dbmoney;
    String accunt_number;
    int cnts,cnts1;
    float sum;
    float sum1,sum2;

    ProgressDialog pd;

    //db valuess

    SQLiteDatabase sql,sql1;
    ContentValues can,can1;
    Bankmessagedb bbd = new Bankmessagedb(this);
    Datab_notify dn = new Datab_notify(this);
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcomecashiya);

        sql = bbd.getWritableDatabase();
        can = new ContentValues();
        sql1 = dn.getWritableDatabase();
        can1 = new ContentValues();

        new GetSms().execute();
    }

    public String checkaccountnmberac(String body) {
        String ss= body.toUpperCase();

        Matcher numberMatcher = Pattern.compile("[X]{2}[0-9]{2,6}").matcher(ss) ;
        while (numberMatcher.find()) {
            numbers.add(numberMatcher.group());
        }

        if (numbers.size() > 0) {
            Numbervalue1 = numbers.get(0);
            Log.e("checkaccountnmber3", "" + numbers.get(0));
        }
        else {
//            Numbervalue1 ="Purchase";
        }

        numbers.clear();
        return Numbervalue1;
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

    public double getdebitedAmountss(String Body) {

        Log.e("Body", "" + Body);
        double debited_amount = 0;
        String splitText[] = Body.split("AMOUNT");
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

    public double getdebitedAmounts(String Body) {

        Log.e("Body", "" + Body);
        double debited_amount = 0;
        String splitText[] = Body.split("INR");
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

    public String checktypetrans(String Body) {
        String Boody1 = Body.toUpperCase();
        String type;
        if (Boody1.contains("CREDITED") || Boody1.contains("DEPOSITED")) {
            type = "CREDITED.";
        } else if (Boody1.contains("DEBITED") /*|| Boody1.contains("DEBIT") */|| Boody1.contains("WITHDRAWN")) {
            type = "DEBITED.";
        } else if (Boody1.contains("PURCHASE")) {
            type = "PURCHASE.";
        }
        else if (Boody1.contains("AVAILABLE") || Boody1.contains("BALANCE IN")|| Boody1.contains("Avbl Bal") ) {
            type = "AVAILABLE BALANCE";
        }
        else {
            type = "SPENT";
        }
        Log.e("type", "" + type);
        return type;
    }

    public String checkactypetrans(String body) {
        String Boody2 = body.toUpperCase();
        String type1;
        if (Boody2.contains("SALARY")) {
            type1 = "SALARY ACCOUNT";
        }
        else {
            type1 = "NORMAL ACCOUNT";
        }
        Log.e("type", "" + type1);
        return type1;
    }


    @Override
    protected void onStop() {
        super.onStop();

        if (pd!=null && pd.isShowing())
            pd.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();


        if (pd!=null && pd.isShowing())
            pd.dismiss();
    }


    private class GetSms extends AsyncTask<String, Void, String> {

        Handler mHandler = new Handler();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pd = new ProgressDialog(SplashScreen.this);

            pd.setTitle("Creating Profile....");
            pd.getWindow().setGravity(Gravity.BOTTOM);
            Uri myMessage = Uri.parse("content://sms");
            mHandler = new Handler();
            ContentResolver cr = SplashScreen.this.getContentResolver();
            Cursor c11 = cr.query(myMessage, new String[]{"_id", "address", "date",
                    "body"}, null, null, null);
            if (c11 != null) {
                cnts = c11.getCount();
            }

            Log.i("cnts", "" + cnts);
            // pd.setProgressStyle(cnts);
            final int totalProgressTime = cnts;
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setCancelable(false);
            pd.getWindow().setBackgroundDrawableResource(R.drawable.dialogbox1);

            Drawable customDrawable= getResources().getDrawable(R.drawable.custom_progressbar);

            // set the drawable as progress drawavle

            pd.setProgressDrawable(customDrawable);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String keyword;
            String abc = "bank";

            String bankname;

            int count = 0;

            // pd.setMax(100);
            //   pd.setMax(cnts);
            pd.setMax(100);


            cnts1 = 100;
//            sum = 588;
            sum = lists.Banklist.size();
            Log.i("sum1", "" + sum);
            Log.i("keyword", "" + cnts1);
            for (int i = 1; i < lists.Banklist.size(); i++) {
                sum1 = i/sum;
                Log.i("sum1", "" + sum1);
                sum2 = sum1*100;
                Log.i("sum1", "" + sum2);

                keyword = lists.Banklist.get(i);

                pd.setProgress((int) sum2);

                Log.i("keywordss", "" + keyword);
                ++count;
                Log.i("count", "" + count);
                Uri myMessage = Uri.parse("content://sms");
                ContentResolver cr = SplashScreen.this.getContentResolver();
                Cursor c1 = cr.query(myMessage, new String[]{"_id", "address", "date",
                        "body"}, "address = '" + keyword + "'", null, null);
                startManagingCursor(c1);

                if (sms_num.size() > 0) {
                    sms_id.clear();
                    sms_num.clear();
                    sms_Name.clear();
                    sms_body.clear();
                    sms_dt.clear();
                }
                try {
                    if (c1 != null && c1.moveToFirst()) {
                        do {

                            if (c1.getString(c1.getColumnIndexOrThrow("address")) == null) {
                                c1.moveToNext();
                                continue;
                            }
                            String Number = c1.getString(
                                    c1.getColumnIndexOrThrow("address"));

                            String _id = c1.getString(c1.getColumnIndexOrThrow("_id"));

                            String dat = c1.getString(c1.getColumnIndexOrThrow("date"));

                            String Body = c1.getString(c1.getColumnIndexOrThrow("body"));

                            Log.i("dat", "" + dat);
                            Log.i("number", "" + Number);
                            Log.i("id", "" + _id);
                            //***************date functions******************************//
                            Long timestamp = Long.parseLong(dat);
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

                            SimpleDateFormat month_year2 = new SimpleDateFormat("HH:mm");
                            String month_name3 = month_year2.format(calendar.getTime());
                            Log.i("month_name3", "" + month_name3);

                            sms_id.add(_id);
                            sms_num.add(Number);
                            sms_body.add(Body);

                            //****************bank name function********************************//
                            if (Number.toUpperCase().endsWith("SBI") ||Number.toUpperCase().endsWith("SBICRD") || (Number.toUpperCase().endsWith("SBGMBS")) || (Number.toUpperCase().endsWith("SCISMS")) || (Number.toUpperCase().endsWith("SBIINB"))) {
                                bankname = "State Bank Of India";
                            } else if (Number.toUpperCase().endsWith("PNBSMS")) {
                                bankname = "Punjab National Bank";
                            } else if (Number.toUpperCase().endsWith("YESBNK")) {
                                bankname = "Yes Bank";
                            } else if (Number.toUpperCase().endsWith("INDUSB")) {
                                bankname = "Indusind Bank";
                            } else if (Number.toUpperCase().endsWith("ICICIB")) {
                                bankname = "ICICI Bank";
                            } else if (Number.toUpperCase().endsWith("CITIBK")) {
                                bankname = "Citi Bank";
                            } else if (Number.toUpperCase().endsWith("OBCBNK")) {
                                bankname = "OBC Bank";
                            } else if (Number.toUpperCase().endsWith("HDFCBK")) {
                                bankname = "HDFC Bank";
                            } else if (Number.toUpperCase().endsWith("CANBNK")) {
                                bankname = "Canara Bank";
                            } else if (Number.toUpperCase().endsWith("KOTAKB")) {
                                bankname = "Kotak Bank";
                            } else if (Number.toUpperCase().endsWith("UNIONB")) {
                                bankname = "Union Bank";
                            } else if (Number.toUpperCase().endsWith("AXISBK")) {
                                bankname = "Axis Bank";
                            } else {
                                bankname = "Other Bank";
                            }


                            //****************type of transaction function*************************************//
                            String bankname1 = checktypetrans(Body);
                            Log.e("bankname1", "" + bankname1);

                            String accuntypes = checkactypetrans(Body);
                            Log.e("bankname1", "" + bankname1);

                            accunt_number = checkaccountnmberac(Body);

                            Log.e("accunt_number", "" + accunt_number);

                            SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy");
                            String month_name = month_date.format(calendar.getTime());
                            Log.i("month", "" + month_name);

                            if (Body.contains("Rs")) {
                                //splitText = cc.split("Rs");
                                dbmoney = String.valueOf(getdebitedAmount(Body));
                            } else if (Body.contains("amount of")) {
                                dbmoney = String.valueOf(getdebitedAmountss(Body));
                            } else if (Body.contains("INR")) {
                                dbmoney = String.valueOf(getdebitedAmounts(Body));
                            } else {
                                dbmoney = "0.0";
                            }

                            Log.e("Bodydd", "" + _id);
                            Log.e("Bodydd", "" + Body);
                            Log.e("Bodydd", "" + smsDate);
                            Log.e("Bodydd", "" + Number);
                            Log.e("Bodydd", "" + bankname);
                            Log.e("Bodydd", "" + bankname1);
                            Log.e("Bodydd", "" + dbmoney);
                            Log.e("Bodydd", "" + accuntypes);
                            Log.e("Bodydd", "" + accunt_number);


                            showdata();
                            b = checkduplicacy(_id);
                            Log.e("wdfdfb", "" + b);


                            if (!b) {
                                float numbercmp = Float.parseFloat(dbmoney);
                                Log.e("numbercmp", "" + numbercmp);
                                if (numbercmp > 0.0 && !Body.contains("due") && (!Body.contains("Loan Account"))) {
                                    can.put("msgid", _id);
                                    can.put("body", Body);
                                    can.put("transdate", smsDate);
                                    can.put("category", Number);
                                    can.put("nameofbank", bankname);
                                    can.put("type", bankname1);
                                    can.put("changdate", month_name);
                                    can.put("mnthdate", month_name1);
                                    can.put("yrdata", month_name2);
                                    can.put("msgtime", month_name3);
                                    can.put("amount", dbmoney);
                                    can.put("accunttype", accuntypes);
                                    can.put("accuntnumber", accunt_number);

                                    Log.e("dbmoneynnn", "" + dbmoney);
                                    //   dbmoney = String.valueOf(getdebitedAmount(Body));
                                    sql.insert("trans_msg", null, can);

                                    Log.e("can", "" + can);

                                    Log.i("bankname", "" + bankname);
                                } else if ((!Body.contains("due")) && (Body.contains("Loan Account"))) {
                                    can1.put("messageid", _id);
                                    can1.put("bodynoti", Body);
                                    can1.put("datenotify_emi", smsDate);
                                    can1.put("monthdata_emi", dbmoney);
                                    can1.put("changesdate", month_name);
                                    can1.put("monthsdate", month_name1);
                                    can1.put("yearsdata", month_name2);
                                    sql1.insert("notify_emi", null, can1);

                                    Log.e("can", "" + can1);
                                }
                            }

                        } while (c1.moveToNext());

                    }
//                    c1.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            pd.dismiss();
            Intent in = new Intent(SplashScreen.this, ProfileCreated.class);
            startActivity(in);
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

            Log.i("address", s);

        }
    }


    private void showdata() {

        cursor = sql.rawQuery("SELECT * FROM trans_msg", null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    String aa = cursor.getString(cursor.getColumnIndex("msgid"));
                    String bb = cursor.getString(cursor.getColumnIndex("nameofbank"));
                    String cc = cursor.getString(cursor.getColumnIndex("body"));
                    String dd = cursor.getString(cursor.getColumnIndex("transdate"));
                    String ee= cursor.getString(cursor.getColumnIndex("category"));

                    Log.i("transactiomn",""+aa );
                    Log.i("transactiomn",""+bb );
                    Log.i("transactiomn",""+cc );
                    Log.i("transactiomn",""+dd );
                    Log.i("transactiomn",""+ee );


                }while (cursor.moveToNext());
            }
        }

    }

    private Boolean checkduplicacy(String idd) {
        Log.i("idd", idd);
        cursor = sql.rawQuery("SELECT * FROM trans_msg where msgid = '"+idd+"'", null);
        return (cursor != null) && (cursor.getCount() > 0);
    }
}



