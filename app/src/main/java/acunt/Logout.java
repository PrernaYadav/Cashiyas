package acunt;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bounside.mj.cashiya.R;

import sqlitedatabase.LoginData;

/**
 * Created by Tamanna on 9/3/2016.
 */
public class Logout extends AppCompatActivity {

    LoginData dp = new LoginData(this);
    SQLiteDatabase sql;
    ContentValues can;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);

        can = new ContentValues();
        sql = dp.getReadableDatabase();
        dp.deleteUser(getEmailId());
        Intent intent3 = new Intent(this, SignIn.class);
        intent3.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent3);
        finish();
    }

    private String getEmailId() {
        String emailid = "";
        Cursor mCursor = sql.rawQuery("SELECT * FROM DetailsUser", null);


        if (mCursor.moveToFirst()) {
            do {
                emailid = mCursor.getString(mCursor.getColumnIndex("Email"));
            } while (mCursor.moveToNext());
        }

//        mCursor.close();
        return emailid;
    }
}
