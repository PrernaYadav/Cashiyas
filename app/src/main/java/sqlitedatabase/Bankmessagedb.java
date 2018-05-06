package sqlitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import shared_pref.UserInformation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Neeraj Sain on 7/19/2016.
 */
public class Bankmessagedb extends SQLiteOpenHelper {
    public Calendar calendar;
    String year, currentMonthValue;
    String key;
    String ano, anm;

    public Bankmessagedb(Context context) {
        super(context, "Messages", null, 1);
    }

    private static int getPreviousYear() {
        Calendar prevYear = Calendar.getInstance();
        prevYear.add(Calendar.YEAR, -1);
        return prevYear.get(Calendar.YEAR);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS trans_msg" +
                "(ID INTEGER PRIMARY KEY," +
                "msgid VARCHAR(25)," +
                "nameofbank VARCHAR(100)," +
                "type VARCHAR(100)," +
                "body VARCHAR(500), " +
                "transdate DATETIME," +
                "changdate DATETIME," +
                "mnthdate INTEGER," +
                "yrdata INTEGER," +
                "category VARCHAR(200)," +
                "amount VARCHAR(100)," +
                "accunttype VARCHAR(500)," +
                "accuntnumber VARCHAR(300)," +
                "msgtime VARCHAR(200))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public void gettransCategory(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());

            String query = "SELECT DISTINCT type FROM trans_msg WHERE amount > 0.0 And changdate LIKE '%-" + key + "-" + year + "' ORDER BY type";
            SQLiteDatabase sql = this.getWritableDatabase();
            List<String> lst = new ArrayList<String>();
            Cursor c = sql.rawQuery(query, null);

        /*
        * Foo[] array = new Foo[list.size()];
list.toArray(array);*/
            if (c.moveToFirst()) {
                do {
                    lst.add(String.valueOf(c.getString(0)));
                }
                while (c.moveToNext());
            }
            String[] arr = new String[lst.size()];
            UserInformation.arr = new String[lst.size()];
            UserInformation.arr = lst.toArray(arr);
            for (String s : UserInformation.arr)
                Log.i("array", "arr" + s);

            //1return UserInformation.arr;
        } else {

            year = month_year1.format(calendar.getTime());

            String query = "SELECT DISTINCT type FROM trans_msg WHERE amount > 0.0 And changdate LIKE '%-" + key + "-" + year + "' ORDER BY type";
            SQLiteDatabase sql = this.getWritableDatabase();
            List<String> lst = new ArrayList<String>();
            Cursor c = sql.rawQuery(query, null);
//
//

        /*
        * Foo[] array = new Foo[list.size()];
list.toArray(array);*/
            if (c.moveToFirst()) {
                do {
                    lst.add(String.valueOf(c.getString(0)));
                }
                while (c.moveToNext());
            }
            String[] arr = new String[lst.size()];
            UserInformation.arr = new String[lst.size()];
            UserInformation.arr = lst.toArray(arr);
            for (String s : UserInformation.arr)
                Log.i("array", "arr" + s);

//        c.close();
            //1return UserInformation.arr;
        }
    }

    public float gettransFood(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'Food' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountfood = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountfood = UserInformation.totalamountfood + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totalamountfood;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Food' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountfood = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountfood = UserInformation.totalamountfood + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totalamountfood;
        }
    }

    public float gettransFuel(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());

            String query = "SELECT * FROM trans_msg WHERE type = 'Fuel' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountfuel = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    Log.i("amnt", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totalamountfuel = UserInformation.totalamountfuel + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountfuel;
        } else {

            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Fuel' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountfuel = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    Log.i("amnt", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totalamountfuel = UserInformation.totalamountfuel + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountfuel;
        }
    }

    public float gettransShopping(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());

            String query = "SELECT * FROM trans_msg WHERE type = 'Shopping' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountshopping = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountshopping = UserInformation.totalamountshopping + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountshopping;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Shopping' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountshopping = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountshopping = UserInformation.totalamountshopping + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountshopping;
        }
    }

    public float gettransElectricity(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());

            String query = "SELECT * FROM trans_msg WHERE type = 'Bills' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountbills = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountbills = UserInformation.totalamountbills + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountbills;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Bills' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountbills = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountbills = UserInformation.totalamountbills + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountbills;
        }
    }

    public float gettransGroceries(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'Groceries' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountgroceries = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountgroceries = UserInformation.totalamountgroceries + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountgroceries;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Groceries' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountgroceries = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountgroceries = UserInformation.totalamountgroceries + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountgroceries;
        }
    }

    public float gettransHealth(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'Health' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamounthealth = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamounthealth = UserInformation.totalamounthealth + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamounthealth;
        } else {

            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Health' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamounthealth = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamounthealth = UserInformation.totalamounthealth + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamounthealth;
        }
    }
    public float gettransTravel(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'Travel' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamounttravel = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamounttravel = UserInformation.totalamounttravel + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamounttravel;
        } else {

            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Travel' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamounttravel = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamounttravel = UserInformation.totalamounttravel + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamounttravel;
        }
    }
    public float gettransOther(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'Other' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountother = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountother = UserInformation.totalamountother + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountother;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Other' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountother = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalamountother = UserInformation.totalamountother + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountother;
        }
    }

    public float gettransEntertainment(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'Entertainment' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountentertainment = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    int temp = Integer.parseInt(amnt);
                    UserInformation.totalamountentertainment = UserInformation.totalamountentertainment + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totalamountentertainment;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'Entertainment' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalamountentertainment = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    int temp = Integer.parseInt(amnt);
                    UserInformation.totalamountentertainment = UserInformation.totalamountentertainment + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totalamountentertainment;
        }
    }

    public float gettransextra(String aa) {

        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'SPENT' And changdate LIKE '%-" + key + "-" + year + "' ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransextra = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ukkkkk", "  extratxn " + amnt + ".." + temp);
                    UserInformation.totaltransextra = UserInformation.totaltransextra + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltransextra;
        } else {

            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'SPENT' And changdate LIKE '%-" + key + "-" + year + "' ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransextra = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ukkkkk", "  extratxn " + amnt + ".." + temp);
                    UserInformation.totaltransextra = UserInformation.totaltransextra + temp;
                }
                while (c.moveToNext());
            }
            return UserInformation.totaltransextra;
        }
    }

    public float gettransdebit(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'DEBITED.' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransdebit = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totaltransdebit = UserInformation.totaltransdebit + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltransdebit;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'DEBITED.' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransdebit = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totaltransdebit = UserInformation.totaltransdebit + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltransdebit;
        }
    }

    public float gettranspurc(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());

            String query = "SELECT * FROM trans_msg WHERE type = 'PURCHASE.' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltranspurchase = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totaltranspurchase = UserInformation.totaltranspurchase + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltranspurchase;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'PURCHASE.' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltranspurchase = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totaltranspurchase = UserInformation.totaltranspurchase + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltranspurchase;
        }
    }

    public float gettranscredit(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'CREDITED.' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltranscredit = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totaltranscredit = UserInformation.totaltranscredit + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltranscredit;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'CREDITED.' And changdate LIKE '%-" + key + "-" + year + "'";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltranscredit = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totaltranscredit = UserInformation.totaltranscredit + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totaltranscredit;
        }
    }
    /////////Sum of all available balance of all accounts //////////////////////////////////////////////////////////////////

    public float gettransavailable(String aa) {
        key = aa;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        year = month_year1.format(calendar.getTime());

        int jan = 1;
        int Feb = 2;
        int Mar = 3;
        int Apr = 4;
        int May = 5;
        int Jun = 6;
        int Jul = 7;
        int Aug = 8;
        int Sep = 9;
        int Oct = 10;
        int Nov = 11;
        int Dec = 12;

        SimpleDateFormat currentMonth = new SimpleDateFormat("MM");
        String currentMonthValue = currentMonth.format(calendar.getTime());
        int currentMontIntValue = Integer.parseInt(currentMonthValue);

        int monthName = 0;

        if (key.contains("Jan")) monthName = jan;
        if (key.contains("Feb")) monthName = Feb;
        if (key.contains("Mar")) monthName = Mar;
        if (key.contains("Apr")) monthName = Apr;
        if (key.contains("May")) monthName = May;
        if (key.contains("Jun")) monthName = Jun;
        if (key.contains("Jul")) monthName = Jul;
        if (key.contains("Aug")) monthName = Aug;
        if (key.contains("Sep")) monthName = Sep;
        if (key.contains("Oct")) monthName = Oct;
        if (key.contains("Nov")) monthName = Nov;
        if (key.contains("Dec")) monthName = Dec;

        if (currentMontIntValue < monthName) {
            year = String.valueOf(getPreviousYear());
            String query = "SELECT * FROM trans_msg WHERE type = 'AVAILABLE BALANCE' And changdate LIKE '%-" + key + "-" + year + "' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalavailable = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalavailable = UserInformation.totalavailable + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totalavailable;
        } else {
            year = month_year1.format(calendar.getTime());
            String query = "SELECT * FROM trans_msg WHERE type = 'AVAILABLE BALANCE' And changdate LIKE '%-" + key + "-" + year + "' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totalavailable = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(10));
                    float temp = Float.parseFloat(amnt);
                    UserInformation.totalavailable = UserInformation.totalavailable + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            return UserInformation.totalavailable;
        }
    }

    public float gettransavailable_sum(String aaa) {
        key = aaa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'AVAILABLE BALANCE' And changdate LIKE '%-" + key + "-" + year + "' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalavailable = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalavailable = UserInformation.totalavailable + temp;
            }
            while (c.moveToNext());
        }
//        c.close();
        return UserInformation.totalavailable;
    }

    //////////////////////////////////////////End of the Method/////////////////////////////////////////////////////////
    public float gettransJan() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("year", "" + year);
        String month = "Jan";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransjan = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);

                UserInformation.totaltransjan = UserInformation.totaltransjan + temp;

            }

            while (c.moveToNext());
        }
//        c.close();
        Log.i("totaltransjan", "  jantotal " + UserInformation.totaltransjan);
        return UserInformation.totaltransjan;

    }

    public float gettransFeb() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01")
                ) {
            Log.i("(calendar.getTime()", "" + year);
            String month = "Feb";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            Log.i("Febtotal", "  Febtotal " + query);

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransfeb = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Febtotal", "  Febtotal " + amnt + ".." + temp);
                    UserInformation.totaltransfeb = UserInformation.totaltransfeb + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Febtotal", "  totalfeb " + UserInformation.totaltransfeb);
            return UserInformation.totaltransfeb;
        }
        //
       /* c.add(Calendar.MONTH, -1);

        int month = c.get(Calendar.MONTH) + 1; // beware of month indexing from zero
        int year  = c.get(Calendar.YEAR);*/

        Log.i("(calendar.getTime()", "" + year);
        String month = "Feb";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        Log.i("Febtotal", "  Febtotal " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransfeb = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("Febtotal", "  Febtotal " + amnt + ".." + temp);
                UserInformation.totaltransfeb = UserInformation.totaltransfeb + temp;
            }
            while (c.moveToNext());
        }
//        c.close();
        Log.i("Febtotal", "  totalfeb " + UserInformation.totaltransfeb);
        return UserInformation.totaltransfeb;
    }

    public float gettransMarch() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02")
                ) {
            Log.i("calendar.getTime()", "" + year);
            String month = "Mar";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransmar = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Martotal", "  Martotal " + amnt + ".." + temp);
                    UserInformation.totaltransmar = UserInformation.totaltransmar + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Martotal", "  totalmar " + UserInformation.totaltransmar);
            return UserInformation.totaltransmar;

        }
        else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Mar";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransmar = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Martotal", "  Martotal " + amnt + ".." + temp);
                    UserInformation.totaltransmar = UserInformation.totaltransmar + temp;

                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Martotal", "  totalmar " + UserInformation.totaltransmar);
            return UserInformation.totaltransmar;

        }
    }

    public float gettransApr() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03")
                ) {
            Log.i("calendar.getTime()", "" + year);
            String month = "Apr";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransapril = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Aprtotal", "  Aprtotal " + amnt + ".." + temp);
                    UserInformation.totaltransapril = UserInformation.totaltransapril + temp;

                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Aprtotal", "  totalapril " + UserInformation.totaltransapril);
            return UserInformation.totaltransapril;
        }

        else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Apr";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransapril = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Aprtotal", "  Aprtotal " + amnt + ".." + temp);
                    UserInformation.totaltransapril = UserInformation.totaltransapril + temp;

                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Aprtotal", "  totalapril " + UserInformation.totaltransapril);
            return UserInformation.totaltransapril;

        }
    }

    public float gettransMay() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04")
                ) {

            Log.i("calendar.getTime()", "" + year);
            String month = "May";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransmay = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Maytotal", "  Maytotal " + amnt + ".." + temp);
                    UserInformation.totaltransmay = UserInformation.totaltransmay + temp;

                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("array", "  Maytotal " + UserInformation.totaltransmay);
            return UserInformation.totaltransmay;

        }
        else {
            Log.i("calendar.getTime()", "" + year);
            String month = "May";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransmay = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Maytotal", "  Maytotal " + amnt + ".." + temp);
                    UserInformation.totaltransmay = UserInformation.totaltransmay + temp;

                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Maytotal", "  jantotal " + UserInformation.totaltransmay);
            return UserInformation.totaltransmay;

        }
    }

    public float gettransJune() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05")
                ) {
            Log.i("calendar.getTime()", "" + year);
            String month = "Jun";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransjune = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Juntotal", "  Juntotal " + amnt + ".." + temp);
                    UserInformation.totaltransjune = UserInformation.totaltransjune + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Juntotal", "  totaljune " + UserInformation.totaltransjune);
            return UserInformation.totaltransjune;
        } else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Jun";

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransjune = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("Juntotal", "  Juntotal " + amnt + ".." + temp);
                    UserInformation.totaltransjune = UserInformation.totaltransjune + temp;
                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("Juntotal", "  totaljune " + UserInformation.totaltransjune);
            return UserInformation.totaltransjune;
        }
    }

    public float gettransJuly() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05") ||
                        currentMonthValue.contentEquals("06")
                ) {
            Log.i("calendar.getTime()", "" + year);
            String month = "Jul";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransjuly = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransjuly = UserInformation.totaltransjuly + temp;

                }
                while (c.moveToNext());
            }
//            c.close();
            Log.i("array", "  totaljuly " + UserInformation.totaltransjuly);
            return UserInformation.totaltransjuly;

        } else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Jul";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransjuly = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransjuly = UserInformation.totaltransjuly + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totaljuly " + UserInformation.totaltransjuly);
            return UserInformation.totaltransjuly;
        }
    }

    public float gettransAug() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05") ||
                        currentMonthValue.contentEquals("06") ||
                        currentMonthValue.contentEquals("07")
                ) {

            Log.i("calendar.getTime()", "" + year);
            String month = "Aug";
            year = String.valueOf(getPreviousYear());
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            Log.i("ufguiofg", "  jantotal " + query);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransaugust = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    Log.i("ufguiofg", "  jantotal " + amnt);
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + temp);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransaugust = UserInformation.totaltransaugust + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totalaugust " + UserInformation.totaltransaugust);
            return UserInformation.totaltransaugust;
        }

        else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Aug";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            Log.i("ufguiofg", "  jantotal " + query);
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransaugust = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    Log.i("ufguiofg", "  jantotal " + amnt);
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + temp);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransaugust = UserInformation.totaltransaugust + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totalaugust " + UserInformation.totaltransaugust);
            return UserInformation.totaltransaugust;

        }
    }

    public float gettransSept() {
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05") ||
                        currentMonthValue.contentEquals("06") ||
                        currentMonthValue.contentEquals("07") ||
                        currentMonthValue.contentEquals("08")
                ) {

            Log.i("calendar.getTime()", "" + year);
            String month = "Sep";
            year = String.valueOf(getPreviousYear());
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltranssep = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltranssep = UserInformation.totaltranssep + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totalsep " + UserInformation.totaltranssep);
            return UserInformation.totaltranssep;

        }

        else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Sep";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate ";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltranssep = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltranssep = UserInformation.totaltranssep + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totalsep " + UserInformation.totaltranssep);
            return UserInformation.totaltranssep;

        }

    }

    public float gettransOct() {
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05") ||
                        currentMonthValue.contentEquals("06") ||
                        currentMonthValue.contentEquals("07") ||
                        currentMonthValue.contentEquals("08") ||
                        currentMonthValue.contentEquals("09")
                ) {

            Log.i("calendar.getTime()", "" + year);
            String month = "Oct";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransoct = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransoct = UserInformation.totaltransoct + temp;
                }
                while (c.moveToNext());
            }
            Log.i("array", "  totaloct " + UserInformation.totaltransoct);
            return UserInformation.totaltransoct;

        } else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Oct";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransoct = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransoct = UserInformation.totaltransoct + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totaloct " + UserInformation.totaltransoct);
            return UserInformation.totaltransoct;
        }
    }

    public float gettransNov() {

        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05") ||
                        currentMonthValue.contentEquals("06") ||
                        currentMonthValue.contentEquals("07") ||
                        currentMonthValue.contentEquals("08") ||
                        currentMonthValue.contentEquals("09") ||
                        currentMonthValue.contentEquals("10")
                )

        {
            Log.i("calendar.getTime()", "" + year);
            String month = "Nov";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransnov = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                    UserInformation.totaltransnov = UserInformation.totaltransnov + temp;

                }
                while (c.moveToNext());
            }
            Log.i("array", "  totalnov " + UserInformation.totaltransnov);
            return UserInformation.totaltransnov;
        } else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Nov";
            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransnov = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("novtotal", "  novtotal " + amnt + ".." + temp);
                    UserInformation.totaltransnov = UserInformation.totaltransnov + temp;

                }
                while (c.moveToNext());
            }
            Log.i("novtotal", "  totalnov " + UserInformation.totaltransnov);
            return UserInformation.totaltransnov;
        }
    }

    public float gettransDec() {

        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");

        SimpleDateFormat currMonth = new SimpleDateFormat("MM");

        year = month_year1.format(calendar.getTime());

        currentMonthValue = currMonth.format(calendar.getTime());

        if (
                currentMonthValue.contentEquals("01") ||
                        currentMonthValue.contentEquals("02") ||
                        currentMonthValue.contentEquals("03") ||
                        currentMonthValue.contentEquals("04") ||
                        currentMonthValue.contentEquals("05") ||
                        currentMonthValue.contentEquals("06") ||
                        currentMonthValue.contentEquals("07") ||
                        currentMonthValue.contentEquals("08") ||
                        currentMonthValue.contentEquals("09") ||
                        currentMonthValue.contentEquals("10") ||
                        currentMonthValue.contentEquals("11")
                )

        {
            Log.i("calendar.getTime()", "" + year);
//            calendar.add(Calendar.DATE, -1);
            String month = "Dec";
            year = String.valueOf(getPreviousYear());

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransdec = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("dectotal", "  dectotal " + amnt + ".." + temp);
                    UserInformation.totaltransdec = UserInformation.totaltransdec + temp;
                }
                while (c.moveToNext());
            }
//            c.close();

            Log.i("array", "  totaldec " + UserInformation.totaltransdec);
            return UserInformation.totaltransdec;

        }
        else {
            Log.i("calendar.getTime()", "" + year);
            String month = "Dec";

            String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.'  AND type!='AVAILABLE BALANCE' ORDER BY changdate";
            SQLiteDatabase db = this.getWritableDatabase();
            Cursor c = db.rawQuery(query, null);
            UserInformation.totaltransdec = 0;
            if (c.moveToFirst()) {
                do {
                    String amnt = String.valueOf(c.getString(0));
                    float temp = Float.parseFloat(amnt);
                    Log.i("dectotal", "  dectotal " + amnt + ".." + temp);
                    UserInformation.totaltransdec = UserInformation.totaltransdec + temp;
                }
                while (c.moveToNext());
            }
//            c.close();

            Log.i("array", "  totaldec " + UserInformation.totaltransdec);
            return UserInformation.totaltransdec;

        }
    }

    public float gettransAug1() {
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Aug";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        Log.i("ufguiofg", "  jantotal " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransaugust = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                Log.i("ufguiofg", "  jantotal " + amnt);
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + temp);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransaugust = UserInformation.totaltransaugust + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalaugust " + UserInformation.totaltransaugust);
        return UserInformation.totaltransaugust;


    }

    public float gettransSept1() {
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Sep";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltranssep = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltranssep = UserInformation.totaltranssep + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalsep " + UserInformation.totaltranssep);
        return UserInformation.totaltranssep;


    }

    public float gettransOct1() {
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Oct";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransoct = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransoct = UserInformation.totaltransoct + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaloct " + UserInformation.totaltransoct);
        return UserInformation.totaltransoct;


    }

    public float gettransNov1() {
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Nov";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransnov = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransnov = UserInformation.totaltransnov + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalnov " + UserInformation.totaltransnov);
        return UserInformation.totaltransnov;


    }


//*************************bank wise transactions***********************************************//

    public float gettransDec1() {
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Dec";

        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransdec = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransdec = UserInformation.totaltransdec + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaldec " + UserInformation.totaltransdec);
        return UserInformation.totaltransdec;


    }

    public float gettransJanbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Jan";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransjanbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);

                UserInformation.totaltransjanbank = UserInformation.totaltransjanbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  jantotal " + UserInformation.totaltransjanbank);
        return UserInformation.totaltransjanbank;


    }

    public float gettransFebbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Feb";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE'  AND nameofbank = '" + key + "' ORDER BY changdate";
        Log.i("ufguiofg", "  jantotal " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransfebbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransfebbank = UserInformation.totaltransfebbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalfeb " + UserInformation.totaltransfebbank);
        return UserInformation.totaltransfebbank;


    }

    public float gettransMarchbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Mar";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransmar = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransmarbank = UserInformation.totaltransmarbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalmar " + UserInformation.totaltransmarbank);
        return UserInformation.totaltransmarbank;


    }

    public float gettransAprbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Apr";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransaprilbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransaprilbank = UserInformation.totaltransaprilbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalapril " + UserInformation.totaltransaprilbank);
        return UserInformation.totaltransaprilbank;


    }

    public float gettransMaybank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "May";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransmaybank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransmaybank = UserInformation.totaltransmaybank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  jantotal " + UserInformation.totaltransmaybank);
        return UserInformation.totaltransmaybank;


    }

    public float gettransJunebank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Jun";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransjunebank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransjunebank = UserInformation.totaltransjunebank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaljune " + UserInformation.totaltransjunebank);
        return UserInformation.totaltransjunebank;


    }

    public float gettransJulybank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Jul";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransjulybank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransjulybank = UserInformation.totaltransjulybank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaljuly " + UserInformation.totaltransjulybank);
        return UserInformation.totaltransjulybank;


    }

    public float gettransAugbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Aug";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        Log.i("ufguiofg", "  jantotal " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransaugustbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                Log.i("ufguiofg", "  jantotal " + amnt);
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + temp);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransaugustbank = UserInformation.totaltransaugustbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalaugust " + UserInformation.totaltransaugustbank);
        return UserInformation.totaltransaugustbank;


    }

    public float gettransSeptbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Sep";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltranssepbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltranssepbank = UserInformation.totaltranssepbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalsep " + UserInformation.totaltranssepbank);
        return UserInformation.totaltranssepbank;


    }

    public float gettransOctbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Oct";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransoctbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransoctbank = UserInformation.totaltransoctbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaloct " + UserInformation.totaltransoctbank);
        return UserInformation.totaltransoctbank;


    }

    public float gettransNovbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Nov";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransnovbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransnovbank = UserInformation.totaltransnovbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalnov " + UserInformation.totaltransnovbank);
        return UserInformation.totaltransnovbank;


    }

    public float gettransDecbank(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Dec";

        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' AND nameofbank = '" + key + "' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransdecbank = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransdecbank = UserInformation.totaltransdecbank + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaldec " + UserInformation.totaltransdecbank);
        return UserInformation.totaltransdecbank;


    }

    public void gettransCategoryweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT DISTINCT type FROM trans_msg WHERE amount > 0.0 And changdate LIKE '" + key + "' ORDER BY type";
        SQLiteDatabase sql = this.getWritableDatabase();
        List<String> lst = new ArrayList<String>();
        Cursor c = sql.rawQuery(query, null);

        /*
        * Foo[] array = new Foo[list.size()];
list.toArray(array);*/
        if (c.moveToFirst()) {
            do {
                lst.add(String.valueOf(c.getString(0)));
            }
            while (c.moveToNext());
        }
        String[] arr = new String[lst.size()];
        UserInformation.arr = new String[lst.size()];
        UserInformation.arr = lst.toArray(arr);
        for (String s : UserInformation.arr)
            Log.i("array", "arr" + s);


        //1return UserInformation.arr;
    }

    public float gettransFoodweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Food' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountfoodweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamountfoodweek = UserInformation.totalamountfoodweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountfoodweek;
    }

    public float gettransFuelweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Fuel' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountfuelweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                Log.i("amnt", "  jantotal " + amnt + ".." + temp);
                UserInformation.totalamountfuelweek = UserInformation.totalamountfuelweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountfuelweek;
    }

    public float gettransShoppingweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Shopping' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountshoppingweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamountshoppingweek = UserInformation.totalamountshoppingweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountshoppingweek;
    }

    public float gettransElectricityweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Bills' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountbillsweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamountbillsweek = UserInformation.totalamountbillsweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountbillsweek;
    }

    public float gettransGroceriesweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Groceries' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountgroceriesweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamountgroceriesweek = UserInformation.totalamountgroceriesweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountgroceriesweek;
    }

    public float gettransHealthweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Health' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamounthealthweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamounthealthweek = UserInformation.totalamounthealthweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamounthealthweek;
    }

    public float gettransTravelweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Travel' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamounttravelweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamounttravelweek = UserInformation.totalamounttravelweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamounttravelweek;
    }

    public float gettransOtherweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Other' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountotherweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamountotherweek = UserInformation.totalamountotherweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountotherweek;
    }

    public float gettransEntertainmentweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Entertainment' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountentertainmentweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                int temp = Integer.parseInt(amnt);
                UserInformation.totalamountentertainmentweek = UserInformation.totalamountentertainmentweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountentertainmentweek;
    }

    public float gettransextraweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'SPENT' And changdate LIKE '" + key + "' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransextraweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                Log.i("ukkkkk", "  jantotal " + amnt + ".." + temp);
                UserInformation.totaltransextraweek = UserInformation.totaltransextraweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totaltransextraweek;
    }

    public float gettransdebitweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'DEBITED.' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltransdebitweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totaltransdebitweek = UserInformation.totaltransdebitweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totaltransdebitweek;
    }

    public float gettranspurcweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'PURCHASE.' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltranspurchaseweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totaltranspurchaseweek = UserInformation.totaltranspurchaseweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totaltranspurchaseweek;
    }

    public float gettranscreditweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'CREDITED.' And changdate LIKE '" + key + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totaltranscreditweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totaltranscreditweek = UserInformation.totaltranscreditweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totaltranscreditweek;
    }


    //*************************tRansaction amount according to bank account number***************************///////

    public float gettransavailableweek(String aa) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'AVAILABLE BALANCE' And changdate LIKE '" + key + "' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalavailableweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalavailableweek = UserInformation.totalavailableweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalavailableweek;
    }

    public float gettransJanacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Jan";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank= '" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.janacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);

                UserInformation.janacno = UserInformation.janacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  jantotal " + UserInformation.janacno);
        return UserInformation.janacno;
    }

    public float gettransFebacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Feb";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        Log.i("ufguiofg", "  jantotal " + query);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.febacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.febacno = UserInformation.febacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalfeb " + UserInformation.febacno);
        return UserInformation.febacno;


    }

    public float gettransMarchacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Mar";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.maracno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.maracno = UserInformation.maracno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalmar " + UserInformation.maracno);
        return UserInformation.maracno;


    }

    public float gettransApracno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Apr";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.apracno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.apracno = UserInformation.apracno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalapril " + UserInformation.apracno);
        return UserInformation.apracno;


    }

    public float gettransMayacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "May";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.mayacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.mayacno = UserInformation.mayacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  jantotal " + UserInformation.mayacno);
        return UserInformation.mayacno;

    }

    public float gettransJuneacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Jun";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.junacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.junacno = UserInformation.junacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaljune " + UserInformation.junacno);
        return UserInformation.junacno;


    }

    public float gettransJulyacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Jul";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.junacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.junacno = UserInformation.junacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaljuly " + UserInformation.junacno);
        return UserInformation.junacno;


    }

    public float gettransAugacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Aug";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        Log.i("ufguiofg", "  jantotal " + query);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.augacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                Log.i("ufguiofg", "  jantotal " + amnt);
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + temp);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.augacno = UserInformation.augacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalaugust " + UserInformation.augacno);
        return UserInformation.augacno;


    }

    public float gettransSeptacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Sep";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.sepacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.sepacno = UserInformation.sepacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalsep " + UserInformation.sepacno);
        return UserInformation.sepacno;

    }

    public float gettransOctacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Oct";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.octacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.octacno = UserInformation.octacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaloct " + UserInformation.octacno);
        return UserInformation.octacno;


    }

    public float gettransNovacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Nov";
        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'  AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.novacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.novacno = UserInformation.novacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totalnov " + UserInformation.novacno);
        return UserInformation.novacno;

    }

    //pie chart data according to bank name and bank number

    public float gettransDecacno(String acno, String acnm) {
        ano = acno;
        anm = acnm;

        calendar = Calendar.getInstance();
        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());

        Log.i("calendar.getTime()", "" + year);
        String month = "Dec";

        String query = "SELECT amount FROM trans_msg WHERE changdate LIKE '%-" + month + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE' ORDER BY changdate";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.decacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(0));
                float temp = Float.parseFloat(amnt);
                Log.i("ufguiofg", "  jantotal " + amnt + ".." + temp);
                UserInformation.decacno = UserInformation.decacno + temp;

            }
            while (c.moveToNext());
        }
        Log.i("array", "  totaldec " + UserInformation.decacno);
        return UserInformation.decacno;


    }

    public void gettransCategoryacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT DISTINCT type FROM trans_msg WHERE amount > 0.0 And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' ORDER BY type";
        SQLiteDatabase sql = this.getWritableDatabase();
        List<String> lst = new ArrayList<String>();
        Cursor c = sql.rawQuery(query, null);

        /*
        * Foo[] array = new Foo[list.size()];
list.toArray(array);*/
        if (c.moveToFirst()) {
            do {
                lst.add(String.valueOf(c.getString(0)));
            }
            while (c.moveToNext());
        }
        String[] arr = new String[lst.size()];
        UserInformation.arracno = new String[lst.size()];
        UserInformation.arracno = lst.toArray(arr);
        for (String s : UserInformation.arracno)
            Log.i("array", "arr" + s);


        //1return UserInformation.arr;
    }

    public float gettransFoodacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Food' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.foodacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.foodacno = UserInformation.foodacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.foodacno;
    }

    public float gettransFuelacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Fuel' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.fuelacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                Log.i("amnt", "  jantotal " + amnt + ".." + temp);
                UserInformation.fuelacno = UserInformation.fuelacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.fuelacno;
    }

    public float gettransShoppingacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Shopping' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.shoppingacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.shoppingacno = UserInformation.shoppingacno + temp;
            }
            while (c.moveToNext());
        }
//        c.close();
        return UserInformation.shoppingacno;
    }

    public float gettransElectricityacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Bills' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.billacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.billacno = UserInformation.billacno + temp;
            }
            while (c.moveToNext());
        }
//        c.close();
        return UserInformation.billacno;
    }

    public float gettransGroceriesacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Groceries' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.groacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.groacno = UserInformation.groacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.groacno;
    }

    public float gettransHealthacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Health' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.healthacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.healthacno = UserInformation.healthacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.healthacno;
    }

    public float gettransTravelacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Travel' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.travelacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.travelacno = UserInformation.travelacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.travelacno;
    }

    public float gettransOtheracno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Other' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.otheracno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.otheracno = UserInformation.otheracno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.otheracno;
    }

    public float gettransEntertainmentacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Entertainment' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.enteracno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                int temp = Integer.parseInt(amnt);
                UserInformation.enteracno = UserInformation.enteracno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.enteracno;
    }

    public float gettransextraacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'SPENT' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.extraacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                Log.i("ukkkkk", "  jantotal " + amnt + ".." + temp);
                UserInformation.extraacno = UserInformation.extraacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.extraacno;
    }

    public float gettransdebitacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'DEBITED.' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.debitacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.debitacno = UserInformation.debitacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.debitacno;
    }

    public float gettranspurcacno(String aa, String bb, String cc) {
        key = aa;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'PURCHASE.' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.purchaseacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.purchaseacno = UserInformation.purchaseacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.purchaseacno;
    }

    public float gettranscreditacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'CREDITED.' And changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.creditacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.creditacno = UserInformation.creditacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.creditacno;
    }

    public float gettransavailableacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'AVAILABLE BALANCE' AND changdate LIKE '%-" + key + "-" + year + "' AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.availacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.availacno = UserInformation.availacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.availacno;
    }

    //********************************weekly pie cahrt functions according to account number and name*********************//
    public void gettransCategoryweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT DISTINCT type FROM trans_msg WHERE amount > 0.0 And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' ORDER BY type";
        SQLiteDatabase sql = this.getWritableDatabase();
        List<String> lst = new ArrayList<String>();
        Cursor c = sql.rawQuery(query, null);

        /*
        * Foo[] array = new Foo[list.size()];
list.toArray(array);*/
        if (c.moveToFirst()) {
            do {
                lst.add(String.valueOf(c.getString(0)));
            }
            while (c.moveToNext());
        }
        String[] arr = new String[lst.size()];
        UserInformation.arrwacno = new String[lst.size()];
        UserInformation.arrwacno = lst.toArray(arr);
        for (String s : UserInformation.arrwacno)
            Log.i("array", "arr" + s);


        //1return UserInformation.arr;
    }

    public float gettransFoodweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Food' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.totalamountfoodweek = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.totalamountfoodweek = UserInformation.totalamountfoodweek + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.totalamountfoodweek;
    }

    public float gettransFuelweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Fuel' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.fuelweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                Log.i("amnt", "  jantotal " + amnt + ".." + temp);
                UserInformation.fuelweekacno = UserInformation.fuelweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.fuelweekacno;
    }

    public float gettransShoppingweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Shopping' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.shoppingweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.shoppingweekacno = UserInformation.shoppingweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.shoppingweekacno;
    }

    public float gettransElectricityweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Bills' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.billweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.billweekacno = UserInformation.billweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.billweekacno;
    }

    public float gettransGroceriesweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Groceries' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.groweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.groweekacno = UserInformation.groweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.groweekacno;
    }

    public float gettransHealthweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Health' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.healthweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.healthweekacno = UserInformation.healthweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.healthweekacno;
    }

    public float gettransTravelweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Travel' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.travelweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.travelweekacno = UserInformation.travelweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.travelweekacno;
    }

    public float gettransOtherweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Other' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.otherweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.otherweekacno = UserInformation.otherweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.otherweekacno;
    }

    public float gettransEntertainmentweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'Entertainment' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.enterweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                int temp = Integer.parseInt(amnt);
                UserInformation.enterweekacno = UserInformation.enterweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.enterweekacno;
    }

    public float gettransextraweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'SPENT' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.extraweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                Log.i("ukkkkk", "  jantotal " + amnt + ".." + temp);
                UserInformation.extraweekacno = UserInformation.extraweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.extraweekacno;
    }

    public float gettransdebitweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'DEBITED.' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.debitweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.debitweekacno = UserInformation.debitweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.debitweekacno;
    }

    public float gettranspurcweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'PURCHASE.' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.purchaseweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.purchaseweekacno = UserInformation.purchaseweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.purchaseweekacno;
    }

    public float gettranscreditweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();


        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'CREDITED.' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.creditweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.creditweekacno = UserInformation.creditweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.creditweekacno;
    }

    public float gettransavailableweekacno(String aa, String bb, String cc) {
        key = aa;
        ano = bb;
        anm = cc;
        calendar = Calendar.getInstance();

        SimpleDateFormat month_year1 = new SimpleDateFormat("yyyy");
        year = month_year1.format(calendar.getTime());
        String query = "SELECT * FROM trans_msg WHERE type = 'AVAILABLE BALANCE' And changdate LIKE '" + key + "'  AND nameofbank='" + anm + "' AND accuntnumber='" + ano + "' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        UserInformation.availweekacno = 0;
        if (c.moveToFirst()) {
            do {
                String amnt = String.valueOf(c.getString(10));
                float temp = Float.parseFloat(amnt);
                UserInformation.availweekacno = UserInformation.availweekacno + temp;
            }
            while (c.moveToNext());
        }
        return UserInformation.availweekacno;
    }


}