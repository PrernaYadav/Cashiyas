package bank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bounside.mj.cashiya.Facebook_page;
import com.bounside.mj.cashiya.R;
import com.bounside.mj.cashiya.Rating_app;
import com.bounside.mj.cashiya.Twitter_page;

/**
 * Created by Neeraj Sain on 11/30/2016.
 */

public class LikeShare extends AppCompatActivity {

    ImageView fb,twitter,share,rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.like_share);
        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle("Like And Share");
        }

        fb = (ImageView) findViewById(R.id.fblike);
        twitter = (ImageView) findViewById(R.id.twitterlike);
        share = (ImageView) findViewById(R.id.sharecashiya);
        rate = (ImageView) findViewById(R.id.ratecashiya);

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(LikeShare.this, Facebook_page.class);
                startActivity(i1);
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(LikeShare.this, Twitter_page.class);
                startActivity(i1);
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
             sharingIntent.setType("text/plain");
             sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Cashiya");
             sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Cashiya click here to visit https://play.google.com/store/apps/details?id=com.bounside.mj.cashiya");
             startActivity(Intent.createChooser(sharingIntent, "Share via"));



            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(LikeShare.this, Rating_app.class);
                startActivity(i1);


            }
        });
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
