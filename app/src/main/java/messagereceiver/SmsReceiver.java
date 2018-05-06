package messagereceiver;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.bounside.mj.cashiya.SplashScreen;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 7/30/2016.
 */
public class SmsReceiver extends BroadcastReceiver {
    String Number;
    String Body, upercase_body;
    int _id;
    Long dat;
    String bankname;
    String dbmoney;


    @Override
    public void onReceive(Context context, Intent intent) {
        // Parse the SMS.
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        String str = "";
        SplashScreen ss = new SplashScreen();
        SQLiteDatabase sql;
        ContentValues can;
        String accunt_number;
        Bankmessagedb bbd = new Bankmessagedb(context);

        if (bundle != null) {
            sql = bbd.getWritableDatabase();
            can = new ContentValues();
            // Retrieve the SMS.
            Object[] pdus = (Object[]) bundle.get("pdus");

            msgs = new SmsMessage[pdus.length];
            Log.i("msgs11", "" + Arrays.toString(pdus));
            Log.i("msgs", "" + Arrays.toString(pdus));
            for (int i = 0; i < msgs.length; i++) {
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                Log.i("msssggggsss", "" + msgs[i].toString());
                Number = msgs[i].getOriginatingAddress();
                Log.i("Number", "" + Number);
                Body = msgs[i].getMessageBody();
                Log.i("Body", "" + Body);
                upercase_body = Body.toUpperCase();
                dat = msgs[i].getTimestampMillis();
                Log.i("dat", "" + dat.toString());
                _id = msgs[i].getOriginatingAddress().indexOf(Body);
                Log.i("_id", "" + _id);

                Long timestamp = Long.parseLong(dat.toString());
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(timestamp);
                Log.i("timestamp", "" + timestamp);
                Date finaldate = calendar.getTime();
                Log.i("finaldate", "" + finaldate);
                String smsDate = finaldate.toString();
                Log.i("smsDate", "" + smsDate);
                calendar.setTime(finaldate);

                SimpleDateFormat month_format = new SimpleDateFormat("MM");
                String currentMonth = month_format.format(calendar.getTime());
                Log.i("month", "" + currentMonth);

                SimpleDateFormat year_format = new SimpleDateFormat("yyyy");
                String currentYear = year_format.format(calendar.getTime());
                Log.i("month", "" + currentYear);

                SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
                String currentTime = time_format.format(calendar.getTime());
                Log.i("currentTime", "" + currentTime);


                if (Number.toUpperCase().endsWith("SBI") || Number.toUpperCase().endsWith("SBICRD") || (Number.toUpperCase().endsWith("SBGMBS")) || (Number.toUpperCase().endsWith("SCISMS"))) {
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
                } else if (Number.toUpperCase().endsWith("BOIIND")) {
                    bankname = "Bank of India";
                } else if (Number.toUpperCase().endsWith("AXISBK")) {
                    bankname = "Axis Bank";
                } else {
                    bankname = "Other Bank";
                }

                SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy");
                String month_name = month_date.format(calendar.getTime());
                Log.i("month", "" + month_name);


                if (Number.endsWith("PNBSMS") || (Number.endsWith("YESBNK")) || (Number.endsWith("INDUSB")) || (Number.endsWith("ICICIB")) ||
                        (Number.endsWith("CITIBK")) || (Number.endsWith("OBCBNK")) || (Number.endsWith("ATMSBI")) ||
                        (Number.endsWith("HDFCBK")) || (Number.endsWith("KOTAKB")) || (Number.endsWith("SCISMS")) ||
                        (Number.endsWith("SBGMBS")) || (Number.endsWith("CBSSBI")) || (Number.endsWith("CANBNK"))) {
                    if (Body.contains("Rs")) {
                        dbmoney = String.valueOf(ss.getdebitedAmount(Body));
                    } else if (Body.contains("INR")) {
                        dbmoney = String.valueOf(ss.getdebitedAmounts(Body));
                    } else {
                        dbmoney = "0.0";
                    }
                    String typeofTxn = ss.checktypetrans(Body);
                    Log.e("typeofTxn", "" + typeofTxn);

                    String accuntypes = ss.checkactypetrans(Body);
                    if (Body.contains("xx")) {
                        accunt_number = ss.checkaccountnmberac(Body);
                    } else {
                        accunt_number = "xxxxx";
                    }

                    float numbercmp = Float.parseFloat(dbmoney);
                    Log.e("numbercmp", "" + numbercmp);
                    if ((numbercmp > 0.0)) {

                        can.put("body", Body);
                        can.put("transdate", smsDate);
                        can.put("category", Number);
                        can.put("nameofbank", bankname);
                        can.put("type", typeofTxn);
                        can.put("changdate", month_name);
                        can.put("mnthdate", currentMonth);
                        can.put("yrdata", currentYear);
                        can.put("msgtime", currentTime);
                        can.put("amount", dbmoney);
                        can.put("accunttype", accuntypes);
                        can.put("accuntnumber", accunt_number);
                        sql.insert("trans_msg", null, can);

                        Log.e("can", "" + can);

                        Log.i("bankname", "" + bankname);
                    }
                } else {
//                   Toast.makeText(context,"No DAta Inserted",Toast.LENGTH_LONG).show();
                }

                str += msgs[i].getOriginatingAddress();
                str += " ...........";
                str += msgs[i].getMessageBody();
                Log.i("msssgggg", "" + str);
                //}
            }
            // Display the SMS as Toast.
            Toast.makeText(context, str, Toast.LENGTH_LONG).show();

        }
    }
}