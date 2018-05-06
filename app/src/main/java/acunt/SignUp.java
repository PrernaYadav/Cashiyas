package acunt;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import contactslist.ContactBean;
import de.hdodenhof.circleimageview.CircleImageView;
import shared_pref.UserInformation;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import otpfetch.OtpFetch;
import shared_pref.SharedPref;
import sqlitedatabase.LoginData;

/**
 * Created by MJ on 3/19/16.
 */
public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private static final int CAMERA_RESULT_CODE = 111;
    private static final int GALLERY_RESULT_CODE = 112;
    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1;
    private static final int REQUEST_APP_SETTINGS = 168;
    private static String successs = "";
    private EditText username;
    private EditText email;
    private EditText phone;
    private EditText editTextLastName;
    private Button signup;
    private final Jsonparse jsonParser = new Jsonparse(this);
    private final JSONObject jobj_arr = new JSONObject();
    private ProgressDialog dialog;
    private String mCurrentPhotoPath;
    private LinkedHashMap<String, String> hmap;
    private final List<Map> lstmsg = new ArrayList<>();
    private SQLiteDatabase sql;
    private final LoginData db = new LoginData(this);
    private String success;
    private CircleImageView imgProfilePicture;
    private final List<ContactBean> list = new ArrayList<>();
    private Bitmap bitmapForGalleryCamera;

    /********** email_id validation *********/

    private static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        LoginData db = new LoginData(this);
        sql = db.getWritableDatabase();
        initToolBar();

        getPermissionToReadSMS();

        username = (EditText) findViewById(R.id.userfirstname);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        signup = (Button) findViewById(R.id.signup);

        email.setText(UserInformation.Email);
        phone.setText(UserInformation.Phone);
        username.setText(UserInformation.UserFirstName);
        editTextLastName.setText(UserInformation.UserLastName);

        UserInformation.Email = email.getText().toString();
        imgProfilePicture = (CircleImageView) findViewById(R.id.profileimage);
        Glide.with(getApplicationContext()).load(UserInformation.Image)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgProfilePicture);
    }

    private void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (toolbar != null) {
            toolbar.setTitle("Sign up");
        }

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        signup.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null && dialog.isShowing())
            dialog.dismiss();
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

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.signup:
                UserInformation.Email = email.getText().toString();

                checkForPermissions();
                break;

            case R.id.profileimage:
                inflateMenu(v);
                break;

            default:
                break;
        }
    }

    private void inflateMenu(View v) {
        PopupMenu popup = new PopupMenu(SignUp.this, v);

        /** Adding menu items to the popumenu */
        popup.getMenuInflater().inflate(R.menu.profileimage
                , popup.getMenu());

        /** Defining menu item click listener for the popup menu */
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.opencamera:

                        captureImage();

                        return true;
                    case R.id.browsegallery:

                        showFileChooser();

                        return true;
                }
                return false;
            }
        });
        /** Showing the popup menu */
        popup.show();
    }

    // function to capture image
    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAMERA_RESULT_CODE);
            }
        }
    }

    // creating image
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
                .format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY_RESULT_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {

            case CAMERA_RESULT_CODE:
                if (resultCode == RESULT_OK) {
                    setPic();
                }
                break;

            case GALLERY_RESULT_CODE:

                if (resultCode == RESULT_OK && data != null) {
                    Uri filePath = data.getData();
                    try {
                        Log.d("USER_PROFILE", "" + filePath);
                        //Getting the Bitmap from Gallery
                        bitmapForGalleryCamera = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                        //Setting the Bitmap to ImageView
                        imgProfilePicture.setImageBitmap(bitmapForGalleryCamera);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void setPic() {
        try {
            // Get the dimensions of the View
            int targetW = imgProfilePicture.getWidth();
            int targetH = imgProfilePicture.getHeight();

            // Get the dimensions of the bitmap
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            // Determine how much to scale down the image
            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

            // Decode the image file into a Bitmap sized to fill the View
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;
            bmOptions.inPurgeable = true;

            bitmapForGalleryCamera = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
            imgProfilePicture.setImageBitmap(bitmapForGalleryCamera);
        } catch (Exception e) {
            Log.getStackTraceString(e);
        }
    }

    private void submituser() {

        displayData();
        UserInformation.Email = email.getText().toString();

        if(UserInformation.Phone == null /*&& !UserInformation.Phone.isEmpty() && !UserInformation.Phone.equals("")*/) {
            UserInformation.Phone = phone.getText().toString();
        }
        UserInformation.seditTextLastName = editTextLastName.getText().toString();

        if (phone.getText().toString().length() < 10) {
            phone.setError("Fill Valid Phone Number");

        } else if (editTextLastName.length() > 26) {
            editTextLastName.setError("Please Fill valid Last Name");

        } else if (!UserInformation.Phone.equals("") && username.length() > 2 && email.length() > 2) {

            if (isValidEmail(UserInformation.Email)) {

                new insertusermain().execute();
                new contactsinsert().execute();
            } else {
                Toast.makeText(SignUp.this, "Check your email id", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(SignUp.this, "Required field missing or Not Proper", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkForPermissions() {

        if (ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {

            submituser();

        } else {
            Toast.makeText(this, "Please Turn on all the App permissions", Toast.LENGTH_SHORT).show();
            goToSettings();

        }
    }

    private void goToSettings() {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(myAppSettings, REQUEST_APP_SETTINGS);
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void getPermissionToReadSMS() {

        if (ContextCompat.checkSelfPermission(SignUp.this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED) {

            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_CONTACTS)) {
            }

            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    MY_PERMISSIONS_REQUEST_READ_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {

        if (requestCode == MY_PERMISSIONS_REQUEST_READ_SMS) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(SignIn.this, "Read Contacts permission granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(SignUp.this, "Please turn on permission to Read SMSs", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    /**************
     * inserting user data into database
     ***********/
    @SuppressWarnings("deprecation")
    private class insertusermain extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(SignUp.this);
            dialog.setCancelable(false);
            dialog.setMessage("Please Wait...");
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<>();

            param.add(new BasicNameValuePair("first_name", UserInformation.UserFirstName));
            param.add(new BasicNameValuePair("last_name", UserInformation.UserLastName));
            param.add(new BasicNameValuePair("email", UserInformation.Email));
            param.add(new BasicNameValuePair("phone", UserInformation.Phone));

            Log.i("param", "param:" + param);
            JSONObject jobj = jsonParser.makeHttpRequest(UserInformation.register, "POST", param);
            String message = "";
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
            if (dialog != null && dialog.isShowing())
                dialog.dismiss();
            dialog = null;

            Log.i("tag", "" + result);

            switch (successs) {
                case "1": {
                    Log.i("USERINFO:", "" + UserInformation.UserFirstName + "" + UserInformation.Email + "" + UserInformation.Phone);

                    Intent in = new Intent(SignUp.this, OtpFetch.class);

                    in.putExtra("username", UserInformation.UserFirstName);
                    in.putExtra("email", UserInformation.Email);
                    in.putExtra("phone", UserInformation.Phone);
                    startActivity(in);

                    overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);

                    break;
                }
                case "0": {
                    Intent in = new Intent(SignUp.this, CreateNewProfile.class);

                    startActivity(in);
                    break;
                }
                default:
                    Toast.makeText(SignUp.this, "Error in Signing Up.", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private class contactsinsert extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog = new ProgressDialog(SignUp.this);
//            dialog.setCancelable(false);
//            dialog.setMessage("Registering...");
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<NameValuePair> param = new ArrayList<>();

            Cursor phones = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
                    null, null);
            int cc = 0;
            if (phones != null) {
                cc = phones.getCount();

                int kk = 0;
                Log.i("cccc", "..........." + cc);
                while (phones.moveToNext() && kk < 1500) {

                    String name = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));

                    String phoneNumber = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    ContactBean objContact = new ContactBean();
                    objContact.setName(name);
                    objContact.setPhoneNo(phoneNumber);

                    hmap = new LinkedHashMap<>();
                    list.add(objContact);
//                    Log.i("list", "..........." + list);
                    hmap.put("a", phoneNumber);
//                    Log.i("phoneNumber", "..........." + hmap);
                    hmap.put("b", name);
//                    Log.i("name", "..........." + hmap);
                    lstmsg.add(hmap);
                    kk++;

                    Log.i("contacts", "..........." + lstmsg);
                }
//                phones.close();
            }


            param.add(new BasicNameValuePair("contact", "" + lstmsg));
            Log.i("paramrr", "" + jobj_arr);
            param.add(new BasicNameValuePair("email", "" + UserInformation.Email));
            JSONObject jobj = jsonParser.makeHttpRequest(SharedPref.contacts, "POST", param);
            String message = "";
            Log.i("jobj", "" + SharedPref.contacts);

            try {
                message = jobj.getString("msg");
                success = jobj.getString("status");

                Log.i("jobj", "" + message);
                Log.i("jobj", "" + success);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return message;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
//            dialog.dismiss();
            Log.i("result", "" + result);
        }
    }

    private void displayData() {
        UserInformation.Email = email.getText().toString();
        sql = db.getWritableDatabase();
        Cursor mCursor = sql.rawQuery("SELECT * FROM DetailsUser WHERE Email = '" + UserInformation.Email+"' ", null);

        if (mCursor.getCount() > 0) {
            while (mCursor.moveToNext()) {
                UserInformation.UserFirstName = mCursor.getString(mCursor.getColumnIndex("FName"));
                UserInformation.UserLastName = mCursor.getString(mCursor.getColumnIndex("LName"));
                UserInformation.Phone = mCursor.getString(mCursor.getColumnIndex("Contactno"));

                Log.i("UserInformation.FName", "" + UserInformation.UserFirstName);
                Log.i("UserInformation.LName", "" + UserInformation.UserLastName);
                Log.i("UserInformation.Phone", "" + UserInformation.Phone);
            }
        }
//        mCursor.close();
    }
}
