package dynamicviewpager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bounside.mj.cashiya.DividerItemDecoration;
import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import java.util.ArrayList;
import java.util.List;

import adapter.Movie;
import adapter.MoviesAdapter;
import sqlitedatabase.ExpenseData;

/**
 * Created by Neeraj Sain on 11/28/2016.
 */

public class MyIncomes extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    String dbmoney;
    private List<Movie> movieList;
    int id = R.mipmap.ic_launcher;

    SQLiteDatabase sql;
    ContentValues can;
    ExpenseData bbd = new ExpenseData(this);
    Cursor cursor;
    Movie movie;
    LinearLayout linearnorecord, linearrecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_incomes);

        linearrecord = (LinearLayout) findViewById(R.id.accountshowinc);
        linearnorecord = (LinearLayout) findViewById(R.id.accountnoinc);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Income");
        }

        sql = bbd.getReadableDatabase();
        can = new ContentValues();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view99inc);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        showdatacard();
    }

    private void showdatacard() {
        movieList = new ArrayList<>();

        cursor = sql.rawQuery("SELECT * FROM income", null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    String bb = cursor.getString(cursor.getColumnIndex("category"));
                    String hh = cursor.getString(cursor.getColumnIndex("amount"));
                    String dd = cursor.getString(cursor.getColumnIndex("incomedate"));

                    int count = cursor.getCount();
                    Log.i("count", "" + count);
                    id = gettypecat(bb);

                    movie = new Movie(bb, hh, dd, id, "Income");
                    Log.i("movie", "" + movie);
                    movieList.add(movie);
                    mAdapter = new MoviesAdapter(movieList);
                    Log.i("movieList", "" + mAdapter);
                    recyclerView.setAdapter(mAdapter);
                    Log.i("recyclerView", "" + recyclerView);
                    mAdapter.notifyDataSetChanged();

                } while (cursor.moveToNext());
            }

        } else {
            linearnorecord.setVisibility(View.VISIBLE);
            linearrecord.setVisibility(View.GONE);
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
        Intent in = new Intent(MyIncomes.this, MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        in.putExtra("EXIT", true);
        startActivity(in);
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