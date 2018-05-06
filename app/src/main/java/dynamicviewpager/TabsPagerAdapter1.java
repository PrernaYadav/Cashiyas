package dynamicviewpager;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by JKB-2 on 4/21/2016.
 */
public class TabsPagerAdapter1 extends FragmentPagerAdapter {

    Monthly_spends_bar mm = new Monthly_spends_bar();
    WeeklySpends ww = new WeeklySpends();
    DailySpends dd = new DailySpends();

    public TabsPagerAdapter1(FragmentManager fm) {
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
        // get item count - equal to number of tabs
        return 3;
    }

}