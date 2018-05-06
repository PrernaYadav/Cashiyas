package leads;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import com.bounside.mj.cashiya.Jsonparse;
import com.bounside.mj.cashiya.R;
import shared_pref.UserInformation;

import sqlitedatabase.LoginData;

public class Leads extends AppCompatActivity implements
        ActionBar.TabListener {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private ActionBar actionBar;
    // Tab titles
    private String[] tabs = { "Leads", "OngoingLead", "ClosedLeads" };

    LoginData db = new LoginData(this);
    SQLiteDatabase sql;
    ContentValues can = new ContentValues();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leads);
        sql = db.getWritableDatabase();
        can = new ContentValues();
        getIconLabel();

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pagerleads);
        actionBar = getSupportActionBar();
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Adding Tabs
        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name)
                    .setTabListener(this));
        }

        /**
         * on swiping the viewpager make respective tab selected
         * */

        /**
         * on swiping the viewpager make respective tab selected
         * */
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                // on changing the page
                // make respected tab selected
                actionBar.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
    }

    public String getIconLabel() {
        String user = "";
        String selectQuery = "SELECT * FROM DetailsUser";
        Cursor c = sql.rawQuery(selectQuery, null);
        while (c.moveToNext()) {
            // can.get("userid");
//           // UserInformation.Email = c.getString(c.getColumnIndex("Email"));
            UserInformation.userid = c.getString(c.getColumnIndex("userid"));
          //  Toast.makeText(Leads.this, "................."  + UserInformation.userid  , Toast.LENGTH_SHORT).show();
        }


        return user;
    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {

    }
}
