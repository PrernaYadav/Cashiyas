package reminder;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;

import android.util.Log;

import com.bounside.mj.cashiya.R;

import shared_pref.UserInformation;


import java.text.SimpleDateFormat;
import java.util.Calendar;

import sqlitedatabase.Bankmessagedb;
import sqlitedatabase.Datab_notify;

public class AlarmReceiver extends BroadcastReceiver {
    SQLiteDatabase sql;
    Bankmessagedb ed;
    Datab_notify dn;
    ContentValues can;
    String currentTime, currentDate, notifytime;
    Cursor mCursor;
    SQLiteDatabase db;
    String dir, dir1, dir2, dir3, dir4, dir5, dir6, dir7, dir8;
    String selectQuery, todaysDate, totime, month_name1;
    String amount;

    public Calendar calendar;

    @Override
    public void onReceive(final Context context, Intent intent) {

        calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        currentDate = sdf.format(calendar.getTime());
        Log.i("DATE", "" + currentDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        currentTime = sdf1.format(calendar.getTime());
        Log.i("TIME", "" + currentTime);

        dn = new Datab_notify(context);
        sql = dn.getReadableDatabase();
        can = new ContentValues();
        ed = new Bankmessagedb(context);

        if (displayNotificationAtGivenTime()) {
            getAllRecords();

            Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Intent intent1 = new Intent();
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent1, 0);
            Ringtone ringtone = RingtoneManager.getRingtone(context, soundUri);
            ringtone.play();

            Notification mNotification = new Notification.Builder(context)
                    .setContentTitle("Cashiya Daily Summary")
                    .setContentText("" + amount)
                    .setSmallIcon(R.mipmap.rupe)
                    .setContentIntent(pIntent)
                    .setSound(soundUri)
                    .build();

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(0, mNotification);

        }
    }

    private void showdata() {
        mCursor = sql.rawQuery("SELECT * FROM notify", null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {
                    dir = mCursor.getString(mCursor.getColumnIndex("datenotify"));
                    dir1 = mCursor.getString(mCursor.getColumnIndex("timenotify"));
                    dir2 = mCursor.getString(mCursor.getColumnIndex("message"));
//                    dir3 = mCursor.getString(mCursor.getColumnIndex("bodynoti"));
//                    dir4 = mCursor.getString(mCursor.getColumnIndex("datenotify"));
//                    dir5 = mCursor.getString(mCursor.getColumnIndex("monthdata"));
//                    dir6 = mCursor.getString(mCursor.getColumnIndex("changesdate"));
//                    dir7 = mCursor.getString(mCursor.getColumnIndex("monthsdate"));
//                    dir8 = mCursor.getString(mCursor.getColumnIndex("yearsdata"));
                    Log.i("datenotif_timenotif_msg", "" + dir + "  " + dir1 + "  " + dir2 + "" + dir3 + "" + dir4 + "  " + dir5 + "  " + dir6 + "" + dir7 + "" + dir8);

                } while (mCursor.moveToNext());
            }
        }
    }


    private void showdata_emi() {
        mCursor = sql.rawQuery("SELECT * FROM notify_emi", null);
        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                do {

                    dir3 = mCursor.getString(mCursor.getColumnIndex("bodynoti"));
                    dir4 = mCursor.getString(mCursor.getColumnIndex("datenotify_emi"));
                    dir5 = mCursor.getString(mCursor.getColumnIndex("monthdata_emi"));
                    dir6 = mCursor.getString(mCursor.getColumnIndex("changesdate"));
                    dir7 = mCursor.getString(mCursor.getColumnIndex("monthsdate"));
                    dir8 = mCursor.getString(mCursor.getColumnIndex("yearsdata"));
                    Log.i("bodynoti", "datenotify_emi" + "monthdata_emi" + dir4 + " dfgdfg " + dir5 + "  dfgdfgd" + dir6 + "dfghdfg" + dir7 + "dfgdfg" + dir8);
                }
                while (mCursor.moveToNext());
            }
        }
    }

    public boolean displayNotificationAtGivenTime() {

        Log.i("currentTime", "" + currentTime);
        Log.i("currentDate", "" + currentDate);

        SimpleDateFormat sdf1 = new SimpleDateFormat("22:00");      //10pm notification
        notifytime = sdf1.format(calendar.getTime());
        Log.i("notifytime", "" + notifytime);

        return currentTime.equals(notifytime);
    }

    private String getAllRecords() {

        calendar = Calendar.getInstance();

        SimpleDateFormat month_year = new SimpleDateFormat("MM");
        month_name1 = month_year.format(calendar.getTime());
        Log.i("month", "" + month_name1);
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
        todaysDate = sdf.format(calendar.getTime());
        Log.i("todaysdate", "" + todaysDate);
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        totime = sdf1.format(calendar.getTime());


        selectQuery = "SELECT amount FROM trans_msg WHERE changdate = '" + todaysDate + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        db = ed.getWritableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        UserInformation.totaltoday = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                UserInformation.totaltoday = UserInformation.totaltoday + temp;
            }
            while (c.moveToNext());
        }
//        c.close();

        if (UserInformation.totaltoday > 0) {

            amount = String.valueOf(UserInformation.totaltoday);

        } else {
            amount = "No Spends";
        }

        Log.i("array", "  totalspends " + UserInformation.totaltoday);

        can.put("datenotify", todaysDate);
        can.put("timenotify", "" + totime);
        can.put("monthdata", "" + month_name1);
        can.put("message", UserInformation.totaltoday);
        sql.insert("notify", null, can);
        Log.i("tdtfgh", "" + can);

        return amount;
    }
}