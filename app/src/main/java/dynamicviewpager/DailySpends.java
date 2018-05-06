package dynamicviewpager;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bounside.mj.cashiya.AddCategory;
import com.bounside.mj.cashiya.DividerItemDecoration;
import com.bounside.mj.cashiya.R;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import adapter.Movie;
import adapter.MoviesAdapter;
import recyclercustomlistener.RecyclerItemClickListener;
import sqlitedatabase.Bankmessagedb;

/**
 * Created by JKB-2 on 4/18/2016.
 */
public class DailySpends extends Fragment {

    int id = R.mipmap.atm;

    TextView weekspends;
    String todate,fromdate;

    Bankmessagedb exp;
    LinearLayout linearnorecord,linearrecord;
    private RecyclerView recyclerView;
    Cursor cursor;
    Movie movie;
    private MoviesAdapter mAdapter;
    String dbmoney;
    SQLiteDatabase sql;
    ContentValues can;
    private List<Movie> movieList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.daily_spends, container, false);

        exp = new Bankmessagedb(getActivity());
        sql = exp.getWritableDatabase();
        can = new ContentValues();

        weekspends = (TextView) rootView.findViewById(R.id.textdaily);
        linearrecord = (LinearLayout) rootView.findViewById(R.id.dailylinear);
        linearnorecord = (LinearLayout) rootView.findViewById(R.id.dailylinear1);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listdaily);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");

        Date date = new Date();
        todate = dateFormat.format(date);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7);
        Date todate1 = cal.getTime();

        fromdate = dateFormat.format(todate1);
        Log.i("test", "date " + todate + fromdate);

        weekspends.setText("" + todate);

        getAllRecords();

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        //fetching item by position
                        try {
                            Movie movie = movieList.get(position);

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
        return rootView;
    }

    private void getAllRecords() {

        movieList = new ArrayList<>();

        cursor =  sql.rawQuery("SELECT * FROM trans_msg WHERE changdate = '"+todate+"' ORDER BY changdate,msgtime desc", null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
//                    String aa = cursor.getString(cursor.getColumnIndex("msgid"));
                    String bb = cursor.getString(cursor.getColumnIndex("nameofbank"));
                    String cc = cursor.getString(cursor.getColumnIndex("body"));
                    String dd = cursor.getString(cursor.getColumnIndex("transdate"));
                    String ee= cursor.getString(cursor.getColumnIndex("category"));
                    String type= cursor.getString(cursor.getColumnIndex("type"));
                    String gg= cursor.getString(cursor.getColumnIndex("changdate"));
                    String hh= cursor.getString(cursor.getColumnIndex("amount"));
                    String ii= cursor.getString(cursor.getColumnIndex("msgtime"));

                    //category function

                    id = gettypecat(type);

                    movie = new Movie(bb, hh, dd,id,type);
                    Log.i("movie",""+movie );
                    movieList.add(movie);
                    Log.i("movieList",""+movieList );

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

    private int gettypecat(String type) {
        switch (type) {
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

            case "Entertainment":
                id = R.mipmap.entertainment_aftr;
                break;

            default:
                id = R.mipmap.atm;
                break;
        }
        return id;
    }
}