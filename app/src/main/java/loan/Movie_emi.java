package loan;

import android.util.Log;

/**
 * Created by Tamanna on 10/6/2016.
 */

public class Movie_emi {
    private String title;
    private String banknames;
    int icon;

    public Movie_emi() {
    }

    public Movie_emi(String title, int icon, String bankname) {
        this.title = title;
        this.icon = icon;
        this.banknames = bankname;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getbank()
    {
        return banknames;
    }

    public void setbank(String bankss)
    {
        this.title = bankss;
    }

    public int geticon() {

        return icon;

    }

    public void seticon(int icon) {
        this.icon = icon;
    }



}

