package dynamicviewpager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Information;
import adapter.LazyAdapter;
import sqlitedatabase.ExpenseData;

/**
 * Created by JKB-2 on 4/22/2016.
 */
public class MyIncome extends AppCompatActivity {

    ListView listView;
    Map<String, String> hmap;
    List<Map> lst = new ArrayList<>();
    String title;
    String selectQuery;
    String cat;
    String time;
    String dmoney;
    int spnds = 0;
    String datemin = "";
    String dateMax = "";
    int id = R.mipmap.atm;

    TextView Income;
    private BarChart barCharti;
    float percb[];
    float[] nmbers1;
    String month[]={"Jan","Feb","Mar","Apr","may","jun","jul","Aug","Sep","Oct","Nov","Dec"};

    String getmonth,backmonths;
    String months;
    String m1,m2,m3,m4,m5;
    String m,m11,m22,m33,m44,m55;
    String[] barzoom=new String[6];
    String[] barzoom1=new String[6];
    Float[] barzoomdata=new Float[6];
    int start1,start2,start3,start4,start5,start6;
    Calendar cal;
    LinearLayout linearnorecord,linearrecord;

    ExpenseData exp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_income);
        exp = new ExpenseData(MyIncome.this);
        listView = (ListView) findViewById(R.id.listincome);
        Income = (TextView) findViewById(R.id.textincome);
        barCharti = (BarChart) findViewById(R.id.chartincome);
        linearrecord = (LinearLayout) findViewById(R.id.incomelinear1);
        linearnorecord = (LinearLayout) findViewById(R.id.incomelinear);


        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = new Date();
        getmonth = dateFormat.format(date);
        months = (String) android.text.format.DateFormat.format("MMM", date);
        m = (String) android.text.format.DateFormat.format("M", date);
        barzoom[5] = months;
        barzoom1[5] = m;


        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date todate5 = cal.getTime();
        m5 = (String) android.text.format.DateFormat.format("MMM", todate5);
        m55 = (String) android.text.format.DateFormat.format("M", todate5);
        barzoom[4] = m5;
        barzoom1[4] = m55;
        backmonths = dateFormat.format(todate5);

        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        Date todate4 = cal.getTime();
        m4 = (String) android.text.format.DateFormat.format("MMM", todate4);
        m44 = (String) android.text.format.DateFormat.format("M", todate4);
        barzoom[3] = m4;
        barzoom1[3] = m44;

        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date todate3 = cal.getTime();
        m3 = (String) android.text.format.DateFormat.format("MMM", todate3);
        m33 = (String) android.text.format.DateFormat.format("M", todate3);
        barzoom[2] = m3;
        barzoom1[2] = m33;

        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -4);
        Date todate2 = cal.getTime();
        m2 = (String) android.text.format.DateFormat.format("MMM", todate2);
        m22 = (String) android.text.format.DateFormat.format("M", todate2);
        barzoom[1] = m2;
        barzoom1[1] = m22;

        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -5);
        Date todate1 = cal.getTime();
        m1 = (String) android.text.format.DateFormat.format("MMM", todate1);
        m11 = (String) android.text.format.DateFormat.format("M", todate1);
        barzoom[0] = m1;
        barzoom1[0] = m11;

        start1=Integer.parseInt(barzoom1[0]);
        start2=Integer.parseInt(barzoom1[1]);
        start3=Integer.parseInt(barzoom1[2]);
        start4=Integer.parseInt(barzoom1[3]);
        start5=Integer.parseInt(barzoom1[4]);
        start6=Integer.parseInt(barzoom1[5]);


        //get income of all months
        exp.getincomejan();
        exp.getincomefeb();
        exp.getincomemarch();
        exp.getincomeapr();
        exp.getincomemay();
        exp.getincomejune();
        exp.getincomejuly();
        exp.getincomeaug();
        exp.getincomesep();
        exp.getincomeoct();
        exp.getincomenov();
        exp.getincomedec();

        //calulate income perc of months
        calulateincome();

        //get records of added income
        getAllRecords();

        //set data in listview
        if(lst.isEmpty()){
            linearnorecord.setVisibility(View.VISIBLE);
            linearrecord.setVisibility(View.GONE);
        }
        else {
            linearnorecord.setVisibility(View.GONE);
            linearrecord.setVisibility(View.VISIBLE);
            LazyAdapter adapter = new LazyAdapter(MyIncome.this, R.layout.custom_row, lst);
            listView.setAdapter(adapter);
        }
        //set data in chart
    }

    private void calulateincome() {

        float[] nmbers1 = {UserInformation.incomejan,
                UserInformation.incmoefeb,
                UserInformation.incomemarch,
                UserInformation.incomeapr,
                UserInformation.incomemay,
                UserInformation.incomejune,
                UserInformation.incomejuly,
                UserInformation.incomeaug,
                UserInformation.incomesep,
                UserInformation.incomeoct,
                UserInformation.incomenov,
                UserInformation.incomedec};
        Log.i("broading", " incomejan  " + UserInformation.incomejan
                + " incmoefeb " + UserInformation.incmoefeb
                + " incomemarch" + UserInformation.incomemarch
                + " incomeapr " + UserInformation.incomeapr
                + " incomemay " + UserInformation.incomemay
                + " incomejune " + UserInformation.incomejune
                + " incomejuly " + UserInformation.incomejuly
                + " incomeaug " + UserInformation.incomeaug
                + " incomesep " + UserInformation.incomesep
                + " incomeoct " + UserInformation.incomeoct
                + " incomenov " + UserInformation.incomenov
                + " incomedec " + UserInformation.incomedec
        );


        float total1=0;
        percb = new float[nmbers1.length];
        for (float aNmbers1 : nmbers1) {
            Log.i("broading", " percb " + aNmbers1);
        }

        for(int k = 0; k<6;k++)
        {
            barzoomdata[0] = nmbers1[start1-1];
            barzoomdata[1] = nmbers1[start2-1];
            barzoomdata[2] = nmbers1[start3-1];
            barzoomdata[3] = nmbers1[start4-1];
            barzoomdata[4] = nmbers1[start5-1];
            barzoomdata[5] = nmbers1[start6-1];


        }
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            entries.add(new BarEntry(barzoomdata[i], i));
        }

        BarDataSet dataset1 = new BarDataSet(entries, "Income");
        dataset1.setBarSpacePercent(50f);

        ArrayList<String> labels1 = new ArrayList<String>();
        labels1.addAll(Arrays.asList(barzoom).subList(0, 6));
        BarData data1 = new BarData(labels1, dataset1);
        dataset1.setColors(new int[]{Color.WHITE});
        barCharti.setData(data1);
        barCharti.setDescription("");
        barCharti.animateXY(2000, 2000);
        barCharti.getAxisLeft().setDrawGridLines(false);
        barCharti.getXAxis().setDrawGridLines(false);
        barCharti.invalidate();
    }

    public void getAllRecords() {

        selectQuery = "SELECT * FROM income ORDER BY yrincome desc,monthinc desc,incomedate desc";

        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                hmap = new HashMap<String, String>();
                title = String.valueOf(cursor.getString(3));
                Log.i("broading", "inside list getALLRecords" + title);
                time = String.valueOf(cursor.getString(2));
                dmoney = String.valueOf(cursor.getString(1));
                Log.i("broading", "inside list getALLRecords" + cat);

                hmap.put("title", title);
                hmap.put("time", time);
                hmap.put("dmoney", dmoney);
                hmap.put("cat", cat);
                lst.add(hmap);
                Log.i("broading", "lst " + lst);
            }
            while (cursor.moveToNext());

        }
//        cursor.close();

    }

}
