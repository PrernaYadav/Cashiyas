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
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.Jsonparse;

import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import shared_pref.SharedPref;
import shared_pref.UserInformation;
import sqlitedatabase.LoginData;

/**
 * Created by rasika on 3/30/2016.
 */
public class    LoanUser extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener, LocationListener,
        GoogleApiClient.ConnectionCallbacks {

    private static final String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
    private static final int GOOGLE_API_CLIENT_ID = 0;
    private static String random_value;
    private final StringBuilder sb = new StringBuilder(4);
    private EditText editTextPersonalStd;
    private EditText editTextPrsonalPhone;
    private String seditTextPersonalStd;
    private String seditTextPrsonalPhone;
    private RadioButton saluser;
    private RadioButton selfuser;
    private RadioButton radioButtonRented;
    private RadioButton radioButtonOwned;
    private RadioGroup radioGroupGender;
    private RadioButton radioButtonMale;
    private RadioButton radioButtonFemale;
    private Spinner loantype;
    private Spinner timeperiod;
    private EditText borrowm;
    private EditText editTextCurrentAddress;
    private EditText editTextCurrentAddress1;
    private EditText editTextCurrentAddressPincode;
    private EditText editTextPermanentAdress;
    private EditText editTextPermanentAdress1;
    private EditText editTextPermanentAdressPincode;
    private Random random = new Random();
    private AutoCompleteTextView editTextCurrentAddressCity;
    private AutoCompleteTextView editTextPermanentAdressCity;
    private AutoCompleteTextView editTextCurrentAddressState;
    private AutoCompleteTextView editTextPermanentAdressState;
    private LinearLayout mainLayoutPermanentAddress;
    private Button proceed;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhone;
    private RadioGroup radioGroupPermanentAddress;
    private EditText dobloan;
    private EditText current_residence_since;
    private EditText editTextPropertyAddress;
    private LinearLayout lhome;
    private LinearLayout ledu;
    private LinearLayout lgold;
    private LinearLayout lper;
    private LinearLayout lcar;
    private LinearLayout lpro;
    private Spinner time_home;
    private Spinner time_per;
    private Spinner time_gold;
    private Spinner time_edu;
    private Spinner time_property;
    private Spinner type_car;
    private Spinner type_home;
    private Spinner type_edu;
    private Spinner type_property;
    private String getmonth;
    private Jsonparse jsonParser = new Jsonparse(this);
    private ProgressDialog dialog;
    private LoginData db = new LoginData(this);
    private SQLiteDatabase sql;
    private EditText email;
    private int getyr;
    private int getmn;
    private int getdatess;
    private String month_name2;
    private String month_name1;
    private String month_name;
    private EditText adharcard_loan;
    private EditText pancard_loan;
    private EditText residence;
    private Location mLastLocation;
    private Pattern pattern;
    private Pattern pattern2;
    private Matcher matcher;
    private Matcher matcher2;
    private String myFormatss = "dd-MM-yyyy"; //In which you need put here
    private SimpleDateFormat sdfs = new SimpleDateFormat(myFormatss, Locale.US);
    private Date convertedDate = new Date();
    private Date convertedDate2 = new Date();
    private Date convertedDate3 = new Date();
    private long ss;
    private String panCardPatttern = "[A-Za-z]{3}[chfatbljgpCHFATBLJGP]{1}[A-Za-z]{1}[0-9]{4}[A-Za-z]{1}";
    private String seditTextPropertyAddress;
    private Spinner spinnerProofAddress;
    private EditText editTextBuilderName;
    private String seditTextBuilderName;
    private RadioButton radioButtonSingle;
    private RadioButton radioButtonMarried;
    private LinearLayout property;
    // Google client to interact with Google API
    private List<String> list = new ArrayList<String>();
    private List<String> list1 = new ArrayList<String>();
    private List<String> list2 = new ArrayList<String>();
    private List<String> list4 = new ArrayList<String>();
    private String s;

    private RadioGroup radioGroupResidenceType;
    private RadioGroup radioGroupMaritalStatus;
    private RadioGroup radioGroupTypeOfProperty;
    private RadioGroup radioGroupConstruction;
    private RadioGroup radioGroupVerified;
    private RadioButton radioButtonResale;
    private RadioButton radioButtonBuilder;
    private RadioButton radioButtonUnderConstruction;
    private RadioButton radioButtonreadyToMove;
    private RadioButton radioButtonVerified;
    private RadioButton radioButtonUnverified;
    private Geocoder geo;
    // boolean flag to toggle periodic location updates
    private double lat;
    private double lon;
    private Calendar myCalendar1 = Calendar.getInstance();
    private Calendar myCalendar2 = Calendar.getInstance();

    private String[] arraySpinnerlong = new String[]{
            "6", "12", "18", "24", "30", "36", "42", "48", "54", "60", "66", "72", "78", "84",
    };

    private String selecteddate1, selecteddate2;
    private GoogleApiClient mGoogleApiClient;
    private DatePickerDialog dobloanDatePicker;
    private DatePickerDialog currResSinceDatePicker;

    private static int getDiffYears(Date first, Date last) {
        Log.i("FIRST DATE", "" + first);
        Log.i("LAST DATE", "" + last);
        Calendar a = getCalendar(first);
        Log.i("CALENDER VARIABLE A", "" + a);
        Calendar b = getCalendar(last);
        Log.i("CALENDER VARIABLE B", "" + b);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        Log.i("DIFFEENCE IN TIME", "" + diff);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        Log.i("DIFFERENCE IN YEARS", "" + diff);
        return diff;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loan_user);

        sql = db.getReadableDatabase();
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setTitle("Loan Form");
            ac.setDisplayHomeAsUpEnabled(true);
        }

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

        mGoogleApiClient = new GoogleApiClient.Builder(LoanUser.this)
                .addApi(Places.GEO_DATA_API)
                .enableAutoManage(this, GOOGLE_API_CLIENT_ID, this)
                .addConnectionCallbacks(this)
                .build();

        //spinner1
        String[] arraySpinnerloan = new String[]{
                "Select", "Car Loan", "Education Loan", "Gold Loan", "Home Loan", "Personal Loan", "Loan Against Property"
        };
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerloan);
        loantype.setAdapter(adapter1);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        timeperiod.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        time_gold.setAdapter(adapter4);

        String[] arrayspinner4 = new String[]{
                "Select", "Passport", "Ration Card", "Driving License", "Pan Card"
        };

        ArrayAdapter<String> adapter10 = new ArrayAdapter<String>(this, R.layout.spinner_item, arrayspinner4);
        spinnerProofAddress.setAdapter(adapter10);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        time_home.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        time_per.setAdapter(adapter6);

        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        time_property.setAdapter(adapter8);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        time_edu.setAdapter(adapter7);

        /////////subcategory of types of loan///////////
        this.arraySpinnerlong = new String[]{
                "New Car", "Used Car"
        };
        ArrayAdapter<String> adapter17 = new ArrayAdapter<String>(this, R.layout.spinner_item, arraySpinnerlong);
        type_car.setAdapter(adapter17);

        this.arraySpinnerlong = new String[]{
                "Graduation", "Post Graduation"
        };
        ArrayAdapter<String> adapter27 = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinnerlong);
        type_edu.setAdapter(adapter27);


        this.arraySpinnerlong = new String[]{
                "Transfer your existing loan", "New Loan-Property identified", "New Loan-Property Not identified"
        };
        ArrayAdapter<String> adapter37 = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinnerlong);
        type_home.setAdapter(adapter37);


        this.arraySpinnerlong = new String[]{
                "Loan against residential property", "Loan against commercial property", "Loan against industrial property"
        };
        ArrayAdapter<String> adapter74 = new ArrayAdapter<String>(this,
                R.layout.spinner_item, arraySpinnerlong);
        type_property.setAdapter(adapter74);


        residence = (EditText) findViewById(R.id.residence1);
        residence.setInputType(EditorInfo.IME_ACTION_NEXT);

        buildGoogleApiClient();

        borrowm.setOnClickListener(this);

        proceed.setOnClickListener(this);

        //*******************transfer loan **********************//

        loantype.setOnItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        editTextFirstName.setText(UserInformation.UserFirstName);
        editTextLastName.setText(UserInformation.UserLastName);

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

    private void initializeFields() {
        residence = (EditText) findViewById(R.id.residence1);
        timeperiod = (Spinner) findViewById(R.id.period);
        time_edu = (Spinner) findViewById(R.id.period_edu);
        time_home = (Spinner) findViewById(R.id.period_home);
        time_per = (Spinner) findViewById(R.id.period_per);
        time_property = (Spinner) findViewById(R.id.period_property);
        dobloan = (EditText) findViewById(R.id.dob_loan);
        current_residence_since = (EditText) findViewById(R.id.current_res_since);
        editTextPersonalStd = (EditText) findViewById(R.id.editTextPersonalStd);
        editTextPrsonalPhone = (EditText) findViewById(R.id.editTextPrsonalPhone);
        pancard_loan = (EditText) findViewById(R.id.pancard_loan);
        type_edu = (Spinner) findViewById(R.id.edu_typeloan);
        type_car = (Spinner) findViewById(R.id.car_typeloan);
        type_home = (Spinner) findViewById(R.id.home_typeloan);
        proceed = (Button) findViewById(R.id.proceed_user_loan);
        lcar = (LinearLayout) findViewById(R.id.time_car);
        email = (EditText) findViewById(R.id.editTextEmail);
        email.setText(UserInformation.Email);
        property = (LinearLayout) findViewById(R.id.linearLayoutProperty);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        editTextPhone.setText(UserInformation.Phone);
        editTextBuilderName = (EditText) findViewById(R.id.editTextBuilderName);
        editTextPropertyAddress = (EditText) findViewById(R.id.editTextPropertyAddress);
        residence = (EditText) findViewById(R.id.residence1);
        saluser = (RadioButton) findViewById(R.id.radio_salaried);
        selfuser = (RadioButton) findViewById(R.id.radio_self_employee);
        loantype = (Spinner) findViewById(R.id.cat_loan_user);
        radioButtonMale = (RadioButton) findViewById(R.id.radioButtonMale);
        radioButtonFemale = (RadioButton) findViewById(R.id.radioButtonFemale);
        borrowm = (EditText) findViewById(R.id.borrow_im);
        time_gold = (Spinner) findViewById(R.id.period_gold);
        adharcard_loan = (EditText) findViewById(R.id.adhar_loan);
        type_edu = (Spinner) findViewById(R.id.edu_typeloan);
        type_property = (Spinner) findViewById(R.id.property_typeloan);
        type_home = (Spinner) findViewById(R.id.home_typeloan);
        lcar = (LinearLayout) findViewById(R.id.time_car);
        lhome = (LinearLayout) findViewById(R.id.time_home);
        lgold = (LinearLayout) findViewById(R.id.time_gold);
        ledu = (LinearLayout) findViewById(R.id.time_edu);
        lper = (LinearLayout) findViewById(R.id.time_personal);
        lpro = (LinearLayout) findViewById(R.id.time_property);

        radioGroupTypeOfProperty = (RadioGroup) findViewById(R.id.radioGroupTypeOfProperty);
        radioGroupConstruction = (RadioGroup) findViewById(R.id.radioGroupConstruction);
        radioGroupVerified = (RadioGroup) findViewById(R.id.radioGroupVerified);
        radioGroupGender = (RadioGroup) findViewById(R.id.radioGroupGender);

        radioButtonResale = (RadioButton) findViewById(R.id.radioButtonResale);
        radioButtonBuilder = (RadioButton) findViewById(R.id.radioButtonBuilder);
        radioButtonreadyToMove = (RadioButton) findViewById(R.id.radioButtonreadyToMove);
        radioButtonVerified = (RadioButton) findViewById(R.id.radioButtonVerified);
        radioButtonUnverified = (RadioButton) findViewById(R.id.radioButtonUnverified);
        radioButtonUnderConstruction = (RadioButton) findViewById(R.id.radioButtonUnderConstruction);
        mainLayoutPermanentAddress = (LinearLayout) findViewById(R.id.mainLayoutPermanentAddress);
        radioGroupPermanentAddress = (RadioGroup) findViewById(R.id.radioGroupPermanentAddress);
        spinnerProofAddress = (Spinner) findViewById(R.id.spinnerProofAddress);

        radioGroupResidenceType = (RadioGroup) findViewById(R.id.radioGroupResidenceType);
        radioButtonRented = (RadioButton) findViewById(R.id.radioButtonRented);
        radioButtonOwned = (RadioButton) findViewById(R.id.radioButtonOwned);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        radioGroupMaritalStatus = (RadioGroup) findViewById(R.id.radioGroupMaritalStatus);
        radioButtonSingle = (RadioButton) findViewById(R.id.radioButtonSingle);
        radioButtonMarried = (RadioButton) findViewById(R.id.radioButtonMarried);

        editTextCurrentAddress = (EditText) findViewById(R.id.editTextCurrentAddress);
        editTextCurrentAddress1 = (EditText) findViewById(R.id.editTextCurrentAddress1);
        editTextCurrentAddressCity = (AutoCompleteTextView) findViewById(R.id.editTextCurrentAddressCity);
        editTextCurrentAddressState = (AutoCompleteTextView) findViewById(R.id.editTextCurrentAddressState);
        editTextCurrentAddressPincode = (EditText) findViewById(R.id.editTextCurrentAddressPincode);
        editTextPermanentAdress = (EditText) findViewById(R.id.editTextPermanentAdress);
        editTextPermanentAdress1 = (EditText) findViewById(R.id.editTextPermanentAdress1);
        editTextPermanentAdressCity = (AutoCompleteTextView) findViewById(R.id.editTextPermanentAdressCity);
        editTextPermanentAdressState = (AutoCompleteTextView) findViewById(R.id.editTextPermanentAdressState);
        editTextPermanentAdressPincode = (EditText) findViewById(R.id.editTextPermanentAdressPincode);


        ArrayAdapter<String> ada1 = new ArrayAdapter<String>(LoanUser.this,
                android.R.layout.select_dialog_singlechoice, list1);
        editTextCurrentAddressCity.setThreshold(4);
        editTextCurrentAddressCity.setAdapter(ada1);
        editTextCurrentAddressCity.setTextColor(Color.BLACK);
        ArrayAdapter<String> ada2 = new ArrayAdapter<String>(LoanUser.this,
                android.R.layout.select_dialog_singlechoice, list2);
        editTextPermanentAdressCity.setThreshold(4);
        editTextPermanentAdressCity.setAdapter(ada2);
        editTextPermanentAdressCity.setTextColor(Color.BLACK);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(LoanUser.this,
                android.R.layout.select_dialog_singlechoice, list);
        editTextCurrentAddressState.setThreshold(2);
        editTextCurrentAddressState.setAdapter(ada);
        editTextCurrentAddressState.setTextColor(Color.BLACK);
        ArrayAdapter<String> ada4 = new ArrayAdapter<String>(LoanUser.this,
                android.R.layout.select_dialog_singlechoice, list4);
        editTextPermanentAdressState.setThreshold(2);
        editTextPermanentAdressState.setAdapter(ada4);
        editTextPermanentAdressState.setTextColor(Color.BLACK);

        editTextFirstName.setText(UserInformation.UserFirstName);
        editTextLastName.setText(UserInformation.UserLastName);

        new searchexample().execute(SharedPref.state_api);
        new searchexample2().execute(SharedPref.state_api);
        new searchexample1().execute(SharedPref.city_api);
        new searchexample4().execute(SharedPref.city_api);
        // Creating adapter for spinner

        radioGroupPermanentAddress.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View radioButton = radioGroupPermanentAddress.findViewById(checkedId);
                int index = radioGroupPermanentAddress.indexOfChild(radioButton);
                // Add logic here
                switch (index) {
                    case 0: // first button
                        editTextPermanentAdress.setText(editTextCurrentAddress.getText().toString());
                        editTextPermanentAdress1.setText(editTextCurrentAddress1.getText().toString());
                        editTextPermanentAdressCity.setText(editTextCurrentAddressCity.getText().toString());
                        editTextPermanentAdressState.setText(editTextCurrentAddressState.getText().toString());
                        editTextPermanentAdressPincode.setText(editTextCurrentAddressPincode.getText().toString());
                        mainLayoutPermanentAddress.setVisibility(View.GONE);
                        break;
                    case 1: // secondbutton
                        editTextPermanentAdress.setText("");
                        editTextPermanentAdress1.setText("");
                        editTextPermanentAdressCity.setText("");
                        editTextPermanentAdressState.setText("");
                        editTextPermanentAdressPincode.setText("");
                        mainLayoutPermanentAddress.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        dobloan.setInputType(InputType.TYPE_NULL);
        current_residence_since.setInputType(InputType.TYPE_NULL);

        final DatePickerDialog.OnDateSetListener dateloan = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar1.set(Calendar.YEAR, year);
                myCalendar1.set(Calendar.MONTH, monthOfYear);
                SimpleDateFormat month_date = new SimpleDateFormat("dd");
                month_name = month_date.format(myCalendar1.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar1.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar1.getTime());
                Log.i("month", "" + month_name2);

                updateLabeloan();
            }

            private void updateLabeloan() {

                String myFormat = "dd-MM-yyyy";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddate1 = sdf.format(myCalendar1.getTime());
                dobloan.setText(selecteddate1);
            }
        };

        final DatePickerDialog.OnDateSetListener dateCurrentResi = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myCalendar2.set(Calendar.YEAR, year);
                myCalendar2.set(Calendar.MONTH, monthOfYear);
                SimpleDateFormat month_date = new SimpleDateFormat("dd");
                month_name = month_date.format(myCalendar2.getTime());
                Log.i("month", "" + month_name);

                SimpleDateFormat month_year = new SimpleDateFormat("MM");
                month_name1 = month_year.format(myCalendar2.getTime());
                Log.i("month", "" + month_name1);

                SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
                month_name2 = month_year1.format(myCalendar2.getTime());
                Log.i("month", "" + month_name2);

                updateCurrResSince();
            }

            private void updateCurrResSince() {

                String myFormat = "dd-MM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddate2 = sdf.format(myCalendar2.getTime());
                current_residence_since.setText(selecteddate2);
            }
        };

        dobloan.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dobloanDatePicker =
                new DatePickerDialog(LoanUser.this, dateloan, myCalendar1
                        .get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH),
                        myCalendar1.get(Calendar.DAY_OF_MONTH));

                dobloanDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                dobloanDatePicker.show();
            }
        });

        current_residence_since.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                currResSinceDatePicker =
                new DatePickerDialog(LoanUser.this, dateCurrentResi, myCalendar2
                        .get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH),
                        myCalendar2.get(Calendar.DAY_OF_MONTH));
                currResSinceDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                currResSinceDatePicker.show();

            }
        });
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

    private void submit() {

        SharedPref.loantypes = loantype.getSelectedItem().toString();

        UserInformation.sProofAddress = spinnerProofAddress.getSelectedItem().toString();

        if (spinnerProofAddress.getSelectedItem().toString().equals("Ration Card")) {
            UserInformation.sProofAddress = "Ration Card";
        }
        if (spinnerProofAddress.getSelectedItem().toString().equals("Passport")) {
            UserInformation.sProofAddress = "PassPort";
        }
        if (spinnerProofAddress.getSelectedItem().toString().equals("Driving License")) {
            UserInformation.sProofAddress = "Driving License ";
        }
        if (spinnerProofAddress.getSelectedItem().toString().equals("Pan Card")) {
            UserInformation.sProofAddress = "Pan Card";
        }

        String timevalue;
        String loan_sub_category;
        if (loantype.getSelectedItem().toString().equals("Car Loan")) {
            SharedPref.loantypes = "Car Loan";

            timevalue = timeperiod.getSelectedItem().toString();
            SharedPref.time = timevalue;

            loan_sub_category = type_car.getSelectedItem().toString();
            SharedPref.subcatory_loan = loan_sub_category;

        }

        if (loantype.getSelectedItem().toString().equals("Education Loan")) {
            SharedPref.loantypes = "Education Loan";

            timevalue = time_edu.getSelectedItem().toString();
            SharedPref.time = timevalue;

            loan_sub_category = type_edu.getSelectedItem().toString();
            SharedPref.subcatory_loan = loan_sub_category;

        }

        if (loantype.getSelectedItem().toString().equals("Gold Loan")) {
            SharedPref.loantypes = "Gold Loan";

            timevalue = time_gold.getSelectedItem().toString();
            SharedPref.time = timevalue;

        }

        if (loantype.getSelectedItem().toString().equals("Home Loan")) {
            SharedPref.loantypes = "Home Loan";
            timevalue = time_home.getSelectedItem().toString();
            SharedPref.time = timevalue;

            loan_sub_category = type_home.getSelectedItem().toString();
            SharedPref.subcatory_loan = loan_sub_category;
        }

        if (loantype.getSelectedItem().toString().equals("Personal Loan")) {
            SharedPref.loantypes = "Personal Loan";

            timevalue = time_per.getSelectedItem().toString();
            SharedPref.time = timevalue;

        }

        if (loantype.getSelectedItem().toString().equals("Loan Against Property")) {
            SharedPref.loantypes = "Loan Against Property";

            timevalue = time_property.getSelectedItem().toString();
            SharedPref.time = timevalue;

            loan_sub_category = type_property.getSelectedItem().toString();
            SharedPref.subcatory_loan = loan_sub_category;
        }

        SharedPref.loanmant = borrowm.getText().toString();

        SharedPref.city = residence.getText().toString();
        SharedPref.Adharcard_loan = adharcard_loan.getText().toString();
        SharedPref.pancard_loan = pancard_loan.getText().toString();
        SharedPref.dob_loan = dobloan.getText().toString();


        SharedPref.current_resi_date = current_residence_since.getText().toString();
        UserInformation.Phone = editTextPhone.getText().toString();

        UserInformation.seditTextLastName = editTextLastName.getText().toString();
        seditTextBuilderName = editTextBuilderName.getText().toString();
        UserInformation.seditTextCurrentAddress = editTextCurrentAddress.getText().toString();
        UserInformation.seditTextCurrentAddress1 = editTextCurrentAddress1.getText().toString();
        UserInformation.seditTextCurrentAddressPincode = editTextCurrentAddressPincode.getText().toString();
        UserInformation.seditTextPermanentAdress = editTextPermanentAdress.getText().toString();
        UserInformation.seditTextPermanentAdress1 = editTextPermanentAdress1.getText().toString();
        UserInformation.seditTextPermanentAdressPincode = editTextPermanentAdressPincode.getText().toString();
        UserInformation.seditTextCurrentAddressCity = editTextCurrentAddressCity.getText().toString();
        UserInformation.seditTextPermanentAdressCity = editTextPermanentAdressCity.getText().toString();
        UserInformation.seditTextCurrentAddressState = editTextCurrentAddressState.getText().toString();
        UserInformation.seditTextPermanentAdressState = editTextPermanentAdressState.getText().toString();
        seditTextPersonalStd = editTextPersonalStd.getText().toString();
        seditTextPrsonalPhone = editTextPrsonalPhone.getText().toString();
        seditTextPropertyAddress = editTextPropertyAddress.getText().toString();

        if (saluser.isChecked()) {
//            SharedPref.emptype = saluser.getText().toString();
            SharedPref.emptype = "salaried";
        }
        if (radioButtonResale.isChecked()) {
            SharedPref.sRadioBtnTypeOfProperty = "Resale";
        }
        if (radioButtonUnderConstruction.isChecked()) {
            SharedPref.sradioButtonreadyToMove = "Under Construction";
        }
        if (radioButtonVerified.isChecked()) {
            SharedPref.sradioButtonVerified = "Verified";
        }
        if (radioButtonUnverified.isChecked()) {
            SharedPref.sradioButtonVerified = "Unverified";
        }
        if (radioButtonreadyToMove.isChecked()) {
            SharedPref.sradioButtonreadyToMove = "Ready To Move";
        }
        if (radioButtonBuilder.isChecked()) {
            SharedPref.sRadioBtnTypeOfProperty = "Builder";
        }
        if (selfuser.isChecked()) {
            SharedPref.emptype = "self employed";
        }
        if (radioButtonMale.isChecked()) {
            SharedPref.gender = "male";
        }
        if (radioButtonFemale.isChecked()) {
            SharedPref.gender = "female";
        }
        if (radioButtonSingle.isChecked()) {
            SharedPref.sradioButtonMaritalStatus = "Single";
        }
        if (radioButtonRented.isChecked()) {
            SharedPref.sRadioButtonResidenceType = "Rented";
        }
        if (radioButtonOwned.isChecked()) {
            SharedPref.sRadioButtonResidenceType = "Owned";
        }
        if (radioButtonMarried.isChecked()) {
            SharedPref.sradioButtonMaritalStatus = "Married";
        }
        new GetAddress().execute();
    }


    @Override
    public void onConnected(Bundle bundle) {

        displayLocation();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        buildGoogleApiClient();

    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String selectedItem = adapterView.getItemAtPosition(i).toString();
        if (selectedItem.equals("Select")) {
            lcar.setVisibility(View.GONE);
            lgold.setVisibility(View.GONE);
            lhome.setVisibility(View.GONE);
            ledu.setVisibility(View.GONE);
            lper.setVisibility(View.GONE);
            lpro.setVisibility(View.GONE);
            property.setVisibility(View.GONE);
        }

        if (selectedItem.equals("Car Loan")) {
            lcar.setVisibility(View.VISIBLE);
            lgold.setVisibility(View.GONE);
            lhome.setVisibility(View.GONE);
            ledu.setVisibility(View.GONE);
            lper.setVisibility(View.GONE);
            lpro.setVisibility(View.GONE);
            property.setVisibility(View.GONE);
        }

        if (selectedItem.equals("Education Loan")) {
            lcar.setVisibility(View.GONE);
            lgold.setVisibility(View.GONE);
            lhome.setVisibility(View.GONE);
            ledu.setVisibility(View.VISIBLE);
            lper.setVisibility(View.GONE);
            lpro.setVisibility(View.GONE);
            property.setVisibility(View.GONE);

        }

        if (selectedItem.equals("Gold Loan")) {
            lcar.setVisibility(View.GONE);
            lgold.setVisibility(View.VISIBLE);
            lhome.setVisibility(View.GONE);
            ledu.setVisibility(View.GONE);
            property.setVisibility(View.GONE);
            lper.setVisibility(View.GONE);
            lpro.setVisibility(View.GONE);
        }

        if (selectedItem.equals("Personal Loan")) {
            lcar.setVisibility(View.GONE);
            lgold.setVisibility(View.GONE);
            lhome.setVisibility(View.GONE);
            ledu.setVisibility(View.GONE);
            lper.setVisibility(View.VISIBLE);
            lpro.setVisibility(View.GONE);
            property.setVisibility(View.GONE);
        }

        if (selectedItem.equals("Home Loan")) {
            lcar.setVisibility(View.GONE);
            lgold.setVisibility(View.GONE);
            property.setVisibility(View.VISIBLE);
            lhome.setVisibility(View.VISIBLE);
            ledu.setVisibility(View.GONE);
            lper.setVisibility(View.GONE);
            lpro.setVisibility(View.GONE);

        }

        if (selectedItem.equals("Loan Against Property")) {
            lcar.setVisibility(View.GONE);
            lgold.setVisibility(View.GONE);
            lhome.setVisibility(View.GONE);
            ledu.setVisibility(View.GONE);
            lper.setVisibility(View.GONE);
            lpro.setVisibility(View.VISIBLE);
            property.setVisibility(View.GONE);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void displayLocation() {

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(100); // Update location every second


        try {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

//            Toast.makeText(this, "Please Turn on the permissions for accessing location", Toast.LENGTH_SHORT).show();

        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (mLastLocation != null) {
            lat = (mLastLocation.getLatitude());
            lon = (mLastLocation.getLongitude());
        }
    }

    private synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        mGoogleApiClient.connect();
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

           /* if (dialog != null ) {
                dialog.dismiss();
                dialog = null;
            }*/

        mGoogleApiClient.disconnect();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    protected void onPause() {
        super.onPause();

        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
    }

    @Override
    public void onLocationChanged(Location location) {
        lat = (location.getLatitude());
        lon = (location.getLongitude());
    }

    //// gps location asynck task location

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.proceed_user_loan:

                try {
                    convertedDate3 = sdfs.parse(getmonth);
                    Log.i("getParsedMonth", "" + convertedDate3);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    convertedDate2 = sdfs.parse(current_residence_since.getText().toString());
                    Log.i("CURRENTRESIDENCESINCE", "" + convertedDate2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                try {
                    convertedDate = sdfs.parse(dobloan.getText().toString());
                    Log.i("DOBLOAN", "" + convertedDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ss = getDiffYears(convertedDate, convertedDate3);
                Log.i("DIFFERENCEINYEARS", "" + ss);

                submit();

                pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
                matcher = pattern.matcher(SharedPref.pancard_loan);

                pattern2 = Pattern.compile("^([a-zA-Z0-9]*)$");
                matcher2 = pattern2.matcher(SharedPref.city);

                ///////////////////////////////FORM VALIDATIONS//////////////////////////////////

                if (UserInformation.UserFirstName.isEmpty() || UserInformation.UserFirstName.equals(null)) {
                    editTextFirstName.setError("Please enter your first name");
                    editTextFirstName.requestFocusFromTouch();

                } else if (UserInformation.UserLastName.isEmpty() || UserInformation.UserLastName.equals(null)) {
                    editTextLastName.setError("Please enter your last name");
                    editTextLastName.requestFocusFromTouch();

                } else if (loantype.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {
                    dobloan.setError(null);
                    TextView errorText = (TextView) loantype.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please select Type of Loan");//changes the selected item text to this

                    loantype.requestFocusFromTouch();

                } else if (loantype.getSelectedItem().toString().equals("Home Loan") && radioGroupTypeOfProperty.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select the type of property", Toast.LENGTH_SHORT).show();

                } else if (loantype.getSelectedItem().toString().equals("Home Loan") && radioGroupConstruction.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select the property status", Toast.LENGTH_SHORT).show();

                } else if (loantype.getSelectedItem().toString().equals("Home Loan") && radioGroupVerified.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select if the property is verified", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(SharedPref.loanmant)) {
                    borrowm.setError("Please enter the loan amount");
                    borrowm.requestFocusFromTouch();

                } else if (!(SharedPref.loantypes.contains("Home Loan")) && Integer.parseInt(SharedPref.loanmant) < 50000) {
                    borrowm.setError("Loan Amount should be equal to or above 50000");
                    borrowm.requestFocusFromTouch();

                } else if (!(SharedPref.loantypes.contains("Home Loan")) && Integer.parseInt(SharedPref.loanmant) > 2000000) {
                    borrowm.setError("Loan Amount should be less than 2000000");
                    borrowm.requestFocusFromTouch();

                } else if (SharedPref.loantypes.contains("Home Loan") && Integer.parseInt(SharedPref.loanmant) < 50000) {
                    borrowm.setError("Loan Amount should be equal to or above 50000");
                    borrowm.requestFocusFromTouch();

                } else if (SharedPref.loantypes.contains("Home Loan") && Integer.parseInt(SharedPref.loanmant) > 10000000) {
                    borrowm.setError("Loan Amount should be less than 10000000");
                    borrowm.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(SharedPref.sradioButtonMaritalStatus)) {
                    Toast.makeText(this, "Please select your marital status", Toast.LENGTH_SHORT).show();

                } else if (radioGroupMaritalStatus.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select your marital status", Toast.LENGTH_SHORT).show();

                } else if (adharcard_loan.length() == 0) {
                    adharcard_loan.setError("Enter your Aadhar number");
                    adharcard_loan.requestFocusFromTouch();

                } else if (adharcard_loan.length() < 12) {
                    adharcard_loan.setError("Please Enter Correct Aadhar number");

                } else if (!SharedPref.pancard_loan.matches(panCardPatttern)) {
                    pancard_loan.setError("Please enter a Valid Pan Card Number");
                    pancard_loan.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(SharedPref.dob_loan)) {
                    Toast.makeText(LoanUser.this, "Please Enter your Date of Birth", Toast.LENGTH_SHORT).show();

                    dobloan.requestFocusFromTouch();
                    dobloan.requestFocus();
                    dobloan.setSelected(true);

                } else if (ss < 1) {
                    Toast.makeText(LoanUser.this, "Please Enter a valid Date of Birth", Toast.LENGTH_SHORT).show();

                    dobloan.requestFocusFromTouch();
                    dobloan.requestFocus();
                    dobloan.setSelected(true);

                } else if (ss < 21) {
                    Toast.makeText(LoanUser.this, "Age should not be less than 21", Toast.LENGTH_SHORT).show();
                    dobloan.requestFocusFromTouch();

                } else if (ss > 65) {
                    Toast.makeText(LoanUser.this, "Age should not be greater than 65", Toast.LENGTH_SHORT).show();
                    dobloan.requestFocusFromTouch();

                } else if (radioGroupGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(SharedPref.gender)) {
                    Toast.makeText(this, "Please select your gender", Toast.LENGTH_SHORT).show();

                } else if (!matcher2.matches()) {
                    residence.setError("Enter Address correctly");
                    residence.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(UserInformation.seditTextCurrentAddress)) {

                    editTextCurrentAddress.setError("Field cannot be left empty");
                    editTextCurrentAddress.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(UserInformation.seditTextCurrentAddress1)) {

                    editTextCurrentAddress1.setError("Field cannot be left empty");
                    editTextCurrentAddress1.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(UserInformation.seditTextCurrentAddressCity)) {
                    editTextCurrentAddressCity.setError("Field cannot be left empty");
                    editTextCurrentAddressCity.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(UserInformation.seditTextCurrentAddressState)) {
                    editTextCurrentAddressState.setError("Field cannot be left empty");
                    editTextCurrentAddressState.requestFocusFromTouch();

                } else if (TextUtils.isEmpty(UserInformation.seditTextCurrentAddressPincode)) {
                    editTextCurrentAddressPincode.setError("Field cannot be left empty");
                    editTextCurrentAddressPincode.requestFocus();

                } else if ((UserInformation.seditTextCurrentAddressPincode.length()<6)) {
                    editTextCurrentAddressPincode.setError("Please enter correct pincode");
                    editTextCurrentAddressPincode.requestFocus();

                } else if (TextUtils.isEmpty(seditTextPersonalStd)) {
                    editTextPersonalStd.setError("Please fill in the std code");
                } else if (TextUtils.isEmpty(seditTextPrsonalPhone)) {
                    editTextPrsonalPhone.setError("Please fill in the phone No.");

                } else if (seditTextPrsonalPhone.length()<7 || seditTextPrsonalPhone.length() > 10) {
                    editTextPrsonalPhone.setError("Please fill in a valid phone No.");

                } else if (radioGroupResidenceType.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select residence type", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(SharedPref.sRadioButtonResidenceType)) {
                    Toast.makeText(this, "Please select residence type", Toast.LENGTH_SHORT).show();

                } else if (current_residence_since.getText().toString().trim().length() < 1) {
                    Toast.makeText(LoanUser.this, "Enter the date since you are residing", Toast.LENGTH_SHORT).show();

                    current_residence_since.requestFocusFromTouch();
                    current_residence_since.requestFocus();
                    current_residence_since.setSelected(true);

                } else if (radioGroupPermanentAddress.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(this, "Please select whether your current address is your permanent address", Toast.LENGTH_SHORT).show();

                } else if (radioGroupPermanentAddress.indexOfChild(radioGroupPermanentAddress.findViewById(radioGroupPermanentAddress.getCheckedRadioButtonId())) == 1) {

                    if (TextUtils.isEmpty(UserInformation.seditTextPermanentAdress)) {

                        editTextPermanentAdress.setError("Field cannot be left empty");
                        editTextPermanentAdress.requestFocusFromTouch();

                    } else if (TextUtils.isEmpty(UserInformation.seditTextPermanentAdress1)) {
                        editTextPermanentAdress1.setError("Field cannot be left empty");

                    } else if (TextUtils.isEmpty(UserInformation.seditTextPermanentAdressState)) {
                        editTextPermanentAdressState.setError("Field cannot be left empty");

                    } else if (TextUtils.isEmpty(UserInformation.seditTextPermanentAdressCity)) {
                        editTextPermanentAdressCity.setError("Field cannot be left empty");

                    } else if (TextUtils.isEmpty(UserInformation.seditTextPermanentAdressPincode)) {
                        editTextPermanentAdressPincode.setError("Field cannot be left empty");
                        editTextPermanentAdressPincode.requestFocusFromTouch();

                    } else if ((UserInformation.seditTextPermanentAdressPincode.length()<6)) {
                        editTextPermanentAdressPincode.setError("Please enter correct pincode");
                        editTextPermanentAdressPincode.requestFocusFromTouch();

                    } else if (spinnerProofAddress.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {

                    TextView errorText = (TextView) spinnerProofAddress.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("Please select Address Proof");
                    spinnerProofAddress.requestFocusFromTouch();

                } else {

                    new insertusermain().execute();

                }
                break;

                } else if (spinnerProofAddress.getSelectedItem().toString().trim().equalsIgnoreCase("Select")) {

                    TextView errorText = (TextView) spinnerProofAddress.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);
                    errorText.setText("Please select Address Proof");
                    spinnerProofAddress.requestFocusFromTouch();

                } else {

                    new insertusermain().execute();

                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class GetAddress extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            geo = new Geocoder(getApplicationContext(), Locale.ENGLISH);
//            dialog = new ProgressDialog(LoanUser.this);
//            dialog.setMessage("Registering Address...");
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
                    Log.i("FULL ADDRESS :", "" + s);
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
            Log.i("LOCATION", "" + SharedPref.Location_gps);
//            dialog.dismiss();
        }
    }

    @SuppressWarnings("deprecation")
    private class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(LoanUser.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            for (int i = 0; i < 4; ++i) {
                sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
                random_value = sb.toString();
                SharedPref.random = random_value;
            }

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("random", random_value));
            param.add(new BasicNameValuePair("loan_type", SharedPref.loantypes));
            param.add(new BasicNameValuePair("subcategory_loan", SharedPref.subcatory_loan));
            param.add(new BasicNameValuePair("borrow_time", SharedPref.time));
            param.add(new BasicNameValuePair("borrow", SharedPref.loanmant));
            param.add(new BasicNameValuePair("adhar_card", SharedPref.Adharcard_loan));
            param.add(new BasicNameValuePair("pan_card", SharedPref.pancard_loan));
            param.add(new BasicNameValuePair("dob", SharedPref.dob_loan));
            param.add(new BasicNameValuePair("emp_type", SharedPref.emptype));
            param.add(new BasicNameValuePair("gender", SharedPref.gender));
            param.add(new BasicNameValuePair("marital_status", SharedPref.sradioButtonMaritalStatus));
            param.add(new BasicNameValuePair("residence_type", SharedPref.sRadioButtonResidenceType));
            param.add(new BasicNameValuePair("gps_location", SharedPref.Location_gps));
            param.add(new BasicNameValuePair("fname", UserInformation.UserFirstName));
            param.add(new BasicNameValuePair("lname", UserInformation.UserLastName));
            param.add(new BasicNameValuePair("email", UserInformation.Email));
            param.add(new BasicNameValuePair("per_std", seditTextPersonalStd));
            param.add(new BasicNameValuePair("per_phone", seditTextPrsonalPhone));
            param.add(new BasicNameValuePair("phone", UserInformation.Phone));
            param.add(new BasicNameValuePair("type_of_property", SharedPref.sRadioBtnTypeOfProperty));
            param.add(new BasicNameValuePair("build_society_name", seditTextBuilderName));
            param.add(new BasicNameValuePair("property_add", seditTextPropertyAddress));
            param.add(new BasicNameValuePair("property_status", SharedPref.sradioButtonreadyToMove));
            param.add(new BasicNameValuePair("property_veri_status", SharedPref.sradioButtonVerified));
            param.add(new BasicNameValuePair("curr_add_line1", UserInformation.seditTextCurrentAddress));
            param.add(new BasicNameValuePair("curr_add_line2", UserInformation.seditTextCurrentAddress1));
            param.add(new BasicNameValuePair("per_add_line1", UserInformation.seditTextPermanentAdress));
            param.add(new BasicNameValuePair("per_add_line2", UserInformation.seditTextPermanentAdress1));
            param.add(new BasicNameValuePair("curr_pincode", UserInformation.seditTextCurrentAddressPincode));
            param.add(new BasicNameValuePair("per_pincode", UserInformation.seditTextPermanentAdressPincode));
            param.add(new BasicNameValuePair("curr_city", UserInformation.seditTextCurrentAddressCity));
            param.add(new BasicNameValuePair("curr_state", UserInformation.seditTextCurrentAddressState));
            param.add(new BasicNameValuePair("per_city", UserInformation.seditTextPermanentAdressCity));
            param.add(new BasicNameValuePair("per_state", UserInformation.seditTextPermanentAdressState));
            param.add(new BasicNameValuePair("curr_Res_Since", SharedPref.current_resi_date));
            param.add(new BasicNameValuePair("curr_resi_proof", UserInformation.sProofAddress));

            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.loaninsurance, "POST", param);
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
            Log.i("1st form result", "" + result);
//            dialog.dismiss();
            Intent i = new Intent(LoanUser.this, SunilProceedLoan.class);
            i.putExtra("id", random_value);
            i.putExtra("loantype", SharedPref.loantypes);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            startActivity(i);
            finish();
        }
    }

    private class searchexample extends AsyncTask<String, Void, Boolean> {

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
//                        id1 = ob2.getString("id");
                        list.add(skill);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> ad = new ArrayAdapter<String>(LoanUser.this, android.R.layout.simple_dropdown_item_1line, list);

            editTextCurrentAddressState.setAdapter(ad);
            editTextCurrentAddressState.setThreshold(1);

        }
    }

    private class searchexample2 extends AsyncTask<String, Void, Boolean> {

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
//                        id1 = ob2.getString("id");
                        list4.add(skill);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> adeditTextPermanentAdressState = new ArrayAdapter<String>(LoanUser.this, android.R.layout.simple_dropdown_item_1line, list4);

            editTextPermanentAdressState.setAdapter(adeditTextPermanentAdressState);
            editTextPermanentAdressState.setThreshold(1);

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

            ArrayAdapter<String> ad7 = new ArrayAdapter<String>(LoanUser.this, android.R.layout.simple_dropdown_item_1line, list1);

            editTextCurrentAddressCity.setAdapter(ad7);
            editTextCurrentAddressCity.setThreshold(2);
        }
    }

    private class searchexample4 extends AsyncTask<String, Void, Boolean> {

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
                        list2.add(skill);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);

            ArrayAdapter<String> adeditTextPermanentAdressCity = new ArrayAdapter<String>(LoanUser.this, android.R.layout.simple_dropdown_item_1line, list2);

            editTextPermanentAdressCity.setAdapter(adeditTextPermanentAdressCity);
            editTextPermanentAdressCity.setThreshold(4);

        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        AlertDialog.Builder builder=new AlertDialog.Builder(LoanUser.this);
        // builder.setCancelable(false);
        builder.setTitle("");
        builder.setMessage("Your application is not complete. Incomplete application would not get processed. Proceed exiting the form?");
        builder.setPositiveButton("yes",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent in1 = new Intent(LoanUser.this, MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(in1);
                finish();
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
