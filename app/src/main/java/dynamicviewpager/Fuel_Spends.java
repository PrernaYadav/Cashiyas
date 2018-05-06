/*
package dynamicviewpager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bounside.mj.cashiya.AddCategory;
import com.bounside.mj.cashiya.DividerItemDecoration;
import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Movie;
import adapter.MoviesAdapter;
import recyclercustomlistener.RecyclerItemClickListener;
import sqlitedatabase.Bankmessagedb;

*/
/**
 * Created by Harsh on 3/31/2016.
 *//*

public class Fuel_Spends extends Activity {
    Map<String, String> hmap;
    List<Map> lst = new ArrayList<>();
    String title;
    String categ, selectQuery, selectQuery1;
    String[] queries;
    String time;
    String dmoney;
    float spnds = 0;
    String cat, key,month,year;
    TextView total_spends;
    TextView show;
    String come;


    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String dbmoney;
    private List<Movie> movieList;
    String Numbervalue;
    int id = R.mipmap.ic_launcher;
    Movie movie;


    Bankmessagedb exp = new Bankmessagedb(Fuel_Spends.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_spends);
        Log.i("broading", "list onCreate");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        total_spends = (TextView) findViewById(R.id.total_spend);
        show = (TextView) findViewById(R.id.showspends);

        key = getIntent().getStringExtra("calling");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("addadys");
        come = getIntent().getStringExtra("coming");


       // new GetRecord().execute();

        if(come.equals("monthly")) {

            getAllRecords();
        }
        else
        {
            getAllRecords1();
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        //fetching item by position
                        try {
                            Movie movie = movieList.get(position);


                            //   HashMap<String,String> map =(HashMap<String,String>)recyclerView.getAdapter(position-1);
                            Intent intent = new Intent(Fuel_Spends.this, AddCategory.class);
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

    }

    private void getAllRecords1() {

        movieList = new ArrayList<>();
        Log.i("nameaasds",""+key);

       // SELECT NAME, SUM(SALARY) FROM COMPANY GROUP BY NAME;
      selectQuery = "SELECT * FROM trans_msg WHERE type = '"+key+"'  And changdate LIKE '"+month+"' ORDER BY yrdata desc,mnthdate desc,changdate  desc";

        Log.i("selectQuery",""+selectQuery);
        UserInformation.id_fuel = R.mipmap.food_aftr;
        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                hmap = new HashMap<String, String>();
                title = cursor.getString(cursor.getColumnIndex("nameofbank"));
                Log.i("ssss", "inside list getALLRecords" + title);
                //categ[i] = String.valueOf(cursor.getString())
                time = cursor.getString(cursor.getColumnIndex("transdate"));
                dmoney = cursor.getString(cursor.getColumnIndex("amount"));
                //float temp = Float.parseFloat(amnt);
                //  spnds = spnds + Integer.parseInt(dmoney);
                spnds = spnds +  Float.parseFloat(dmoney);
                cat = cursor.getString(cursor.getColumnIndex("type"));
                Log.i("sssss", "inside list getALLRecords" + cat);

                id = gettypecat(cat);
                Log.i("movidde",""+id );

                // movie = new Movie(aa, bb, dd,id);
                movie = new Movie(title, dmoney, time,id,cat);
                Log.i("movie",""+movie );
                movieList.add(movie);
                Log.i("movieList",""+movieList );

                mAdapter = new MoviesAdapter(movieList);
                Log.i("movieList",""+mAdapter );
                recyclerView.setAdapter(mAdapter);
                Log.i("recyclerView",""+recyclerView );
                mAdapter.notifyDataSetChanged();
            }
            while (cursor.moveToNext());

        }
        Log.i("broading", "expense fuel " + spnds);
        total_spends.setText("Rs." + spnds);

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


    //      CREATE TABLE IF NOT EXISTS expense(ID INTEGER PRIMARY KEY, item VARCHAR(200), amount VARCHAR(100), date VARCHAR(250), category VARCHAR(200))
    public void getAllRecords() {
        movieList = new ArrayList<>();
        Log.i("nameaasds",""+key);


            selectQuery = "SELECT * FROM trans_msg WHERE type = '"+key+"'  And changdate LIKE '%-"+month+"-"+year+"' ORDER BY yrdata desc,mnthdate desc,changdate  desc";
        Log.i("selectQuery",""+selectQuery);
        UserInformation.id_fuel = R.mipmap.food_aftr;
        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                hmap = new HashMap<String, String>();
                title = cursor.getString(cursor.getColumnIndex("nameofbank"));
                Log.i("ssss", "inside list getALLRecords" + title);
                //categ[i] = String.valueOf(cursor.getString())
                time = cursor.getString(cursor.getColumnIndex("transdate"));
                dmoney = cursor.getString(cursor.getColumnIndex("amount"));
                //float temp = Float.parseFloat(amnt);
              //  spnds = spnds + Integer.parseInt(dmoney);
                spnds = spnds +  Float.parseFloat(dmoney);
                cat = cursor.getString(cursor.getColumnIndex("type"));
                Log.i("sssss", "inside list getALLRecords" + cat);

                id = gettypecat(cat);
                Log.i("movidde",""+id );

                // movie = new Movie(aa, bb, dd,id);
                movie = new Movie(title, dmoney, time,id,cat);
                Log.i("movie",""+movie );
                movieList.add(movie);
                Log.i("movieList",""+movieList );

                mAdapter = new MoviesAdapter(movieList);
                Log.i("movieList",""+mAdapter );
                recyclerView.setAdapter(mAdapter);
                Log.i("recyclerView",""+recyclerView );
                mAdapter.notifyDataSetChanged();
                show.setText("Total  "+key+"  =");


            }
            while (cursor.moveToNext());

        }
        Log.i("broading", "expense fuel " + spnds);
        total_spends.setText("Rs." + spnds);

    }



}*/



package dynamicviewpager;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bounside.mj.cashiya.AddCategory;
import com.bounside.mj.cashiya.DividerItemDecoration;
import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.Movie;
import adapter.MoviesAdapter;
import recyclercustomlistener.RecyclerItemClickListener;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by Harsh on 3/31/2016.
 */
public class Fuel_Spends extends Activity {
    Map<String, String> hmap;
    List<Map> lst = new ArrayList<>();
    String title;
    String categ, selectQuery, selectQuery1;
    String[] queries;
    String time;
    String dmoney;
    float spnds = 0;
    String cat, key,month,year;
    TextView total_spends;
    TextView show;
    String come;


    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Movie> movieList;
    int id = R.mipmap.ic_launcher;
    Movie movie;

    Bankmessagedb exp = new Bankmessagedb(Fuel_Spends.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuel_spends);

        Log.i("broading", "list onCreate");
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        total_spends = (TextView) findViewById(R.id.total_spend);
        show = (TextView) findViewById(R.id.showspends);

        key = getIntent().getStringExtra("calling");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("addadys");
        come = getIntent().getStringExtra("coming");


        // new GetRecord().execute();

        if(come.equals("monthly")) {

            getAllRecords();
        }
        else
        {
            getAllRecords1();
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        //fetching item by position
                        try {
                            Movie movie = movieList.get(position);


                            //   HashMap<String,String> map =(HashMap<String,String>)recyclerView.getAdapter(position-1);
                            Intent intent = new Intent(Fuel_Spends.this, AddCategory.class);
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

    }

    private void getAllRecords1() {

        movieList = new ArrayList<>();
        Log.i("nameaasds",""+key);

        // SELECT NAME, SUM(SALARY) FROM COMPANY GROUP BY NAME;
        selectQuery = "SELECT * FROM trans_msg WHERE type = '"+key+"'  And changdate LIKE '"+month+"' ORDER BY yrdata desc,mnthdate desc,changdate  desc";

        Log.i("selectQuery",""+selectQuery);
        UserInformation.id_fuel = R.mipmap.food_aftr;
        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                hmap = new HashMap<String, String>();
                title = cursor.getString(cursor.getColumnIndex("nameofbank"));
                Log.i("ssss", "inside list getALLRecords" + title);
                time = cursor.getString(cursor.getColumnIndex("transdate"));
                dmoney = cursor.getString(cursor.getColumnIndex("amount"));
                spnds = spnds +  Float.parseFloat(dmoney);
                cat = cursor.getString(cursor.getColumnIndex("type"));
                Log.i("sssss", "inside list getALLRecords" + cat);

                id = gettypecat(cat);
                Log.i("movidde",""+id );

                movie = new Movie(title, dmoney, time,id,cat);
                Log.i("movie",""+movie );
                movieList.add(movie);
                Log.i("movieList",""+movieList );

                mAdapter = new MoviesAdapter(movieList);
                Log.i("movieList",""+mAdapter );
                recyclerView.setAdapter(mAdapter);
                Log.i("recyclerView",""+recyclerView );
                mAdapter.notifyDataSetChanged();
            }
            while (cursor.moveToNext());

        }
        Log.i("broading", "expense fuel " + spnds);
        total_spends.setText("Rs." + spnds);

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


    //      CREATE TABLE IF NOT EXISTS expense(ID INTEGER PRIMARY KEY, item VARCHAR(200), amount VARCHAR(100), date VARCHAR(250), category VARCHAR(200))
    public void getAllRecords() {
        movieList = new ArrayList<>();
        Log.i("nameaasds",""+key);


        selectQuery = "SELECT * FROM trans_msg WHERE type = '"+key+"'  And changdate LIKE '%-"+month+"-"+year+"' ORDER BY yrdata desc,mnthdate desc,changdate  desc";
        Log.i("selectQuery",""+selectQuery);
        UserInformation.id_fuel = R.mipmap.food_aftr;
        SQLiteDatabase db = exp.getWritableDatabase();
        Cursor cursor =db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {

            do {
                hmap = new HashMap<String, String>();
                title = cursor.getString(cursor.getColumnIndex("nameofbank"));
                Log.i("ssss", "inside list getALLRecords" + title);
                time = cursor.getString(cursor.getColumnIndex("transdate"));
                dmoney = cursor.getString(cursor.getColumnIndex("amount"));
                spnds = spnds +  Float.parseFloat(dmoney);
                cat = cursor.getString(cursor.getColumnIndex("type"));
                Log.i("sssss", "inside list getALLRecords" + cat);

                id = gettypecat(cat);
                Log.i("movidde",""+id );

                movie = new Movie(title, dmoney, time,id,cat);
                Log.i("movie",""+movie );
                movieList.add(movie);
                Log.i("movieList",""+movieList );

                mAdapter = new MoviesAdapter(movieList);
                Log.i("movieList",""+mAdapter );
                recyclerView.setAdapter(mAdapter);
                Log.i("recyclerView",""+recyclerView );
                mAdapter.notifyDataSetChanged();
                show.setText("Total  "+key+"  =");
            }
            while (cursor.moveToNext());
        }
        Log.i("broading", "expense fuel " + spnds);
        total_spends.setText("Rs." + spnds);
    }
}