package bank;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import dynamicviewpager.DailySpends;
import dynamicviewpager.Monthly_spends_bar;
import dynamicviewpager.WeeklySpends;

/**
 * Created by Neeraj Sain on 11/18/2016.
 */

public class BankNumberAdapter extends FragmentPagerAdapter {

    Monthlybaracno mm = new Monthlybaracno();
    Weeklybaracno ww = new Weeklybaracno();
    Dailybaracno dd = new Dailybaracno();

    public BankNumberAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                return mm;

            case 1:
                return ww;

            case 2:
                return dd;

        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}