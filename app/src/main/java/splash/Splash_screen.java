package splash;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import java.util.Timer;
import java.util.TimerTask;

import acunt.SignUp;
import sqlitedatabase.LoginData;

/**
 * Created by Tamanna on 9/8/2016.
 */
public class Splash_screen extends Activity {

    private TextView tt;
    private final LoginData dt = new LoginData(this);
    private SQLiteDatabase sql;
    private String email = "";
    private String password = "";
    private TimerTask task;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash_screen);

        tt = (TextView) findViewById(R.id.animms);

//        displayData();
        new getPrivateList().execute();
        //database
    }

    private void displayData() {

        try {
            Cursor mCursor = sql.rawQuery("SELECT * FROM DetailsUser", null);

            if (mCursor.moveToFirst()) {
                do {
                    email = mCursor.getString(mCursor.getColumnIndex("Email"));
                    password = mCursor.getString(mCursor.getColumnIndex("Password"));

                } while (mCursor.moveToNext());
            }
//            mCursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class getPrivateList extends AsyncTask<String, Void, SimpleAdapter> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sql = dt.getReadableDatabase();
            TranslateAnimation animation = new TranslateAnimation(0, 0, -250, 0);
            animation.setDuration(3000);
            tt.startAnimation(animation);
        }

        @Override
        protected SimpleAdapter doInBackground(String... params) {
            displayData();

            return null;
        }

        @Override
        protected void onPostExecute(SimpleAdapter result) {
            super.onPostExecute(result);
            task = new TimerTask() {

                @Override
                public void run() {

                    try {
                        if (email != null /*|| password != null*/) {

                            if (email.equals("") /*|| password.equals("")*/) {
                                Intent intent = new Intent(Splash_screen.this, PermissionMi.class);
                                startActivity(intent);
                                finish();
                            } else {
                                startActivity(new Intent(Splash_screen.this, MainActivity.class));
                                finish();
                            }
                        } else {
                            startActivity(new Intent(Splash_screen.this, SignUp.class));

//                            finish();
//                            Toast.makeText(Splash_screen.this, "You need to sign in", Toast.LENGTH_SHORT).show();
                        }
                        } catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                ;
                Timer timer = new Timer();
            timer.schedule(task, 3000);
        }
    }
}

