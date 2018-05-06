package bank;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bounside.mj.cashiya.R;

import java.util.ArrayList;

/**
 * Created by Neeraj Sain on 11/8/2016.
 */

public class BillsData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bills_data);
        fetchInboxSms();
    }

    public ArrayList<Message> fetchInboxSms() {
        ArrayList<Message> smsInbox = new ArrayList<Message>();

        Uri uriSms = Uri.parse("content://sms");

        Cursor cursor = this.getContentResolver()
                .query(uriSms,
                        new String[] { "_id", "address", "date", "body",
                                "type", "read" }, null, null,null,null);

        Uri myMessage = Uri.parse("content://sms");
        ContentResolver cr = BillsData.this.getContentResolver();
        Cursor c1 = cr.query(myMessage, new String[]{"_id", "address", "date",
                "body"}, null, null, null);
        startManagingCursor(c1);
        if (cursor != null) {
            cursor.moveToLast();
            if (cursor.getCount() > 0) {

                do {


                    String Number = cursor.getString(cursor
                            .getColumnIndex("address"));
                    String Body = cursor.getString(cursor
                            .getColumnIndex("body"));
                    //smsInbox.add(message);
                    Log.i("vfgyh",""+Body);
                } while (cursor.moveToPrevious());
            }
//            cursor.close();
        }

        return smsInbox;

    }
}
