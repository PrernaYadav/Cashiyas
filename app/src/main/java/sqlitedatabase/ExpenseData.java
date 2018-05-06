package sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import adapter.Information;

public class ExpenseData extends SQLiteOpenHelper {
    public Calendar calendar;
    String year;

    public ExpenseData(Context context) {

        super(context, "Expense3", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //table for inserting values from add money
        db.execSQL("CREATE TABLE IF NOT EXISTS expense" +
                "(ID INTEGER PRIMARY KEY, " +
                "item VARCHAR(200), amount VARCHAR(100), " +
                "expensedate DATETIME, " +
                "category VARCHAR(200)," +
                "month INTEGER )");
        //table for inserting values from add category if any transactional message is categorised

        db.execSQL("CREATE TABLE IF NOT EXISTS income" +
                "(ID INTEGER PRIMARY KEY, " +
                "amount VARCHAR(200), " +
                "incomedate DATETIME, " +
                "category VARCHAR(250)," +
                "monthinc INTEGER," +
                "yrincome INTEGER)");

        db.execSQL("CREATE TABLE IF NOT EXISTS billspayment" +
                "(ID INTEGER PRIMARY KEY, " +
                "amount VARCHAR(200), " +
                "billdate DATETIME, " +
                "billsdscr VARCHAR(250)," +
                "billscat VARCHAR(200)," +
                "monthbill INTEGER," +
                "yrbill INTEGER," +
                "vendorname VARCHAR(250)," +
                "msgids VARCHAR(25))");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }


    public float getIncome() {
        String query = "SELECT amount FROM income";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        int temp = 0;

        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp1 = Integer.parseInt(amnt);
                temp += temp1;
            }
            while (c.moveToNext());
        }
//        c.close();
        return temp;
    }


    public float getincomejan(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =1;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomejan = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomejan = UserInformation.incomejan + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomejan " +UserInformation.incomejan);
        return UserInformation.incomejan;

    }
    public float getincomefeb(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =2;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incmoefeb = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incmoefeb = UserInformation.incmoefeb + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incmoefeb " +UserInformation.incmoefeb);
        return UserInformation.incmoefeb;


    }
    public float getincomemarch(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =3;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomemarch = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomemarch = UserInformation.incomemarch + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomemarch " +UserInformation.incomemarch);
        return UserInformation.incomemarch;


    }
    public float getincomeapr(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =4;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomeapr = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomeapr = UserInformation.incomeapr + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomeapr " +UserInformation.incomeapr);
        return UserInformation.incomeapr;


    }
    public float getincomemay(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =5;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomemay = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomemay = UserInformation.incomemay + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomemay " +UserInformation.incomemay);
        return UserInformation.incomemay;


    }
    public float getincomejune(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =6;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomejune = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomejune = UserInformation.incomejune + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomejune " +UserInformation.incomejune);
        return UserInformation.incomejune;


    }
    public float getincomejuly(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =7;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomejuly = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomejuly = UserInformation.incomejuly + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomejuly " +UserInformation.incomejuly);
        return UserInformation.incomejuly;


    }
    public float getincomeaug(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =8;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomeaug = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomeaug = UserInformation.incomeaug + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomeaug " +UserInformation.incomeaug);
        return UserInformation.incomeaug;


    }
    public float getincomesep(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =9;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-0"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomesep = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomesep = UserInformation.incomesep + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array","  incomesep " +UserInformation.incomesep);
        return UserInformation.incomesep;


    }
    public float getincomeoct(){
        calendar=Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =10;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomeoct = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomeoct = UserInformation.incomeoct + temp;

            }
            while (c.moveToNext());
        }
//        c.close();
        Log.i("array","  incomeoct " +UserInformation.incomeoct);
        return UserInformation.incomeoct;


    }
    public float getincomenov(){
        calendar=Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =11;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomenov = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomenov = UserInformation.incomenov + temp;

            }
            while (c.moveToNext());
        }
//        c.close();
        Log.i("array","  incomenov " +UserInformation.incomenov);
        return UserInformation.incomenov;
    }

    public float getincomedec(){
        calendar=Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year",""+year);
        int i =12;
        String query ="SELECT amount FROM income WHERE incomedate LIKE '%-"+i+"-"+year+"' ORDER BY incomedate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.incomedec = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                int temp = Integer.parseInt(amnt);
                UserInformation.incomedec = UserInformation.incomedec + temp;

            }
            while (c.moveToNext());
        }
//        c.close();
        Log.i("array","  incomedec " +UserInformation.incomedec);
        return UserInformation.incomedec;


    }
}
