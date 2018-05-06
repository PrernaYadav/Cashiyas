package com.bounside.mj.cashiya;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import bank.AccountSummary;
import bank.LikeShare;
import bank.ProfileCreated1;
import bank.YourQueries;
import dynamicviewpager.MonthlySavings;
import leads.Leads;
import reminder.MainNotification;
import shared_pref.UserInformation;
import sqlitedatabase.Bankmessagedb;
import sqlitedatabase.Datab_notify;
import sqlitedatabase.LoginData;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    final int MY_PERMISSIONS_REQUEST_RECIEVE_SMS = 2;
    TextView hname,hemail,phone;
    ImageView iv;
    LoginData db = new LoginData(this);

    ArrayList<String> sms_id = new ArrayList<>();
    ArrayList<String> sms_num = new ArrayList<>();
    ArrayList<String> sms_Name = new ArrayList<>();
    ArrayList<String> sms_dt = new ArrayList<>();
    ArrayList<String> sms_body = new ArrayList<>();
    Banklists lists= new Banklists();
    boolean b;
    String dbmoney;
    String accunt_number;
    int cnts,cnts1;
    float sum;
    float sum1,sum2;

    List<String> numbers = new ArrayList<String>();
    String Numbervalue,Numbervalue1;

    SQLiteDatabase sql, sql1, sqlbankdb;
    ContentValues can,can1, can2;
    Cursor cursor, cursor2;
    public static Date currentDate, msgDate;

    Bankmessagedb bdb = new Bankmessagedb(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        sqlbankdb = bdb.getWritableDatabase();

        sql = db.getReadableDatabase();
        can = new ContentValues();

        FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
        tx.replace(R.id.mainframe, new Home());
        tx.commit();


        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        iv = (ImageView) hView.findViewById(R.id.imageViewdrawer);

        hemail = (TextView) hView.findViewById(R.id.emailaccount);
        hname = (TextView) hView.findViewById(R.id.nameaccount);
        phone = (TextView) hView.findViewById(R.id.phoneaccount);

        navigationView.setNavigationItemSelectedListener(this);

        Menu nav_Menu = navigationView.getMenu();
        displayData();


        if (UserInformation.UserFirstName != null)       {
            if(UserInformation.UserFirstName.equals("Service Provider")) {
            nav_Menu.findItem(R.id.leadsmains).setVisible(true);
            }
        }

        checkReadContactsPerm();
        checkRecieveSmsPerm();

        Calendar currentCalendar = Calendar.getInstance();
        currentDate = currentCalendar.getTime();

        Date lastMsgDate = getLastMsgDate();

        if (lastMsgDate != null)
        {
            long diff = getDateDifference(lastMsgDate, currentDate);

            if (diff > 1){
                new GetSms().execute();
            }
        }

        hname.setText(UserInformation.UserFirstName);
        hemail.setText(UserInformation.Email);
        phone.setText(UserInformation.Phone);
        Log.i("UserInformation.Image",""+UserInformation.Image);
        if(UserInformation.Image == null) {
            Picasso.with(MainActivity.this).load(R.drawable.userprofile)

                    .into(iv);
        }
        else
        {
            Picasso.with(MainActivity.this)
                    .load(UserInformation.Image)
                    .into(iv);
        }
    }

    private void displayData() {

        Cursor mCursor = sql.rawQuery("SELECT * FROM DetailsUser", null);

        if (mCursor.moveToFirst()) {
            do {
                UserInformation.UserFirstName = mCursor.getString(mCursor.getColumnIndex("FName"));
                UserInformation.UserLastName = mCursor.getString(mCursor.getColumnIndex("LName"));
                UserInformation.Email = mCursor.getString(mCursor.getColumnIndex("Email"));
                UserInformation.Phone = mCursor.getString(mCursor.getColumnIndex("Contactno"));
                UserInformation.Image = mCursor.getString(mCursor.getColumnIndex("image"));

                Log.i("UserFirstname",""+UserInformation.UserFirstName);
                Log.i("UserLastname",""+UserInformation.UserLastName);
                Log.i("Email",""+UserInformation.Email);
                Log.i("Image",""+UserInformation.Image);

            } while (mCursor.moveToNext());
        }

//        mCursor.close();
    }

    private Date getLastMsgDate() {
        Date date = null;

//        Cursor mCursor = sqlbankdb.rawQuery("SELECT * FROM trans_msg ORDER BY transdate desc", null);


        try {Cursor mCursor = sqlbankdb.rawQuery("SELECT * FROM trans_msg ORDER BY transdate desc", null);

            if (mCursor.moveToFirst()) {

                    String dateOfLastMsg = mCursor.getString(mCursor.getColumnIndex("transdate"));

                    Log.i("msgDate",""+  dateOfLastMsg);

                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy'T'HH:mm:ss'Z'");
                    date = format.parse(dateOfLastMsg);
                }

            else {
                Log.i("msgDate", "" + "No Date found");
            }
            } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
        //        mCursor.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getLastMsgDate();
    }

    public void checkRecieveSmsPerm() {
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.RECEIVE_SMS);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,  Manifest.permission.RECEIVE_SMS)!= PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)) {



                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

//                Toast.makeText(this, "Please turn on the notification for reading the otp message", Toast.LENGTH_SHORT).show();

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECEIVE_SMS}, MY_PERMISSIONS_REQUEST_RECIEVE_SMS
                );

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    public void checkReadContactsPerm() {
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_SMS);


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.READ_CONTACTS)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.READ_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_RECIEVE_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
            // builder.setCancelable(false);
            builder.setTitle("");
            builder.setMessage("Do you want to exit the application?");
            builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    /*finish();
                    System.exit(0);*/

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finishAffinity();
                    startActivity(intent);

                }
            });
            builder.setNegativeButton("No",new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert=builder.create();
            alert.show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(MainActivity.this, MainNotification.class));
            return true;
        }
//        else super.onBackPressed();
        return true;
        //return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.income) {
            startActivity(new Intent(MainActivity.this, ExpenseList.class));
        }
        if (id == R.id.monthly) {
            startActivity(new Intent(MainActivity.this, MonthlySavings.class));
        }
        else if (id == R.id.calcsave) {
            startActivity(new Intent(MainActivity.this, Allcalculator.class));
        }
        else if(id==R.id.leadsmains)
        {
            startActivity(new Intent(MainActivity.this, Leads.class));
        }
        else if(id==R.id.privacy)
        {
            startActivity(new Intent(MainActivity.this, PrivacyPolicy.class));
        }
        else if(id==R.id.terms)
        {
            startActivity(new Intent(MainActivity.this, TermCondition.class));
        }
        else if (id == R.id.importmsg)
        {
            startActivity(new Intent(MainActivity.this, SplashScreen1.class));
        }
        else if (id == R.id.summaryaccnt)
        {                               //account summary
            startActivity(new Intent(MainActivity.this, AccountSummary.class));
        }
        else if (id == R.id.query_list)
        {
            startActivity(new Intent(MainActivity.this, YourQueries.class));
        }
        else if(id == R.id.ratecas_nav){
            startActivity(new Intent(MainActivity.this, LikeShare.class));
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public String checktypetrans(String Body) {
        String Boody1 = Body.toUpperCase();
        String type;
        if (Boody1.contains("CREDITED") || Boody1.contains("DEPOSITED")) {
            type = "CREDITED.";
        } else if (Boody1.contains("DEBITED") /*||Boody1.contains("DEBIT") */|| Boody1.contains("WITHDRAWN")) {
            type = "DEBITED.";
        } else if (Boody1.contains("PURCHASE")) {
            type = "PURCHASE.";
        }
        else if (Boody1.contains("AVAILABLE") || Boody1.contains("BALANCE IN")|| Boody1.contains("Avbl Bal")) {
            type = "AVAILABLE BALANCE";
        }
        else {
            type = "SPENT";
        }
        Log.e("type", "" + type);
        return type;

    }

    public String checkaccountnmberac(String body) {
        Log.e("Body22", "" + body);
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

    public String checkactypetrans(String body) {
        String Body2 = body.toUpperCase();
        String type1;
        if (Body2.contains("SALARY")) {
            type1 = "SALARY ACCOUNT";
        }
        else {
            type1 = "NORMAL ACCOUNT";
        }
        Log.e("type", "" + type1);
        return type1;
    }

    private class GetSms extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Uri myMessage = Uri.parse("content://sms");

            ContentResolver cr = MainActivity.this.getContentResolver();

            Cursor c11 = cr.query(myMessage, new String[]{"_id", "address", "date",
                    "body"}, null, null, null);
            if (c11 != null) {
                cnts = c11.getCount();
            }

            Log.i("cnts", "" + cnts);

        }

        @Override
        protected String doInBackground(String... params) {
            String keyword;
            String abc = "bank";

//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

            String bankname;

            cnts1 = 100;
            sum = 536;          //TOTAL NO. OF BANKS

            for (int i = 1; i < lists.Banklist.size(); i++) {
                sum1 = i/sum;
                Log.i("sum1", "" + sum1);
                sum2 = sum1*100;
                Log.i("sum1", "" + sum2);

                keyword = lists.Banklist.get(i);

                Uri myMessage = Uri.parse("content://sms");
                ContentResolver cr = MainActivity.this.getContentResolver();
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
                    if (c1 != null ? c1.moveToFirst() : false) {
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


                            SimpleDateFormat month_format = new SimpleDateFormat("MM");
                            String currentMonth = month_format.format(calendar.getTime());
                            Log.i("month", "" + currentMonth);

                            SimpleDateFormat year_format = new SimpleDateFormat("yyyy");
                            String currentYear = year_format.format(calendar.getTime());
                            Log.i("year", "" + currentYear);

                            SimpleDateFormat time_format = new SimpleDateFormat("HH:mm");
                            String currentTime = time_format.format(calendar.getTime());
                            Log.i("time", "" + currentTime);

                            SimpleDateFormat date_format = new SimpleDateFormat("dd/MM/yyyy");
                            String messagedate = date_format.format(calendar.getTime());
                            Log.i("date", "" + messagedate);

                            msgDate = calendar.getTime();

                            sms_id.add(_id);
                            sms_num.add(Number);
                            sms_body.add(Body);

                            //****************************bank name function********************************//
                            if (Number.toUpperCase().endsWith("SBI") || Number.toUpperCase().endsWith("SBICRD") || (Number.toUpperCase().endsWith("SBGMBS")) || (Number.toUpperCase().endsWith("SCISMS"))||(Number.toUpperCase().endsWith("SBIINB"))) {
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

                            //****************type of transaction function***********************************//
                            String txnType = checktypetrans(Body);
                            Log.e("txnType", "" + txnType);

                            String accuntypes = checkactypetrans(Body);
                            Log.e("account type", "" + accuntypes);

                            accunt_number = checkaccountnmberac(Body);

                            Log.e("accunt_number", "" + accunt_number);

                            SimpleDateFormat month_date = new SimpleDateFormat("dd-MMM-yyyy");
                            String month_name = month_date.format(calendar.getTime());
                            Log.i("month", "" + month_name);

                            if (Body.contains("Rs")) {
                                //splitText = cc.split("Rs");
                                dbmoney = String.valueOf(getdebitedAmount(Body));
                            } else if(Body.contains("amount of")){
                                dbmoney = String.valueOf(getdebitedAmountss(Body));
                            }
                            else if (Body.contains("INR")) {
                                dbmoney = String.valueOf(getdebitedAmounts(Body));
                            } else {
                                dbmoney = "0.0";
                            }

                            Log.e("Bodydd", "" + _id);
                            Log.e("Bodydd", "" + Body);
                            Log.e("smsDate", "" + smsDate);
                            Log.e("Bodydd", "" + Number);
                            Log.e("Bodydd", "" + bankname);
                            Log.e("Bodydd", "" + txnType);
                            Log.e("Bodydd", "" + dbmoney);
                            Log.e("Bodydd", "" + accuntypes);
                            Log.e("Bodydd", "" + accunt_number);

                            b = checkduplicacy(_id);
                            Log.e("duplicacy?", "" + b);

                            if (!b) {
                                float numbercmp = Float.parseFloat(dbmoney);
                                Log.e("numbercmp", "" + numbercmp);
                                if (numbercmp > 0.0 && !Body.contains("due") && (!Body.contains("Loan Account"))) {
                                    can.put("msgid", _id);
                                    can.put("body", Body);
                                    can.put("transdate", smsDate);
                                    can.put("category", Number);
                                    can.put("nameofbank", bankname);
                                    can.put("type", txnType);
                                    can.put("changdate", month_name);
                                    can.put("currentMonth", currentMonth);
                                    can.put("currentYear", currentYear);
                                    can.put("currentTime", currentTime);
                                    can.put("amount", dbmoney);
                                    can.put("accunttype", accuntypes);
                                    can.put("accuntnumber", accunt_number);

                                    Log.e("dbmoneynnn", "" + dbmoney);

                                    sql.insert("trans_msg", null, can);

                                    Log.e("can", "" + can);

                                    Log.i("bankname", "" + bankname);
                                }
                                else if((!Body.contains("due")) && (Body.contains("Loan Account")))
                                {
                                    can1.put("messageid", _id);
                                    can1.put("bodynoti", Body);
                                    can1.put("datenotify_emi", smsDate);
                                    can1.put("monthdata_emi", dbmoney);
                                    can1.put("changesdate", month_name);
                                    can1.put("monthsdate", currentMonth);
                                    can1.put("yearsdata", currentYear);
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

            Log.i("address", s);

        }
    }

    private Boolean checkduplicacy(String idd) {
        cursor = sql.rawQuery("SELECT * FROM trans_msg where msgid = '"+idd+"'", null);
        return (cursor != null) && (cursor.getCount() > 0);
    }


    public long getDateDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        Log.i("startDate", "" + startDate);
        Log.i("endDate", "" + endDate);
        Log.i("different", "" + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;


        System.out.printf(
                "%d days, %d hours, %d minutes, %d seconds%n",
                elapsedDays,
                elapsedHours, elapsedMinutes, elapsedSeconds);

        return elapsedHours;
    }

}
