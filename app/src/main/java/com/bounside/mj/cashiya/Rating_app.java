package com.bounside.mj.cashiya;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.github.fernandodev.easyratingdialog.library.EasyRatingDialog;

/**
 * Created by Rasika on 10/17/2016.
 */

public class Rating_app extends Activity {

    EasyRatingDialog easyRatingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_app);
        easyRatingDialog = new EasyRatingDialog(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        easyRatingDialog.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        easyRatingDialog.showIfNeeded();
    }

    public void onClickRateNow(View view) {
        easyRatingDialog.rateNow();
    }

    public void onClickNeverReminder(View view) {
        easyRatingDialog.neverReminder();
        finish();
    }

    public EasyRatingDialog getEasyRatingDialog() {
        return easyRatingDialog;
    }
}
