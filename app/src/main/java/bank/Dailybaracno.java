package bank;

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
 * Created by Neeraj Sain on 11/18/2016.
 */

public class Dailybaracno extends Fragment {

    ListView listView;
    Map<String, String> hmap;
    List<Map> lst = new ArrayList<>();
    String title;
    String selectQuery;
    String[] queries;
    String cat, key;
    String time;
    String dmoney;
    float spnds = 0;
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

    String accntno,accntname;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.daily_bar_acno, container, false);
        exp = new Bankmessagedb(getActivity());
        sql = exp.getWritableDatabase();
        can = new ContentValues();


        weekspends = (TextView) rootView.findViewById(R.id.textdailyacno);
        linearrecord = (LinearLayout) rootView.findViewById(R.id.dailylinearacno);
        linearnorecord = (LinearLayout) rootView.findViewById(R.id.dailylinear1acno);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.listdailyacno);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        accntno = getActivity().getIntent().getExtras().getString("accuntnumbers");
        accntname = getActivity().getIntent().getExtras().getString("accuntname");
        Log.i("nameaa",""+ accntno);
        Log.i("nameaa",""+ accntname);

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





        return rootView;
    }

    private void getAllRecords() {

        movieList = new ArrayList<>();

        cursor =  sql.rawQuery("SELECT * FROM trans_msg WHERE changdate = '"+todate+"' AND accuntnumber ='"+accntno+"' AND nameofbank='"+accntname+"' ORDER BY changdate desc,msgtime desc", null);
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
                    Log.i("aa",""+ee );
                    Log.i("adsda",""+ff );
                    Log.i("atg87tga",""+gg );
                    Log.i("fffff",""+hh );

                    Log.i("adda",""+dbmoney );

                    int count = cursor.getCount();
                    Log.i("count",""+count );

                    //category function

                    id = gettypecat(ff);
                    Log.i("movidde",""+id );

                    // movie = new Movie(aa, bb, dd,id);
                    movie = new Movie(bb, hh, dd,id,ff);
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
