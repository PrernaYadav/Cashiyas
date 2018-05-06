package dynamicviewpager;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bounside.mj.cashiya.AddCategory;
import com.bounside.mj.cashiya.DividerItemDecoration;
import com.bounside.mj.cashiya.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import adapter.Movie;
import adapter.MoviesAdapter;
import recyclercustomlistener.RecyclerItemClickListener;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by JKB-2 on 4/18/2016.
 */
public class WeeklySpends extends Fragment implements OnChartValueSelectedListener {

    Map<String, String> hmap;
    List<Map> lst = new ArrayList<>();
    String title;
    String selectQuery;
    String[] queries;
    String cat, key;
    String time;
    String dmoney;
    Movie movie;

    EditText sv;

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Movie> movieList;
    String Numbervalue;
    int id = R.mipmap.ic_launcher;

    TextView weekspends;

    Bankmessagedb exp;
    private int todaymonth;
    String month_name1;
    LinearLayout linearnorecord,linearrecord;
    Date todate1,todate2,todate3,todate4,todate5,todate6,todate7;
    String s1,s2,s3,s4,s5,s6,s7,todate;
    String getdate;

    private BarChart barChartt;

    String[] barzoom=new String[7];
    Float[] barzoomdata=new Float[7];
    BarDataSet dataset1,dataset2;
    ArrayList<String> labels;
    ProgressDialog dialog;

    Calendar myCalendar = Calendar.getInstance();
    private String selecteddat;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.weekly_spends, container, false);
        exp = new Bankmessagedb(getActivity());

        recyclerView = (RecyclerView) rootView.findViewById(R.id.listweek);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        weekspends = (TextView) rootView.findViewById(R.id.textweek);
        linearrecord = (LinearLayout)  rootView.findViewById(R.id.weeklinear);
        linearnorecord = (LinearLayout)  rootView.findViewById(R.id.weeklinear1);

        barChartt = (BarChart) rootView.findViewById(R.id.chartbarweek);
        sv = (EditText) rootView.findViewById(R.id.searchweek);

        DateFormat dateFormat =new SimpleDateFormat("dd-MMM-yyyy");

        Calendar cal7 = Calendar.getInstance();
        todate7 = cal7.getTime();
        s7 = dateFormat.format(todate7);
        barzoom[6]=s7;
        barzoomdata[6]= getdataday(s7);

        Calendar cal6 = Calendar.getInstance();
        cal6.add(Calendar.DATE, -6);
        todate6 = cal6.getTime();
        s6 = dateFormat.format(todate6);
        barzoom[0]=s6;
        barzoomdata[0]= getdataday(s6);

        Calendar cal5 = Calendar.getInstance();
        cal5.add(Calendar.DATE, -5);
        todate5 = cal5.getTime();
        s5 = dateFormat.format(todate5);
        barzoom[1]=s5;
        barzoomdata[1]= getdataday(s5);

        Calendar cal4 = Calendar.getInstance();
        cal4.add(Calendar.DATE, -4);
        todate4 = cal4.getTime();
        s4 = dateFormat.format(todate4);
        barzoom[2]=s4;
        barzoomdata[2]= getdataday(s4);

        Calendar cal3 = Calendar.getInstance();
        cal3.add(Calendar.DATE, -3);
        todate3 = cal3.getTime();
        s3 = dateFormat.format(todate3);
        barzoom[3]=s3;
        barzoomdata[3]= getdataday(s3);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DATE, -2);
        todate2 = cal2.getTime();
        s2 = dateFormat.format(todate2);
        barzoom[4]=s2;
        barzoomdata[4]= getdataday(s2);

        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DATE, -1);
        todate1 = cal1.getTime();
        s1 = dateFormat.format(todate1);
        barzoom[5]=s1;
        barzoomdata[5]= getdataday(s1);

        getAllRecords();
        calulateSavings();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
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

        barChartt.setOnChartValueSelectedListener(this);

        final DatePickerDialog.OnDateSetListener dates = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                //  myCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();

            }

            private void updateLabel()  {

                String myFormat = "dd-MMM-yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selecteddat = sdf.format(myCalendar.getTime());
                getdate = selecteddat;
                Log.i("fvdy",""+selecteddat);
                sv.setText(selecteddat);
                calculatenxtweek(getdate);
            }
        };

        sv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getContext(), dates, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return rootView;
    }

    private void calculatenxtweek(String getdates) {
        DateFormat dateFormat =new SimpleDateFormat("dd-MMM-yyyy");
        s6=getdates;
        Calendar cal1 = Calendar.getInstance();

        barzoom[0]=s6;
        barzoomdata[0]= getdataday(s6);


        try {
            cal1.setTime(dateFormat.parse(s6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.add(Calendar.DATE, 1);
        todate1 = cal1.getTime();
        s1 = dateFormat.format(todate1);
        barzoom[1]=s1;
        barzoomdata[1]= getdataday(s1);

        try {
            cal1.setTime(dateFormat.parse(s6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.add(Calendar.DATE, 2);
        todate2 = cal1.getTime();
        s2 = dateFormat.format(todate2);
        barzoom[2]=s2;
        barzoomdata[2]= getdataday(s2);

        try {
            cal1.setTime(dateFormat.parse(s6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.add(Calendar.DATE, 3);
        todate3 = cal1.getTime();
        s3 = dateFormat.format(todate3);
        barzoom[3]=s3;
        barzoomdata[3]= getdataday(s3);

        try {
            cal1.setTime(dateFormat.parse(s6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.add(Calendar.DATE, 4);
        todate4 = cal1.getTime();
        s4 = dateFormat.format(todate4);
        barzoom[4]=s4;
        barzoomdata[4]= getdataday(s4);

        try {
            cal1.setTime(dateFormat.parse(s6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.add(Calendar.DATE, 5);
        todate5 = cal1.getTime();
        s5 = dateFormat.format(todate5);
        barzoom[5]=s5;
        barzoomdata[5]= getdataday(s5);

        try {
            cal1.setTime(dateFormat.parse(s6));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal1.add(Calendar.DATE, 6);
        todate6 = cal1.getTime();
        s7 = dateFormat.format(todate6);
        barzoom[6]=s7;
        barzoomdata[6]= getdataday(s7);


        getAllRecords();
        calulateSavings();
    }

        private void calulateSavings() {

        ArrayList<IBarDataSet> dataSets;

        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i <= 6; i++) {
            entries.add(new BarEntry(barzoomdata[i], i));
        }

        dataset1 = new BarDataSet(entries, "Weekly Spends");
        dataset1.setBarSpacePercent(50f);
        dataset1.setColor(Color.rgb(106,90,205));

        labels = new ArrayList<String>();
        for (int i = 0; i <= 6; i++) {
            labels.add(barzoom[i]);
        }

        dataSets = new ArrayList<>();
        dataSets.add(dataset1);

        BarData data1 = new BarData(labels, dataSets);

        //******************************set all bar data on barchartt********************************//
        barChartt.setData(data1);
        barChartt.setDescription("");
        barChartt.animateXY(2000, 2000);
        barChartt.getAxisLeft().setDrawGridLines(false);

        barChartt.getXAxis().setDrawGridLines(false);

        barChartt.invalidate();

    }

    private Float getdataday(String day) {
        String aa = day;
        float spnds = 0;
        selectQuery = "SELECT amount from trans_msg WHERE changdate='" + aa + "' AND type!='CREDITED.' AND type!='AVAILABLE BALANCE'";
        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {

            do {
                dmoney = cursor.getString(cursor.getColumnIndex("amount"));
                Log.i("broadingsss1aa", "inside list getALLRecords" + dmoney);
                spnds = spnds + Float.parseFloat(dmoney);
                Log.i("broadingsss1aa", "inside list getALLRecords" + dmoney);
            }
            while (cursor.moveToNext());

        }
//        cursor.close();

        return spnds;
    }


    public void getAllRecords() {
        movieList = new ArrayList<>();
        float spnds = 0;
        Log.i("dateSS", "from" + s7+"  ss"+ todate);

        //  selectQuery = "SELECT * FROM trans_msg WHERE changdate BETWEEN '" + fromdate + "' AND '" + todate + "' and month =" + todaymonth + " ORDER BY CAST(changdate as date) desc";
//        selectQuery ="SELECT * FROM(SELECT * FROM trans_msg WHERE changdate = '"+s6+"' OR changdate = '"+s7+"' OR changdate = '"+s1+"' OR changdate = '"+s2+"' OR changdate = '"+s3+"' OR changdate = '"+s4+"' OR changdate = '"+s5+"' " +
//                "AND type='AVAILABLE BALANCE' ORDER BY yrdata desc,mnthdate desc,changdate desc LIMIT 1) " +
//                "UNION ALL " +
//                "SELECT * FROM(SELECT * FROM trans_msg WHERE changdate = '"+s6+"' OR changdate = '"+s7+"' OR changdate = '"+s1+"' OR changdate = '"+s2+"' OR changdate = '"+s3+"' OR changdate = '"+s4+"' OR changdate = '"+s5+"' " +
//                "AND " +
//                "type!='AVAILABLE BALANCE' ORDER BY yrdata desc,mnthdate desc,changdate desc)";
        selectQuery = "SELECT * FROM trans_msg WHERE changdate = '"+s6+"' OR changdate = '"+s7+"' OR changdate = '"+s1+"'" +
                "OR changdate = '"+s2+"' OR changdate = '"+s3+"' OR changdate = '"+s4+"' OR changdate = '"+s5+"' ORDER BY yrdata desc,mnthdate desc,changdate  desc,msgtime desc";


        Log.i("gygiu", "from" + selectQuery );
        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        lst.clear();
        if(cursor!=null){
            if (cursor.moveToFirst()) {

                do {
                    hmap = new HashMap<String, String>();
                    title = cursor.getString(cursor.getColumnIndex("nameofbank"));

                    Log.i("broadingsss1", "inside list getALLRecords" + title);
                    time = cursor.getString(cursor.getColumnIndex("transdate"));
                    Log.i("broadingsss1", "inside list getALLRecords" + time);
                    dmoney = cursor.getString(cursor.getColumnIndex("amount"));
                    Log.i("broadingsss1", "inside list getALLRecords" + dmoney);
                    spnds = spnds +  Float.parseFloat(dmoney);
                    Log.i("broadingsss1", "inside list getALLRecords" + dmoney);
                    cat = cursor.getString(cursor.getColumnIndex("type"));
                    Log.i("broadingsss1", "inside list getALLRecords" + cat);

                    hmap.put("title", title);
                    hmap.put("time", time);
                    hmap.put("dmoney", dmoney);
                    hmap.put("cat", cat);
                    lst.add(hmap);


                    id = gettypecat(cat);
                    Log.i("movidde",""+id );

                    // movie = new Movie(aa, bb, dd,id);
                    movie = new Movie(title, dmoney, time,id,cat);
                    Log.i("movie",""+movie );
                    movieList.add(movie);
                    mAdapter = new MoviesAdapter(movieList);
                    Log.i("movieList",""+mAdapter );
                    recyclerView.setAdapter(mAdapter);
                    Log.i("recyclerView",""+recyclerView );
                    mAdapter.notifyDataSetChanged();

                    weekspends.setText("" + s6 + " " + "-" + " " + s7);
                    Log.i("broadingdddd", "lst " + lst);
                }
                while (cursor.moveToNext());
            }
            else
            {
                linearnorecord.setVisibility(View.VISIBLE);
                linearrecord.setVisibility(View.GONE);
            }
        }

    }

    private int gettypecat(String ff) {
        switch (ff) {
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        int  entry = e.getXIndex();


        Log.i("ddsdd",""+entry);
        String category =labels.get(entry);


        Log.i("category",""+category);
        Intent ii = new Intent(getContext(), WeeklyPieChart.class);
        ii.putExtra("calling", category);

        startActivity(ii);
    }

    @Override
    public void onNothingSelected() {

    }

}
