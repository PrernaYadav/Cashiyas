package com.bounside.mj.cashiya;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import shared_pref.UserInformation;

/**
 * Created by JKB-2 on 4/27/2016.
 */
public class ShowTargets extends AppCompatActivity {
    int income, target,saving, yearlysave, year, needtosave,needtosave1;
    String targetdata[] = {"Target", "Income", "Yearly Saving", "Need To Save"};
    TextView textincome,texttarget,textyearly,textneed;
    private BarChart barCharttarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_targets);

        barCharttarget = (BarChart) findViewById(R.id.charttargets);

        //****************************************textviews***********************//

        textincome = (TextView) findViewById(R.id.tvincome1);
        texttarget = (TextView) findViewById(R.id.tvtarget1);
        textyearly = (TextView) findViewById(R.id.tvsaving1);
        textneed = (TextView) findViewById(R.id.tvneed1);


        income = UserInformation.income_edi;
        target = UserInformation.target_edi;
        saving = UserInformation.saving_edi;
        year = UserInformation.year_edi;
        yearlysave= year * saving * 12;
        needtosave = target - yearlysave;
        if(needtosave<0)
        {
            needtosave1 = 0;
        }
        else
        {
            needtosave1 = needtosave;
        }

        needtosave = needtosave / year;
        calculatetargets();
        textincome.setText(""+income);
        texttarget.setText(""+target);
        textyearly.setText(""+yearlysave);
        textneed.setText(""+needtosave1);

    }

    private void calculatetargets() {
        int[] data = {
                target,
                income,
                yearlysave,
                needtosave
        };
        ArrayList<BarEntry> entries = new ArrayList<>();
        int count = data.length;
        for (int i = 0; i < count; i++) {
            entries.add(new BarEntry(data[i], i));
            Log.i("broading", " percb " + data[i]);

        }
        BarDataSet dataset1 = new BarDataSet(entries, "");
        dataset1.setColors(ColorTemplate.COLORFUL_COLORS);


        ArrayList<String> labels = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            labels.add(targetdata[i]);
            Log.i("broading", " percb " + targetdata[i]);

        }
        BarData data1 = new BarData(labels, dataset1);
        Log.i("broading", " percb " + data1);

        barCharttarget.setData(data1);
        barCharttarget.setDescription("");
        barCharttarget.animateXY(2000, 2000);
        barCharttarget.getAxisLeft().setDrawGridLines(false);
        barCharttarget.getXAxis().setDrawGridLines(false);
        barCharttarget.invalidate();
    }
}