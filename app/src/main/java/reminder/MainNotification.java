package reminder;


import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bounside.mj.cashiya.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.LazyAdapter;
import sqlitedatabase.Datab_notify;


/**
 * Created by Neeraj Sain on 7/20/2016.
 */
public class MainNotification extends AppCompatActivity implements  AdapterView.OnItemClickListener{

    ListView lv;
    List<Map> lst = new ArrayList<>();
    public Calendar calendar;
    String todate,totime;
    SQLiteDatabase sql;
    Cursor mCursor;
    Datab_notify dn ;
    String dir,dir1,dir2;
    Map<String, String> hmap;
    LazyAdapter adapter;
    ProgressDialog dialog;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_notification);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setTitle("Notifications");
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
        }

        dn=new Datab_notify(this);
        sql = dn.getReadableDatabase();

        calendar = Calendar.getInstance();
        lv = (ListView) findViewById(R.id.remindlist);

        adapter = new LazyAdapter(MainNotification.this, R.layout.custom_row, lst);
        lv.setAdapter(adapter);

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        MainNotification.this);
                builder.setTitle("Delete");
                builder.setMessage("Are you sure?");

                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                //  new deleteList().execute(dir);
                                new delete().execute();
                                lv.invalidateViews();
                                // adapter.notifyDataSetChanged();
                                adapter.remove(adapter.getItem(position));

                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.dismiss();
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });
        //  new getPrivateList().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();

        getAllRecords();


    }

    private String getNotificationDate() {
        String aa = "";
        mCursor = sql.rawQuery("SELECT * FROM notify", null);

        if(mCursor.moveToFirst())
        {
            do {
                aa = mCursor.getString(mCursor.getColumnIndex("datenotify"));
            }while (mCursor.moveToNext());
        }
//        mCursor.close();
        return aa;
    }

    public void getAllRecords() {

        calendar=Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyy");
        todate = sdf.format(calendar.getTime());
        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        totime = sdf1.format(calendar.getTime());

//        lst.clear();
        mCursor = sql.rawQuery("SELECT * FROM notify ORDER BY monthdata DESC, datenotify DESC", null);
        if (mCursor != null ) {
            if  (mCursor.moveToFirst()) {
                do {
                    hmap = new HashMap<String, String>();
                    dir = mCursor.getString(mCursor.getColumnIndex("datenotify"));
                    dir1 = mCursor.getString(mCursor.getColumnIndex("timenotify"));
                    dir2 = mCursor.getString(mCursor.getColumnIndex("message"));

                    Log.i("dateTimeMoney",""+dir + "  " +dir1+"  "+dir2);

                    hmap.put("title", dir);
                    hmap.put("time", dir1);
                    hmap.put("dmoney", dir2);
                    hmap.put("cat", "Notify");
                    lst.add(hmap);
                    Log.i("broading", "lst " + lst);

                }while (mCursor.moveToNext());
            }
//            mCursor.close();
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
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    private class delete extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(MainNotification.this);
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            dn.deleteUser(getNotificationDate());
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
        }
    }
}