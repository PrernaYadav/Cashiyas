package com.bounside.mj.cashiya;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import adapter.Information;
import adapter.LazyAdapter;
import adapter.Movie;
import adapter.MoviesAdapter;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 7/12/2016.
 */
public class ExpenseList extends AppCompatActivity {

    Map<String, String> hmap;
    List<Map> lst = new ArrayList<>();
    String title;
    String selectQuery;
    String[] queries;
    String cat,mnth, key;
    String time;
    String dmoney;
    int spnds = 0;
    int id = R.mipmap.atm;
    LazyAdapter adapter;
    Information aa;
    LinearLayout linearnorecord,linearrecord;

    ArrayList<Integer> colors = new ArrayList<Integer>();

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String dbmoney;
    private List<Movie> movieList;
    String Numbervalue;

    SQLiteDatabase sql;
    ContentValues can;
    Bankmessagedb bbd ;
    Cursor cursor;
    Movie movie;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expense_list);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Income");
        }

        recyclerView = (RecyclerView) findViewById(R.id.expenselistdata);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        linearrecord = (LinearLayout) findViewById(R.id.expenselinear);
        linearnorecord = (LinearLayout) findViewById(R.id.expenselinear1);

        bbd = new Bankmessagedb(this);
        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        getAllRecords();
        Information.items = new ArrayList<>();

    }

    private void getAllRecords() {
        movieList = new ArrayList<>();

        cursor = sql.rawQuery("SELECT * FROM trans_msg WHERE type = 'CREDITED.' ORDER BY yrdata desc,mnthdate desc,changdate  desc", null);
        Log.i("cursor",""+cursor );
        Log.i("cursor",""+sql );
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

                    Log.i("aa",""+bb );
                    Log.i("aa",""+cc );
                    Log.i("addda",""+dd );
                    Log.i("category",""+ee );
                    Log.i("adsda",""+ff );
                    Log.i("atg87tga",""+gg );
                    Log.i("fffff",""+hh );

                    Log.i("adda",""+dbmoney );

                    int count = cursor.getCount();
                    Log.i("count",""+count );

                    //category function

                    id = gettypecat(bb);
                    Log.i("movidde",""+id );

                    // movie = new Movie(aa, bb, dd,id);
                    movie = new Movie(bb, hh, dd,id,ff);
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

            case "PURCHASE.":
                id = R.mipmap.purchase_aftr;
                break;

            case "EXPENSE":
                id = R.mipmap.extra_aftr;
                break;

            case "DEBITED.":
                id = R.mipmap.debited_aftr;
                break;

            case "Salary":
                id = R.mipmap.salaryicon;
                break;


            case "Rent":
                id = R.mipmap.renticon;
                break;


            case "Sale":
                id = R.mipmap.saleicon;
                break;


            case "Entertainment":
                id = R.mipmap.entertainment_aftr;
                break;

            default:
                id = R.mipmap.atm;
                break;
        }
        return id;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }


}
