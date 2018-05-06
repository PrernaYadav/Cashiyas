package com.bounside.mj.cashiya;

import android.content.ContentValues;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import adapter.Movie;
import adapter.MoviesAdapter;
import bank.DetailAccntnmbr;
import recyclercustomlistener.RecyclerItemClickListener;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by Neeraj Sain on 8/3/2016.
 */
public class MyCardData extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String dbmoney;
    private List<Movie> movieList;
    int id = R.mipmap.ic_launcher;

    SQLiteDatabase sql;
    ContentValues can;
    Bankmessagedb bbd = new Bankmessagedb(this);
    Cursor cursor;
    Movie movie;
    ImageView ii;
    TextView tt;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mycardsdata);

        Intent intent = getIntent();
        name = intent.getStringExtra("cardname");
        Log.i("nameaa",""+ name);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setHomeAsUpIndicator(R.drawable.gohome1);
        }

        sql = bbd.getWritableDatabase();
        can = new ContentValues();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showdatacard();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //fetching item by position
                        try {
                            Movie movie = movieList.get(position);

                            //   HashMap<String,String> map =(HashMap<String,String>)recyclerView.getAdapter(position-1);
                            Intent intent = new Intent(MyCardData.this, AddCategory.class);
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

    private void showdatacard() {
        Log.i("nameaasds",""+name);
        movieList = new ArrayList<>();
        cursor = sql.rawQuery("SELECT * FROM(SELECT * FROM trans_msg WHERE type='AVAILABLE BALANCE' AND nameofbank ='"+name+"' ORDER BY yrdata desc,mnthdate desc,changdate desc,msgtime desc LIMIT 1) UNION ALL SELECT * FROM(SELECT * FROM trans_msg WHERE  type!='AVAILABLE BALANCE' AND nameofbank ='"+name+"' ORDER BY yrdata desc,mnthdate desc,changdate desc,msgtime desc)", null);
        // cursor = sql.rawQuery("SELECT * FROM trans_msg WHERE nameofbank = 'State Bank Of India'  ORDER BY yrdata desc,mnthdate desc,changdate  desc ", null);
        // cursor = sql.rawQuery("SELECT * FROM trans_msg WHERE nameofbank ='"+name+"' ORDER BY yrdata desc,mnthdate desc,changdate  desc ", null);

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
                    String iii= cursor.getString(cursor.getColumnIndex("accunttype"));

                    Log.i("aadd",""+bb );
                    Log.i("aadd",""+cc );
                    Log.i("aadd",""+dd );
                    Log.i("aadd",""+ee );
                    Log.i("aadd",""+ff );
                    Log.i("aadd",""+gg );
                    Log.i("aaaaadd",""+iii );

                    Log.i("aa",""+dbmoney );

                    int count = cursor.getCount();
                    Log.i("count",""+count );
                    id = gettypecat(ff);

                    //  movie = new Movie(aa, bb, dd,id);
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
            case "EXTRA":
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
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent(MyCardData.this,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.putExtra("EXIT", true);
        startActivity(in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent in1 = new Intent(MyCardData.this,MainActivity.class);
                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in1);
                finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
