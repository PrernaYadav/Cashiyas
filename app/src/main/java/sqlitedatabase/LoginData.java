package sqlitedatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;

/**
 * Created by JKB-2 on 3/21/2016.
 */
public class LoginData extends SQLiteOpenHelper {
    public LoginData(Context context) {
        super(context, "UserDbase", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS DetailsUser(SerialNo INTEGER PRIMARY KEY AUTOINCREMENT," +
                " FName VARCHAR(50)," +
                " LName VARCHAR(50)," +
                " Email VARCHAR(50)," +
                " Password VARCHAR(50)," +
                "Contactno VARCHAR(50)," +
                "image VARCHAR(250))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void insertPlace(HashMap<String, String> queryValues) {
        SQLiteDatabase database = this.getWritableDatabase();
        String Query = "SELECT * FROM DetailsUser";
        Cursor cursor = database.rawQuery(Query, null);

        if (cursor.getCount() < 10) {
            ContentValues values = new ContentValues();

            values.put("fname", queryValues.get("fname"));
            values.put("lname", queryValues.get("lname"));
//            values.put("UserFirstname", queryValues.get("UserFirstname"));
//            values.put("UserLastname", queryValues.get("UserLastname"));
            values.put("Email", queryValues.get("Email"));
            values.put("Password", queryValues.get("Password"));
            values.put("Phone", queryValues.get("Phone"));
            values.put("Loation", queryValues.get("Loation"));
            values.put("Category", queryValues.get("Category"));
            database.insert("DetailsUser", null, values);
            Log.i("ujsdfh",""+values);
            database.close();
        }
    }

    public boolean checkuser(String name, String emailid) {

        String Query = "SELECT * FROM DetailsUser where Username ='" + name
                + "' and Email='" + emailid + "'";
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(Query, null);

        if (cursor.getCount() <= 0) {
//            cursor.close();
            return false;
        }
//        cursor.close();
        return true;
    }


    public void deleteUser(String emailid)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            db.delete("DetailsUser", "Email = ?", new String[] { emailid });
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            db.close();
        }
    }
}
