package sqlitedatabase;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import reminder.MainNotification;

/**
 * Created by Tamanna on 7/13/2016.
 */
public class Datab_notify extends SQLiteOpenHelper {
    public Datab_notify(Context context) {
        super(context, "Notification_data", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS notify" +
                "(datenotify DATETIME," +
                " timenotify DATETIME ," +
                "monthdata INTEGER," +
                " message VARCHAR(50))");

        db.execSQL("CREATE TABLE IF NOT EXISTS notify_emi" +
                "(messageid VARCHAR(25)," +
                "changesdate DATETIME," +
                "monthsdate INTEGER," +
                "yearsdata INTEGER," +
                "datenotify_emi DATETIME," +
                "monthdata_emi INTEGER," +
                "bodynoti VARCHAR(500))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public void deleteUser(String userName)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        try
        {
            db.delete("notify", "datenotify = ?", new String[] { userName });
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
