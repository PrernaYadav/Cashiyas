package acunt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bounside.mj.cashiya.CheckInternet;
import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.SplashScreen;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;

import shared_pref.UserInformation;
import sqlitedatabase.LoginData;

//import com.google.android.gms.plus.Plus;
//import com.google.android.gms.plus.model.people.Person;

/*
*
 * Created by MJ on 3/19/16.

*/

public class SignIn extends Activity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleApiClient.ConnectionCallbacks {

    CheckInternet internet;

    boolean isavailable;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 0;
    private static final int MY_PERMISSIONS_REQUEST_GET_ACCOUNTS = 1;
    LoginData db = new LoginData(this);
    GoogleApiClient mGoogleApiClient;
    boolean mIntentInProgress;
    final int RC_SIGN_IN = 0;
    ConnectionResult mConnectionResult;
    boolean mSignInClicked;
    EditText loginuser, loginpass;
    Button google;
    ProgressDialog dialog;
    String success = null;
    SQLiteDatabase sql;
    public static String Email, username, userFirstName, userLastName;
    private static final int READ_SMS_PERMISSIONS_REQUEST = 5;
    final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    final int MY_PERMISSIONS_READ_EXTERNAL_STORAGE = 2;
    final int MY_PERMISSIONS_CALL_PHONE = 4;
    final int LOCATION_PERMISSION_REQUEST_CODE = 3;
    Jsonparse jsonParser = new Jsonparse(this);

    private static final String APP_ID = "220593884971893";
    private static final String APP_NAMESPACE = "Cashiya";
    Dialog pd;
    private static final int REQUEST_APP_SETTINGS = 168;

    private static final String[] requiredPermissions = new String[]{
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.READ_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

//        checklocation();
//        checklocationcourse();
        LoginData db = new LoginData(this);
        sql = db.getWritableDatabase();
        internet = new CheckInternet(SignIn.this);
        pd = new Dialog(SignIn.this);
        networkAvailable();
//        checklocation();
//        checklocationcourse();
//        getPermissionToReadUserContacts();
//        getPermissionToReadExternalStorage();
//        getPermissionTelephone();
//        checkReadContactsPerm();

//        SignIn.this.sp.edit().putBoolean("Pref-XiaomiMiui8ServiceSmsPerm", true).apply();
//        SignIn.this.startActivityForResult(.getPermissionManagerIntent(SignIn.this), 4479);

        if (ContextCompat.checkSelfPermission(SignIn.this, Manifest.permission.GET_ACCOUNTS)!= PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignIn.this,
                    Manifest.permission.GET_ACCOUNTS)) {

//                Toast.makeText(this, "Please trun on the permissions for getting google account", Toast.LENGTH_SHORT).show();

            } else {

                ActivityCompat.requestPermissions(SignIn.this,
                        new String[]{Manifest.permission.GET_ACCOUNTS},
                        MY_PERMISSIONS_REQUEST_GET_ACCOUNTS);
            }
        }

        checkPermissionForGettingLocation();

        google = (Button) findViewById(R.id.gogle);
        google.setOnClickListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void getPermissionToReadUserContacts() {

        if (ContextCompat.checkSelfPermission(SignIn.this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_CONTACTS)) {

//                Toast.makeText(this, "Permissions to read contacts is necessary for the application to perform", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void getPermissionToReadExternalStorage() {

        if (ContextCompat.checkSelfPermission(SignIn.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

//                Toast.makeText(this, "Permissions to access external storage is necessary for the application to perform", Toast.LENGTH_SHORT).show();

            }

            else {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_READ_EXTERNAL_STORAGE);
        }
    }}

    @TargetApi(Build.VERSION_CODES.M)
        public void getPermissionTelephone() {

             if (ContextCompat.checkSelfPermission(SignIn.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CALL_PHONE)) {
//                    Toast.makeText(this, "Permissions to access is necessary for the application to perform", Toast.LENGTH_SHORT).show();

                }

                else{
                requestPermissions(new String[]{Manifest.permission.CALL_PHONE},
                        MY_PERMISSIONS_CALL_PHONE);
            }}
    }

    public void checklocation() {

        if (ContextCompat.checkSelfPermission(SignIn.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignIn.this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
//                Toast.makeText(this, "Permissions to access location is necessary for the application to perform", Toast.LENGTH_SHORT).show();


            } else {
                ActivityCompat.requestPermissions(SignIn.this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            }
        }
    }


    public void checklocationcourse() {

        if (ContextCompat.checkSelfPermission(SignIn.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignIn.this,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {
                ActivityCompat.requestPermissions(SignIn.this,
                        new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);


            }
        }
    }


    public boolean networkAvailable() {
        isavailable = internet.isNetworkConnected();
        if (isavailable) {
            pd.dismiss();
            return true;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(
                    SignIn.this);
            builder.setTitle("No Internet Connection");
            builder.setIcon(R.drawable.fail);
            builder.setMessage("You don't have internet connetion");
            builder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
//                            finish();
                        }
                    });
            pd = builder.create();
            pd.show();
            return false;
        }
    }

    public void checkPermissionForGettingLocation() {

        final int fineLocationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
        final int coarseLocationPermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        final int externalStoragePermission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        final int readsmsPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_SMS);
        final int readcontactPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);

        if (
                fineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                coarseLocationPermission == PackageManager.PERMISSION_GRANTED &&
                externalStoragePermission == PackageManager.PERMISSION_GRANTED &&
                readsmsPermission == PackageManager.PERMISSION_GRANTED
                && readcontactPermission == PackageManager.PERMISSION_GRANTED) {

            checklocation();
            checklocationcourse();
//            getPermissionToReadUserContacts();
//            getPermissionToReadExternalStorage();
//            getPermissionTelephone();
            checkReadContactsPerm();

        } else {
            boolean requestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION);
            if (requestPermissionRationale) {

                Toast.makeText(this, "Location permission are required to get location.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("com.bounside.mj.cashiya", this.getPackageName(), null);
                intent.setData(uri);
                this.startActivity(intent);

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        }
    }

    public void checkReadContactsPerm() {

        if (ContextCompat.checkSelfPermission(SignIn.this, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SignIn.this, Manifest.permission.READ_SMS)) {}
            else {
                ActivityCompat.requestPermissions(SignIn.this, new String[]{Manifest.permission.READ_SMS}, READ_SMS_PERMISSIONS_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        if (requestCode == READ_SMS_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(SignIn.this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
//                  Toast.makeText(SignIn.this, "Please turn on permission to Read Contacts", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }


        if (requestCode == MY_PERMISSIONS_REQUEST_READ_CONTACTS) {
            // If request is cancelled, the result arrays are empty.
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // permission was granted, yay! Do the
                // contacts-related task you need to do.

            } else {
//                Toast.makeText(SignIn.this, "Please turn on permission to Read Contacts", Toast.LENGTH_SHORT).show();
                // permission denied, boo! Disable the
                // functionality that depends on this permission.
            }
        }

        if (requestCode == MY_PERMISSIONS_REQUEST_GET_ACCOUNTS) {
            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Toast.makeText(SignIn.this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(SignIn.this, "Please turn on permission to get google account", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //    getLocationAndSaveInDatabaseOrEnableGPS();
                // showGpsDialogAndGetLocation();
                checklocation();
                checklocationcourse();
//                getPermissionToReadUserContacts();
//                getPermissionToReadExternalStorage();
//                getPermissionTelephone();
                checkReadContactsPerm();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mIntentInProgress = false;
        if (!mGoogleApiClient.isConnecting()) {
            mGoogleApiClient.connect();
        }

        if (requestCode == REQUEST_APP_SETTINGS) {
            if (hasPermissions(requiredPermissions)) {
                Toast.makeText(this, "All permissions granted!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Permissions not granted.", Toast.LENGTH_LONG).show();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermissions(@NonNull String... permissions) {
        for (String permission : permissions)
            if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(permission))
                return false;
        return true;
    }


    private class GetData extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SignIn.this);
            dialog.setCancelable(false);
            dialog.setMessage("Signing In...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                }
            });

            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("password", UserInformation.Password));
            param.add(new BasicNameValuePair("email", UserInformation.Email));

            Log.i("broading", "password and email" + UserInformation.Password + "-" + UserInformation.Email);

            JSONObject jobj = jsonParser.makeHttpRequest(UserInformation.infopro, "POST", param);

            String message = "";
            try {
                message = jobj.getString("info");
                success = jobj.getString("status");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return message;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dialog.dismiss();
            if (success.equals("1")) {
                startActivity(new Intent(SignIn.this, SplashScreen.class));
            }
        }
    }

    private void displayData() {
        UserInformation.Email = loginuser.getText().toString();
        UserInformation.Password = loginpass.getText().toString();
        sql = db.getWritableDatabase();
        Cursor mCursor = sql.rawQuery("SELECT * FROM DetailsUser WHERE Email = '" + UserInformation.Email + "' AND Password = '" + UserInformation.Password + "' ", null);

        if (mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                UserInformation.UserFirstName = mCursor.getString(mCursor.getColumnIndex("FName"));
                UserInformation.UserLastName = mCursor.getString(mCursor.getColumnIndex("LName"));
            }
        }
// mCursor.close();
        new GetData().execute();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;

        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {

            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);

            String personPhotoUrl = currentPerson.getImage().getUrl();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            username = currentPerson.getName().getGivenName() + currentPerson.getName().getFamilyName();
            userFirstName = currentPerson.getName().getGivenName();
            userLastName = currentPerson.getName().getFamilyName();

            UserInformation.UserFirstName = userFirstName;
            UserInformation.UserLastName = userLastName;

            UserInformation.Image = personPhotoUrl;
            UserInformation.Email = email;
            UserInformation.Password = "gmail";

            Intent ii = new Intent(SignIn.this, SignUp.class);

            startActivity(ii);
        }
    }

    public void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                startIntentSenderForResult(mConnectionResult.getResolution()
                        .getIntentSender(), RC_SIGN_IN, null, 0, 0, 0);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        if (!mIntentInProgress) {
            mConnectionResult = result;
            if (mSignInClicked) {
                resolveSignInError();
            }
        }
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.gogle:
                if (!mGoogleApiClient.isConnecting()) {
                    mSignInClicked = true;
                    resolveSignInError();
                }
              break;
        }
    }

    public class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SignIn.this);
            dialog.setCancelable(false);
            dialog.setMessage("Registering...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<NameValuePair>();

            param.add(new BasicNameValuePair("username", UserInformation.UserFirstName));
            param.add(new BasicNameValuePair("location", UserInformation.Location));
            param.add(new BasicNameValuePair("password", UserInformation.Password));
            param.add(new BasicNameValuePair("email", UserInformation.Email));
            // param.add(new BasicNameValuePair("phone", UserInformation.Phone));

            JSONObject jobj = jsonParser.makeHttpRequest(UserInformation.register, "POST", param);
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
            Intent ii = new Intent(SignIn.this, SignUp.class);

            startActivity(ii);
            overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

            finish();
        }
    }

    @Override
    public void onBackPressed() {
        finish();
//        super.onBackPressed();

//        Intent intent = new Intent(SignIn.this, Splash_screen.class);
//        startActivity(intent);
//        overridePendingTransition(R.anim.move_left_in_activity, R.anim.move_right_out_activity);
    }
}




