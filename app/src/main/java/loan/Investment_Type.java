package loan;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bounside.mj.cashiya.MainActivity;
import com.bounside.mj.cashiya.R;

import adapter.BannerRecyclerAdpater;

/**
 * Created by Rasika on 12/13/2016.
 */
public class Investment_Type extends AppCompatActivity {

    ImageView loan, loantransfer, insurance;
    RecyclerView recyclerViewInsurance;
    RecyclerView recyclerViewBanks;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investment_type);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setDisplayHomeAsUpEnabled(true);
            ac.setTitle(R.string.InvestmentPlaning);
        }

        int[] banking_partners_image_list =
                {
                        R.drawable.axisbank,
                        R.drawable.cfl,
                        R.drawable.dhfl,
                        R.drawable.hdfc,
                        R.drawable.icici,
                        R.drawable.indiabulls,
                        R.drawable.kotak,
                        R.drawable.pnbbank,
                        R.drawable.rbl,
                        R.drawable.rx
                };

        int[] insurance_partners_image_list =
                {
                        R.drawable.aegon,
                        R.drawable.apollo_munich_health,
                        R.drawable.bajaj,
                        R.drawable.bharti_axa,
                        R.drawable.cigna,
                        R.drawable.edelweiss_logo,
                        R.drawable.future_generali,
                        R.drawable.hdfc_ergo,
                        R.drawable.hdfc_life_2,
                        R.drawable.iffco,
                        R.drawable.lnt,
                        R.drawable.logo_aviva_car_insurance,
                        R.drawable.max_bupa,
                        R.drawable.max_life,
                        R.drawable.nat,
                        R.drawable.pnb,
                        R.drawable.re,
                        R.drawable.reliance,
                        R.drawable.religare,
                        R.drawable.star_health_insurance,
                        R.drawable.tata_aig_with_bg,
                        R.drawable.universal_sompo
                };

        context = this;

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
//
//        layoutManager.smoothScrollToPosition(recyclerViewBanks, null, 0);


//        LinearLayoutManager layoutManager = new LinearLayoutManager(this) {
//            @Override
//            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
//                final LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Investment_Type.this) {
//
//                    private static final float SPEED = 25f;// Change this value (default=25f)
//                    @Override
//                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
//                        return SPEED / displayMetrics.densityDpi;
//                    }
//
//                    @Override
//                    public PointF computeScrollVectorForPosition(int targetPosition) {
//                        return layoutManager.this.computeScrollVectorForPosition();
//                    return  null;
//                    }
//                };

//                smoothScroller.setTargetPosition(position);
//                startSmoothScroll(smoothScroller);
//            }

//            @Override
//            public void scrollToPositionWithOffset(int position, int offset) {
//                super.scrollToPositionWithOffset(position, 0);
//            }
//        };
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        layoutManager.setReverseLayout(false);

        /*public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
            LinearSmoothScroller smoothScroller = new LinearSmoothScroller(Investment_Type.this) {
                private static final float SPEED = 4000f;// Change this                value (default=25f)
                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return SPEED / displayMetrics.densityDpi;
                }
            };
            smoothScroller.setTargetPosition(position);
            startSmoothScroll(smoothScroller);
        } */

        LinearLayoutManager layoutManager_ = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        recyclerViewInsurance = (RecyclerView) findViewById(R.id.banner_recycler_view_insurance);
        recyclerViewBanks = (RecyclerView) findViewById(R.id.banner_recycler_view_banks);
        recyclerViewInsurance.setLayoutManager(layoutManager);
        recyclerViewBanks.setLayoutManager(layoutManager_);

        recyclerViewInsurance.setHasFixedSize(true);
        recyclerViewBanks.setHasFixedSize(true);
        recyclerViewInsurance.setAdapter(new BannerRecyclerAdpater(insurance_partners_image_list, R.layout.banner_image_layout));
        recyclerViewBanks.setAdapter(new BannerRecyclerAdpater(banking_partners_image_list, R.layout.banner_image_layout));

        recyclerViewInsurance.setItemAnimator(new DefaultItemAnimator());
        recyclerViewBanks.setItemAnimator(new DefaultItemAnimator());

//        autoScrollBanks();
//        autoScrollInsurance();

        loan = (ImageView) findViewById(R.id.loan_invest);
        loantransfer = (ImageView) findViewById(R.id.loantransfer_invest);
        insurance = (ImageView) findViewById(R.id.insurance_invest);

        loan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Investment_Type.this, LoanUser.class);
                startActivity(i1);
            }
        });

        loantransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Investment_Type.this, Loan_transfer.class);
                startActivity(i1);
            }
        });

        insurance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i1 = new Intent(Investment_Type.this, InsuranceTypes.class);
                startActivity(i1);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
//                Intent in1 = new Intent(Investment_Type.this, MainActivity.class);
//                in1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(in1);
//                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void autoScrollBanks() {
        final int speedScroll = 50;
        final Handler handler1 = new Handler();
        final Runnable runnable1 = new Runnable() {
            int count1 = 0;

            @Override
            public void run() {
                if (count1 == recyclerViewBanks.getAdapter().getItemCount())
                    count1 = 0;
                if (count1 < recyclerViewBanks.getAdapter().getItemCount()) {
                    recyclerViewBanks.smoothScrollToPosition(++count1);
                    handler1.postDelayed(this, speedScroll);
                }
            }
        };
        handler1.postDelayed(runnable1, speedScroll);
    }
    public void autoScrollInsurance() {
        final int speedScroll = 50;
        final Handler handler2 = new Handler();
        final Runnable runnable2 = new Runnable() {
            int count2 = 0;

            @Override
            public void run() {
                if (count2 == recyclerViewInsurance.getAdapter().getItemCount())
                    count2 = 0;
                if (count2 < recyclerViewInsurance.getAdapter().getItemCount()) {
                    recyclerViewInsurance.smoothScrollToPosition(++count2);
                    handler2.postDelayed(this, speedScroll);
                }
            }
        };

        handler2.postDelayed(runnable2, speedScroll);
    }
}