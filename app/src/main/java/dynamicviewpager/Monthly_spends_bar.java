package dynamicviewpager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bounside.mj.cashiya.AddCategory;
import com.bounside.mj.cashiya.DividerItemDecoration;
import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;
import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import adapter.Movie;
import adapter.MoviesAdapter;
import recyclercustomlistener.RecyclerItemClickListener;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 8/8/2016.
 */
public class Monthly_spends_bar extends Fragment implements OnChartValueSelectedListener {
    List<String> list;

    private BarChart barChart;
    int lengthofarray;
    String month[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
    float perc1[],percb[],percb1[];

    LinkedHashMap<String, String> hmap;
    Calendar cal;

    String getmonth,backmonths;
    String months;
    String m1,m2,m3,m4,m5;
    String m,m11,m22,m33,m44,m55;
    String[] barzoom=new String[6];
    String[] barzoom1=new String[6];
    Float[] barzoomdata=new Float[6];
    int start1,start2,start3,start4,start5,start6;
    ArrayList<String> xVals;

    LinearLayout linearnorecord,linearrecord;

    //recycler view
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String dbmoney;
    private List<Movie> movieList;
    int id = R.mipmap.ic_launcher;

    ArrayList<String> labels1;

    SQLiteDatabase sql;
    ContentValues can;
    Bankmessagedb bbd ;
    Cursor cursor;
    Movie movie;

    ArrayList<Integer> colors = new ArrayList<Integer>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.month_spend_bar, container, false);

        linearrecord = (LinearLayout) rootView.findViewById(R.id.monthlinear);
        linearnorecord = (LinearLayout) rootView.findViewById(R.id.monthlinear1);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_viewtransa);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        barChart = (BarChart) rootView.findViewById(R.id.chartbar);
        bbd = new Bankmessagedb(getActivity());
        sql = bbd.getWritableDatabase();
        can = new ContentValues();

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

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());



        list = new ArrayList<String>(UserInformation.set);

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

        getPositionlabel();
        calculatePercentage1();
        getAllRecords();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //fetching item by position
                        try {
                            Movie movie = movieList.get(position);

                            //   HashMap<String,String> map =(HashMap<String,String>)recyclerView.getAdapter(position-1);
                            Intent intent = new Intent(getContext(), AddCategory.class);
                            intent.putExtra("account",movie.getTitle());
                            intent.putExtra("date", movie.getYear());
                            intent.putExtra("amount", movie.getGenre());
                            intent.putExtra("cat", movie.geticon());
                            intent.putExtra("type", movie.getType());
                            startActivity(intent);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    }
                })
        );

        barChart.setOnChartValueSelectedListener(this);

        setData(lengthofarray, 100);

        //  }
        return rootView;
    }

    private void getPositionlabel() {
        int pos = 0;
        Log.i("month.length","...."+month.length);
        Log.i("months","...."+months);
        for (int i = 0; i < month.length; i++) {
            Log.i("postrfrtf","...."+month[i]);
            if(months.equals(month[i]))
            {
                pos = i;
                Log.i("posyertydf","...."+pos);
            }
        }
    }

    private void getAllRecords() {
        movieList = new ArrayList<>();

        cursor = sql.rawQuery("SELECT * FROM(SELECT * FROM trans_msg WHERE type='AVAILABLE BALANCE' ORDER BY yrdata desc,mnthdate desc,changdate desc,msgtime desc LIMIT 1) UNION ALL SELECT * FROM(SELECT * FROM trans_msg WHERE  type!='AVAILABLE BALANCE' ORDER BY yrdata desc,mnthdate desc,changdate desc,msgtime desc)", null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
//                    String aa = cursor.getString(cursor.getColumnIndex("msgid"));
                    String bb = cursor.getString(cursor.getColumnIndex("nameofbank"));
                    String cc = cursor.getString(cursor.getColumnIndex("body"));
                    String dd = cursor.getString(cursor.getColumnIndex("transdate"));
                    String ee= cursor.getString(cursor.getColumnIndex("category"));
                    String ff= cursor.getString(cursor.getColumnIndex("type"));
                    String gg= cursor.getString(cursor.getColumnIndex("changdate"));
                    String hh= cursor.getString(cursor.getColumnIndex("amount"));


                    id = gettypecat(ff);

                    movie = new Movie(bb, hh, dd, id, ff);
                    Log.i("movie",""+movie );
                    movieList.add(movie);
                    mAdapter = new MoviesAdapter(movieList);
                    Log.i("movieList",""+mAdapter );
                    recyclerView.setAdapter(mAdapter);
                    Log.i("recyclerView",""+recyclerView );
                    mAdapter.notifyDataSetChanged();

                }while (cursor.moveToNext());
            }
            else
            {
                linearnorecord.setVisibility(View.VISIBLE);
                linearrecord.setVisibility(View.GONE);
            }
        }
    }

    private int gettypecat(String ff) {
        String a = ff;
        Log.i("guikk",""+a );
        switch (a) {
            case "Bills":
                id = R.mipmap.bill_aftr;
                break;

            case "Food":
                id = R.mipmap.food_aftr;
                break;

            case "Fuel":
                id = R.mipmap.fuel_aftr;
                break;

            case "Groceries":
                id = R.mipmap.grocery_aftr;
                break;

            case "Health":
                id = R.mipmap.health_aftr;
                break;

            case "Shopping":
                id = R.mipmap.shoping_aftr;
                break;

            case "Travel":
                id = R.mipmap.travel_aftr;
                break;

            case "Other":
                id = R.mipmap.other_aftr;
                break;

            case "Entertainment":
                id = R.mipmap.entertainment_aftr;
                break;

            case "PURCHASE.":
                id = R.mipmap.purchase_aftr;
                break;

            case "EXPENSE":
                id = R.mipmap.extra_aftr;
                break;

            case "DEBITED.":
                id = R.mipmap.debited_aftr;
                break;

            default:
                id = R.mipmap.atm;
                break;
        }
        return id;
    }

    private void calculatePercentage1() {
        BarData data1;
        float[] nmbers1={
                UserInformation.totaltransjan,
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
                UserInformation.totaltransdec
        };

        float total1=0;
        percb = new float[nmbers1.length];
        percb1 = new float[nmbers1.length];
        for (float a : nmbers1)
            total1 = total1 + a;
        int j=0;
        for (int i = 0; i < nmbers1.length; i++) {
            percb[i] = (nmbers1[i] * 100) / total1;
            if (percb[i] > 0) {
                percb1[j] = percb[i];
                Log.i("broading", " percb1 " + percb1[j]);
                ++j;
            }
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

        BarDataSet dataset1 = new BarDataSet(entries, "");
        dataset1.setBarSpacePercent(50f);
        dataset1.setColors(colors);
        labels1 = new ArrayList<String>();
        labels1.addAll(Arrays.asList(barzoom).subList(0, 6));
        data1 = new BarData(labels1, dataset1);

        Log.i("datasets", " datataa " + data1);

        barChart.setData(data1);

        Log.i("datasets", " datataa " + barChart);
        barChart.setDescription("");
        barChart.animateXY(2000, 2000);


        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        Float min =  barChart.getYMin();
        Float max = barChart.getYMax();
        Log.i("minmax",""+min +"........"+max);

        barChart.invalidate();

    }

    private void setData(int count, float range) {


        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            Log.i("broading", " length count  " + count);
            yVals1.add(new Entry(perc1[i], i));
            Log.i("broading", " perc2 " + perc1[i]);
            Log.i("broading", " yVals " + yVals1.size());
        }

        xVals = new ArrayList<String>();

        for (int i = 0; i < count; i++)
            xVals.add(UserInformation.arr[i]);
        Log.i("broading", " xVals " + xVals.size());
        PieDataSet dataSet = new PieDataSet(yVals1, " ");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        //chart data
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i <= count; i++) {
            entries.add(new BarEntry(percb[i], i));

        }


        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i <= count; i++) {
            labels.add(month[i]);
        }

        dataSet.setColors(colors);
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        int  entry = e.getXIndex();

        String category =labels1.get(entry);

        Intent ii = new Intent(getContext(), Monthly_spends.class);
        ii.putExtra("calling", category);
        startActivity(ii);
    }

    @Override
    public void onNothingSelected() {
    }
}