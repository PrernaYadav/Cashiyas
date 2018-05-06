package loan;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import shared_pref.UserInformation;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shared_pref.SharedPref;
import sqlitedatabase.LoginData;

import static com.google.android.gms.common.api.GoogleApiClient.*;

/**
 * Created by rasika on 3/30/2016.
 */
public class Loan_transfer extends AppCompatActivity implements OnClickListener, OnConnectionFailedListener, LocationListener,
        ConnectionCallbacks {

    Calendar cal;
    String r;
    Spinner loantransfer;
    EditText exitloan, tenture, interest, grossincome, income, name, dob;
    AutoCompleteTextView city;
    TextView phone;
    Button submit;
    RadioGroup gender;
    Spinner banktrna;
    String getmonth;
    Jsonparse jsonParser = new Jsonparse(this);
    ProgressDialog dialog;
    String success;
    LoginData db = new LoginData(this);
    SQLiteDatabase sql;
    ContentValues can;
    double f3;
    int getyr, getmn, getdatess;
    String month_name2, month_name1, month_name;
    EditText adharcard2_loantransfer, pancard2_loantransfer, residence;
    Location mLastLocation;
    Pattern pattern, pattern1;
    Matcher matcher, matcher1;
    String myFormatss = "dd-MM-yyyy"; //In which you need put here
    SimpleDateFormat sdfs = new SimpleDateFormat(myFormatss, Locale.US);
    Date convertedDate = new Date();
    Date convertedDate3 = new Date();
    long ss;
    // Google client to interact with Google API
    String s;
    Geocoder geo;
    // boolean flag to toggle periodic location updates
    double lat, lon;
    LocationRequest mLocationRequest;
    Calendar myCalendar = Calendar.getInstance();
    private List<String> list1 = new ArrayList<String>();
    private String[] arraySpinnerloan;
    private String[] bank;
    private String selecteddat1, selecteddat2;
    private GoogleApiClient mGoogleApiClient;
    private RadioButton Male, Female;

    public static int getDiffYears(Date first, Date last) {
        Log.i("ujyhfgvhi", "" + first);
        Log.i("ujyhfgvhi", "" + last);
        Calendar a = getCalendar(first);
        Log.i("ujyhfgvhi", "" + a);
        Calendar b = getCalendar(last);
        Log.i("ujyhfgvhi", "" + b);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        Log.i("ujyhfgvhi", "" + diff);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        Log.i("ujyhfgvhi", "" + diff);
        return diff;
    }

    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_transfer);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Loan Transfer Form");
        }

        sql = db.getReadableDatabase();
        can = new ContentValues();
        cal = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date dates = new Date();
        getmonth = dateFormat.format(dates);
        Log.i("getmonth", "" + getmonth);
        getyr = Calendar.getInstance().get(Calendar.YEAR);
        Log.i("getmonth", "" + getyr);
        getmn = Calendar.getInstance().get(Calendar.MONTH) + 1;
        Log.i("getmonth", "" + getmn);
        getdatess = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        Log.i("getmonth", "" + getdatess);
        //remove focus from edittext when activity is  invoked
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initializeFields();
        displayData();

        name.setText(UserInformation.UserFirstName);
        phone.setText(UserInformation.Phone);

        buildGoogleApiClient();



        this.arraySpinnerloan = new String[]{
                "Home Loan Balance Transfer", "Personal Loan Balance Transfer", "Loan Against Property Balance Transfer"
        };
        ArrayAdapter<String> adapter11 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, arraySpinnerloan);
        loantransfer.setAdapter(adapter11);
        loantransfer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                {

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submit.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
        }

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void initializeFields() {


        //*****************transfer loan data****************************//
        exitloan = (EditText) findViewById(R.id.starttrans);
        loantransfer = (Spinner) findViewById(R.id.type_transfer);
        tenture = (EditText) findViewById(R.id.tenturetrans);
        interest = (EditText) findViewById(R.id.interesttrans);
        city = (AutoCompleteTextView) findViewById(R.id.citytrans);
//        city.setInputType(EditorInfo.IME_ACTION_NEXT);

/*        ArrayAdapter<String> ada1 = new ArrayAdapter<String>(LoanUser.this,
                android.R.layout.select_dialog_singlechoice, list1);
        city.setThreshold(4);
        city.setAdapter(ada1);
        city.setTextColor(Color.BLACK);*/

        income = (EditText) findViewById(R.id.incometrans);
        grossincome = (EditText) findViewById(R.id.grossincometrans);
        name = (EditText) findViewById(R.id.nametrans);
        phone = (TextView) findViewById(R.id.phontrans);
        gender = (RadioGroup) findViewById(R.id.radiotrans);
        Male = (RadioButton) findViewById(R.id.radio_maletrans);
        Female = (RadioButton) findViewById(R.id.radio_femaletrans);
        dob = (EditText) findViewById(R.id.dobtrans);
        adharcard2_loantransfer = (EditText) findViewById(R.id.adhar_loantransfer);
        pancard2_loantransfer = (EditText) findViewById(R.id.pan_loantransfer);
        banktrna = (Spinner) findViewById(R.id.banktransp);

        submit = (Button) findViewById(R.id.submittransloan);
        this.bank = new String[]
                {"AXIS", "HDFC", "ICICI", "SBI", "Cash or Cheque", "Others"};
        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, bank);
        banktrna.setAdapter(adapter5);


        new searchexample1().execute(SharedPref.city_api);


        final DatePickerDialog.OnDateSetListener datedob = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                SimpleDateFormat month_date = new SimpleDateFormat("dd");
                month_name = month_date.format(myCalendar.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar.getTime());
                Log.i("month", "" + month_name2);

                updateLabedob();
            }

            private void updateLabedob() {

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddat1 = sdf.format(myCalendar.getTime());
                dob.setText(selecteddat1);

            }
        };

        final DatePickerDialog.OnDateSetListener dateexit = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                SimpleDateFormat month_date = new SimpleDateFormat("dd");
                month_name = month_date.format(myCalendar.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar.getTime());
                Log.i("month", "" + month_name2);

                updateLabeexit();

            }

            private void updateLabeexit() {

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddat2 = sdf.format(myCalendar.getTime());
                exitloan.setText(selecteddat2);

            }
        };

        dob.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Loan_transfer.this, datedob, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });


        exitloan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(Loan_transfer.this, dateexit, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.submittransloan:
                try {
                    convertedDate3 = sdfs.parse(getmonth);
                    Log.i("tyfs", "" + convertedDate3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    convertedDate = sdfs.parse(dob.getText().toString());
                    Log.i("tyfss", "" + convertedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ss = getDiffYears(convertedDate, convertedDate3);
                Log.i("tdds", "" + ss);
                submituser();
                // afterTextChanged();

                pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                matcher = pattern.matcher(SharedPref.pancard_loantransfer);

                pattern1 = Pattern.compile("^\\d{1,2}(\\.\\d{1,2})?$");
                matcher1 = pattern1.matcher(interest.getText().toString());
                // Check if pattern matches
//                if (matcher.matches()) {
//                    Toast.makeText(Loan_transfer.this,"correct Pan Card", Toast.LENGTH_LONG).show();
//                }
                if (!matcher.matches()) {
                    pancard2_loantransfer.setError("enter correct Pan Card No.");
                    Toast.makeText(Loan_transfer.this, "Enter correct Pan Card", Toast.LENGTH_LONG).show();
                } else if (!matcher1.matches()) {
                    interest.setError("Enter correct rate of interest i.e. eg.= 00.00");
                    Toast.makeText(Loan_transfer.this, "Enter correct rate of interest i.e. example= 00.00", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(SharedPref.nametrns)) {
                    name.setError("Field Missing");
                } else if (TextUtils.isEmpty(SharedPref.incometrans)) {
                    income.setError("Field Missing");
                } else if ((income.length() < 5) || (SharedPref.incometrans.startsWith("0"))) {
                    income.setError("Income is not valid");
                    Toast.makeText(Loan_transfer.this, " Income is not valid ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(SharedPref.citytrance)) {
                    city.setError("Field Missing");
                } else if (TextUtils.isEmpty(SharedPref.dobtrans)) {
                    dob.setError("Field Missing");
                } else if (TextUtils.isEmpty(SharedPref.interest)) {
                    interest.setError("Field Missing");
                } else if (Double.parseDouble(SharedPref.interest) > 20.00) {
                    interest.setError("Interest rate should be less than 20%");
                    Toast.makeText(Loan_transfer.this, "Interest rate should be less than 20%", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(SharedPref.tenture)) {
                    tenture.setError("Field Missing");
                } else if (TextUtils.isEmpty(SharedPref.exitloan)) {
                    exitloan.setError("Field Missing");
                } else if (TextUtils.isEmpty(SharedPref.grossinctrans)) {
                    grossincome.setError("Field Missing");
                } else if ((Integer.parseInt(SharedPref.grossinctrans)) < (Integer.parseInt(SharedPref.incometrans))) {
                    grossincome.setError("Gross Income should be Greater than Net Income");
                    Toast.makeText(Loan_transfer.this, "Gross Income should be Greater than Net Income", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(SharedPref.phonetrans)) {
                    phone.setError("Field Missing");
                } else if ((grossincome.length() < 5) || (SharedPref.grossinctrans.startsWith("0"))) {
                    grossincome.setError("Salary is not valid");
                    Toast.makeText(Loan_transfer.this, " Salary is not valid ", Toast.LENGTH_SHORT).show();
                } else if (adharcard2_loantransfer.length() < 12) {
                    adharcard2_loantransfer.setError("Enter Correct aadhar number");
                    Toast.makeText(Loan_transfer.this, " Please Enter Correct aadhar number ", Toast.LENGTH_SHORT).show();
                } else if (ss < 18) {
                    dob.setError("Age should not be less than 18");
                    Toast.makeText(Loan_transfer.this, "Check your Date of Birth",
                            Toast.LENGTH_SHORT).show();
                } else if (ss > 65) {
                    dob.setError("Age should not be greater than 65");
                    Toast.makeText(Loan_transfer.this, "Check your Date of Birth",
                            Toast.LENGTH_SHORT).show();
                } else {
                    new insertusermain().execute();
                }
                break;
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

            } while (mCursor.moveToNext());
        }

//        mCursor.close();
    }


    @Override
    public void onConnected(Bundle bundle) {

//        mPlaceArrayAdapter.setGoogleApiClient(mGoogleApiClient);
//        Log.i(LOG_TAG, "Google Places API connected.");

        displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();
//        Log.e(LOG_TAG, "Google Places API connection failed with error code: "
//                + connectionResult.getErrorCode());
//
//        Toast.makeText(this,
//                "Google Places API connection failed with error code:" +
//                        connectionResult.getErrorCode(),
//                Toast.LENGTH_LONG).show();
    }

    @Override
    public void onConnectionSuspended(int i) {
        // mPlaceArrayAdapter.setGoogleApiClient(null);
        // Log.e(LOG_TAG, "Google Places API connection suspended.");
    }


    private void submituser() {
        new GetAddress().execute();
        SharedPref.nametrns = name.getText().toString();
        Log.i("value 1", "" + SharedPref.nametrns);
        SharedPref.transf_type = loantransfer.getSelectedItem().toString();
        switch (SharedPref.transf_type) {
            case "Home Loan Balance Transfer":
                f3 = 9.35;
                Log.i("value 1", "" + f3);
                break;
            case "Personal Loan Balance Transfer":
                f3 = 11.49;
                Log.i("value 1", "" + f3);
                break;
            case "Loan Against Property Balance Transfer":
                f3 = 10.5;
                Log.i("value 1", "" + f3);
                break;
        }
        SharedPref.incometrans = income.getText().toString();
        Log.i("value 1", "" + SharedPref.incometrans);
        SharedPref.interest = interest.getText().toString();
        Log.i("value 1", "" + SharedPref.incometrans);
        SharedPref.grossinctrans = grossincome.getText().toString();
        SharedPref.citytrance = city.getText().toString();
        Log.i("value 1", "" + SharedPref.citytrance);
        SharedPref.dobtrans = dob.getText().toString();
        Log.i("value 1", "" + SharedPref.dobtrans);
        SharedPref.banktypes = banktrna.getSelectedItem().toString();
        SharedPref.Adharcard_loantransfer = adharcard2_loantransfer.getText().toString();
        SharedPref.pancard_loantransfer = pancard2_loantransfer.getText().toString();


        if (banktrna.getSelectedItem().toString().equals("AXIS")) {
            SharedPref.banktypes = "AXIS";
            Log.i("c", "............." + SharedPref.banktypes);
        }
        if (banktrna.getSelectedItem().toString().equals("HDFC")) {
            SharedPref.banktypes = "HDFC";
            Log.i("e", "............." + SharedPref.banktypes);
        }
        if (banktrna.getSelectedItem().toString().equals("ICICI")) {
            SharedPref.banktypes = "ICICI";
            Log.i("g", "............." + SharedPref.banktypes);
        }
        if (banktrna.getSelectedItem().toString().equals("SBI")) {
            SharedPref.banktypes = "SBI";
            Log.i("h", "............." + SharedPref.banktypes);
        }
        if (banktrna.getSelectedItem().toString().equals("Cash or Cheque")) {
            SharedPref.banktypes = "Cash or Cheque";
            Log.i("p", "............." + SharedPref.banktypes);
        }
        if (banktrna.getSelectedItem().toString().equals("Others")) {
            SharedPref.banktypes = "Others";
            Log.i("p", "............." + SharedPref.banktypes);
        }
        Log.i("value 1", "" + SharedPref.banktypes);

        //  f2 = Double.parseDouble(interest.getText().toString());

        Log.i("value 111", "" + SharedPref.interest);


        SharedPref.tenture = tenture.getText().toString();
        Log.i("value 1", "" + SharedPref.tenture);
        SharedPref.exitloan = exitloan.getText().toString();
        Log.i("value 1", "" + SharedPref.exitloan);

        if (Male.isChecked()) {
            SharedPref.gentrans = Male.getText().toString();
        }
        if (Female.isChecked()) {
            SharedPref.gentrans = Female.getText().toString();
        }

        Log.i("value 1", "" + SharedPref.gentrans);
        SharedPref.emailtrans = UserInformation.Email;
        Log.i("value 1", "" + SharedPref.emailtrans);
        SharedPref.phonetrans = phone.getText().toString();
        Log.i("value 1", "" + SharedPref.phonetrans);


    }


    private void displayLocation() {

        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second

        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            lat = (mLastLocation.getLatitude());
            lon = (mLastLocation.getLongitude());

        }
    }

    synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mGoogleApiClient.disconnect();
    }


    @Override
    public void onLocationChanged(Location location) {
        lat = (location.getLatitude());
        lon = (location.getLongitude());

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in1 = new Intent(Loan_transfer.this, MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
    //// gps location asynck task location

    public class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Loan_transfer.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
            // Toast.makeText(Loan_transfer.this, "finally invoked !", Toast.LENGTH_SHORT).show();

        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            // param.add(new BasicNameValuePair("outstanding", SharedPref.outstandbal));
            param.add(new BasicNameValuePair("existing_loan_start", SharedPref.exitloan));
            param.add(new BasicNameValuePair("outstanding_loan_tenure", SharedPref.tenture));
            param.add(new BasicNameValuePair("interest_rate", SharedPref.interest));
            param.add(new BasicNameValuePair("existing_loan_bank", SharedPref.banktypes));
            param.add(new BasicNameValuePair("city", SharedPref.citytrance));
            param.add(new BasicNameValuePair("monthly income", SharedPref.incometrans));
            param.add(new BasicNameValuePair("name", SharedPref.nametrns));
            param.add(new BasicNameValuePair("gender", SharedPref.gentrans));
            param.add(new BasicNameValuePair("dob", SharedPref.dobtrans));
            param.add(new BasicNameValuePair("email", SharedPref.emailtrans));
            param.add(new BasicNameValuePair("mobile", SharedPref.phonetrans));
            param.add(new BasicNameValuePair("type_transfer", SharedPref.transf_type));
            param.add(new BasicNameValuePair("gross_income", SharedPref.grossinctrans));
            param.add(new BasicNameValuePair("gps_location", SharedPref.Location_gps));
            param.add(new BasicNameValuePair("adhar_card", SharedPref.Adharcard_loantransfer));
            param.add(new BasicNameValuePair("pan_card", SharedPref.pancard_loantransfer));


            JSONObject jobj = jsonParser.makeHttpRequest(
                    SharedPref.loan_transfer, "POST", param);
            String message = "";


            try {
                message = jobj.getString("msg");
                success = jobj.getString("status");


            } catch (Exception e) {
                e.printStackTrace();
            }

            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Intent i = new Intent(Loan_transfer.this, SubmitLoan.class);
            startActivity(i);

//            try {
//                f2 = Double.parseDouble(interest.getText().toString().replace(",", ""));
//            } catch (NumberFormatException e) {
//                // EditText EtPotential does not contain a valid double
//            }
//            Log.i("fgdtyfj",""+f2);
//            if(f2<=f3)
//            {
//                Log.i("fdyfjg",""+f3);
//                Toast.makeText(Loan_transfer.this, "Congratulation!..You are already best Rate Interest....", Toast.LENGTH_SHORT).show();
//                SharedPref.interest= interest.getText().toString();
//
//            }
//            else
//            {
//                Toast.makeText(Loan_transfer.this, "We have better rate of interest. i.e. "+f3, Toast.LENGTH_SHORT).show();
//                SharedPref.interest= interest.getText().toString();
//            }


        }
    }


    private class searchexample1 extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... arg0) {
            try {

                HttpClient hc = new DefaultHttpClient();
                HttpPost hp = new HttpPost(arg0[0]);

                HttpResponse hr = hc.execute(hp);

                int status = hr.getStatusLine().getStatusCode();
                if (status == 200) {
                    HttpEntity he = hr.getEntity();
                    String data = EntityUtils.toString(he);

                    JSONArray array1 = new JSONArray(data);
                    for (int i = 0; i < array1.length(); i++) {
                        JSONObject ob2 = array1.getJSONObject(i);
                        String skill = ob2.getString("name");
                        list1.add(skill);
                    }
                }

            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> ad7 = new ArrayAdapter<String>(Loan_transfer.this, android.R.layout.simple_dropdown_item_1line, list1);

            city.setAdapter(ad7);
            city.setThreshold(2);
        }
    }


    private class GetAddress extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            geo = new Geocoder(getApplicationContext(), Locale.ENGLISH);
            dialog = new ProgressDialog(Loan_transfer.this);
//            dialog.setMessage("Registering..");
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

//            displayLocation();
            try {
                List<Address> mylist = geo.getFromLocation(lat, lon, 10);
                Address mynearadd = mylist.get(0);
                StringBuilder mystring = new StringBuilder();
                for (int i = 0; i < mynearadd.getMaxAddressLineIndex(); i++) {
                    mystring.append(mynearadd.getAddressLine(i)).append("\n");
                    s = mystring.toString();
                    Log.i("address :", "" + s);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            SharedPref.Location_gps = s;
            Log.i("location", "" + SharedPref.Location_gps);
            dialog.dismiss();

        }
    }


}
