package adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Information {
    public static List<Information> items ;
    String title, time, dmoney;
    int iconid;

    public Information(String title, int iconid, String time, String dmoney) {
        this.title = title;
        this.iconid = iconid;
        this.time = time;
        this.dmoney = dmoney;
    }
    public String getTitle() {
        return title;
    }
    public String getTime() {
        return time;
    }
    public String getDmoney() {
        return dmoney;
    }

}
