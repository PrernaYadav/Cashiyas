package dynamicviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import sqlitedatabase.Bankmessagedb;
import sqlitedatabase.ExpenseData;

/**
 * Created by JKB-2 on 4/25/2016.
 *
 *
 */
public class SavingRate extends AppCompatActivity {
    String month[]={"Jan","Feb","Mar","Apr","may","jun","jul","Aug","Sep","Oct","Nov","Dec"};
    private BarChart barChartt;
    BarDataSet dataset1,dataset2;
    ExpenseData exp = new ExpenseData(SavingRate.this);
    Bankmessagedb bbd = new Bankmessagedb(SavingRate.this);
    ArrayList<String> labels;
    ArrayList<Float> labels1;
    ArrayList<Float> labels2;
    ListView lv;
    ListAdapter listAdapter;
    ArrayList<HashMap<String,String >> hmap=new ArrayList<>();
    int i;

    String getmonth,backmonths;
    String months;
    String m4,m5;
    String m, m44, m55;
    String[] barzoom=new String[3];
    String[] barzoom1=new String[3];
    Float[] barzoomdata=new Float[3];
    Float[] barzoomdata1=new Float[3];
    int start1,start2,start3;
    Calendar cal;
    LinearLayout linearnorecord,linearrecord;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.savings);
        ActionBar ac = getSupportActionBar();
        ac.setHomeButtonEnabled(true);
        ac.setDisplayHomeAsUpEnabled(true);
        lv = (ListView) findViewById(R.id.savings);
        barChartt = (BarChart) findViewById(R.id.charttarget);
        linearrecord = (LinearLayout) findViewById(R.id.savinglinear);
        linearnorecord = (LinearLayout) findViewById(R.id.savinglinear1);

        //*******************count down the month*************************//

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        Date date = new Date();
        getmonth = dateFormat.format(date);
        months = (String) android.text.format.DateFormat.format("MMM", date);
        m = (String) android.text.format.DateFormat.format("M", date);
        barzoom[2] = months;
        barzoom1[2] = m;


        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date todate5 = cal.getTime();
        m5 = (String) android.text.format.DateFormat.format("MMM", todate5);
        m55 = (String) android.text.format.DateFormat.format("M", todate5);
        barzoom[1] = m5;
        barzoom1[1] = m55;
        backmonths = dateFormat.format(todate5);

        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -2);
        Date todate4 = cal.getTime();
        m4 = (String) android.text.format.DateFormat.format("MMM", todate4);
        m44 = (String) android.text.format.DateFormat.format("M", todate4);
        barzoom[0] = m4;
        barzoom1[0] = m44;

        start1=Integer.parseInt(barzoom1[0]);
        start2=Integer.parseInt(barzoom1[1]);
        start3=Integer.parseInt(barzoom1[2]);

        //********************************get income of all months*******************************//
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

        //************************get expense of all months************************************//
        bbd.gettransJan();
        bbd.gettransFeb();
        bbd.gettransMarch();
        bbd.gettransApr();
        bbd.gettransMay();
        bbd.gettransJune();
        bbd.gettransJuly();
        bbd.gettransAug();
        bbd.gettransSept();
        bbd.gettransOct();
        bbd.gettransNov();
        bbd.gettransDec();

        //************************calculate income vs expenses******************************//
        calulateSavings();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void calulateSavings() {
        //**********************************income data*************************************//
        ArrayList<IBarDataSet> dataSets = null;
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


        for(int k = 0; k<3;k++)
        {
            barzoomdata[0] = nmbers1[start1-1];
            barzoomdata[1] = nmbers1[start2-1];
            barzoomdata[2] = nmbers1[start3-1];
        }

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            entries.add(new BarEntry(barzoomdata[i], i));
        }

        dataset1 = new BarDataSet(entries, "Income");
        dataset1.setColor(Color.rgb(255,112,67));

        //*************************************expense data*******************************//
        float[] nmbers2={UserInformation.totaltransjan,
                UserInformation.totaltransfeb,
                UserInformation.totaltransmar,
                UserInformation.totaltransapril,
                UserInformation.totaltransmay,
                UserInformation.totaltransjune,
                UserInformation.totaltransjuly,
                UserInformation.totaltransaugust,
                UserInformation.totaltranssep,
                UserInformation.totaltransoct,
                UserInformation.totaltransnov,
                UserInformation.totaltransdec};

        for (i = 0; i < nmbers2.length; i++) {
        }

        for(int k = 0; k<3;k++)
        {
            barzoomdata1[0] = nmbers2[start1-1];
            barzoomdata1[1] = nmbers2[start2-1];
            barzoomdata1[2] = nmbers2[start3-1];
        }

        ArrayList<BarEntry> entries1 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            entries1.add(new BarEntry(barzoomdata1[i], i));
        }

        dataset2 = new BarDataSet(entries1, "Expense");
        dataset2.setColor(Color.WHITE);

        //**************************months data************************************//

        labels = new ArrayList<String>();
        for (int i = 0; i < 3; i++) {
            labels.add(barzoom[i]);
        }


        labels1 = new ArrayList<Float>();
        for (int i = 0; i < 3; i++) {
            labels1.add(barzoomdata[i]);
        }
        labels2 = new ArrayList<Float>();
        for (int i = 0; i < 3; i++) {
            labels2.add(barzoomdata1[i]);
        }

        List lLists = Arrays.asList(month);
        Log.i("lLists","list"+lLists);

        List lLists1 = Arrays.asList(nmbers1);
        Log.i("lLists","list1"+lLists1);

        List lLists2 = Arrays.asList(nmbers2);
        Log.i("lLists","list2"+lLists2);

        //************************data set adds income and expense data for each month*******************//
        dataSets = new ArrayList<>();
        dataSets.add(dataset1);
        dataSets.add(dataset2);

        Log.i("broading", " dataSets " + dataSets);

        //bar data includes data and labels
        BarData data1 = new BarData(labels, dataSets);

        Log.i("broading", " data1 " + data1);

        //******************************set all bar data on barchartt********************************//
        barChartt.setData(data1);
        barChartt.animateXY(2000, 2000);
        barChartt.getAxisLeft().setDrawGridLines(false);

        barChartt.getXAxis().setDrawGridLines(false);

        barChartt.setMaxVisibleValueCount(5);
        barChartt.getData().setHighlightEnabled(true);
//        RefreshChart();

        barChartt.invalidate();


        HashMap<String, String> map;

        for (int i=0;  i<3;  i++) {
            map = new HashMap<String, String>();
            String s = labels.get(i);
            Log.i("s", " yz " + s);
            map.put("labels", s);
            Float s1 = labels1.get(i);
            Log.i("s1", " yz " + s1);

            String str = String.valueOf(s1);
            Log.i("str", " yz " + str);
            str = "Income"+"-"+str;

            map.put("entries", str);
            Float s2 = labels2.get(i);
            Log.i("s2", " yz " + s2);
            String str1 = String.valueOf(s2);
            str1 = "Expense"+"-"+str1;
            Log.i("str1", " yz " + str1);
            map.put("entries1", str1);
            hmap.add(map);
        }

        if(hmap.isEmpty())
        {
            linearnorecord.setVisibility(View.VISIBLE);
            linearrecord.setVisibility(View.GONE);
        }
        else{
            linearnorecord.setVisibility(View.GONE);
            linearrecord.setVisibility(View.VISIBLE);
            String[] from = {"labels","entries","entries1"};
            int[] to = {R.id.month, R.id.income, R.id.expense};
            listAdapter = new SimpleAdapter(SavingRate.this, hmap, R.layout.custom_row1, from, to);
            lv.setAdapter(listAdapter);
        }
    }
}

