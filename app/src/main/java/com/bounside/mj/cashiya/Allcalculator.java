package com.bounside.mj.cashiya;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import bank.CalculatorEligibility;
import bank.Personalloancalc;
import dynamicviewpager.EmiCalc;
import dynamicviewpager.SavigCalc;

/**
 * Created by Neeraj Sain on 10/11/2016.
 */

public class Allcalculator extends AppCompatActivity {
    Button simple,emi,personal,home,btnWealthCalculator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.allcalculator);

        ActionBar ac = getSupportActionBar();
        if (ac != null) {
            ac.setHomeButtonEnabled(true);
            ac.setTitle("All Calculators");
            ac.setDisplayHomeAsUpEnabled(true);
        }

        simple = (Button) findViewById(R.id.sscalc);
        emi = (Button) findViewById(R.id.emicalc);
        btnWealthCalculator = (Button) findViewById(R.id.btnWealthCalculator);

        personal = (Button) findViewById(R.id.personalcalc);
        home = (Button) findViewById(R.id.homecalc);

        simple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ii = new Intent(Allcalculator.this, SavigCalc.class);
                startActivity(ii);
            }
        });

        emi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i2 = new Intent(Allcalculator.this, EmiCalc.class);
                startActivity(i2);
            }
        });

        personal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i3 = new Intent(Allcalculator.this, Personalloancalc.class);
                startActivity(i3);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {        //homeloan eligibility calc
                Intent i4 = new Intent(Allcalculator.this, CalculatorEligibility.class);
                startActivity(i4);

            }
        });
        btnWealthCalculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i5 = new Intent(Allcalculator.this, WealthCalculator.class);
                startActivity(i5);
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
