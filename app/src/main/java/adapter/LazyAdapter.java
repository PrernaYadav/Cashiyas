package adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bounside.mj.cashiya.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Harsh on 3/31/2016.
 */
public class LazyAdapter extends ArrayAdapter<List<Map>> {

    private final Activity context;
    //    private final String[] title;
//    private final String[] categ;
//    private final String[] time;
//    private final String[] dmoney;
    List<Map> lst = new ArrayList<>();
    int id;

    public LazyAdapter(Activity context, int id, List lst) {
        super(context, id, lst);
        this.context = context;
        this.lst = lst;
        Log.i("broading", "constructor inside" + lst);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.custom_row, null, true);
            viewHolder = new ViewHolder();
            id = position - (position - 1);
            viewHolder.time = (TextView) view.findViewById(R.id.txt_time);
            viewHolder.account = (TextView) view.findViewById(R.id.txt);
            viewHolder.amount = (TextView) view.findViewById(R.id.txt_dmoney);
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
//        String titleS = String.valueOf(lst.get(position).get("title"));
//        String timeS = String.valueOf(lst.get(position).get("time"));
//        String dmoneyS = String.valueOf(lst.get(position).get("dmoney"));
        String catS = String.valueOf(lst.get(position).get("cat"));
        Log.i("broading", "list values" + lst);

        switch (catS) {
            case "Bills":
                id = R.mipmap.bill_aftr;
                break;
            case "Food":
                id = R.mipmap.food_aftr;
                break;
            case "Fuel":
                id = R.mipmap.fuel_aftr;
                break;
            case "Groceries":
                id = R.mipmap.grocery_aftr;
                break;
            case "Health":
                id = R.mipmap.health_aftr;
                break;

            case "Shopping":
                id = R.mipmap.shoping_aftr;
                break;

            case "Travel":
                id = R.mipmap.travel_aftr;
                break;

            case "Other":
                id = R.mipmap.other_aftr;
                break;

            case "Entertainment":
                id = R.mipmap.entertainment_aftr;
                break;

            case "Notify":
                id = R.mipmap.notifyy;
                break;
            case "PURCHASE.":
                id = R.mipmap.purchase_aftr;
                break;
            case "EXPENSE":
                id = R.mipmap.extra_aftr;
                break;

            case "DEBITED.":
                id = R.mipmap.debited_aftr;
                break;

            case "CREDITED.":
                id = R.mipmap.atm;
                break;
            default:
                id = R.mipmap.atm;
                break;
        }
        viewHolder.time.setText(lst.get(position).get("time").toString());
        viewHolder.account.setText(lst.get(position).get("title").toString());
        viewHolder.amount.setText(lst.get(position).get("dmoney").toString());
        viewHolder.imageView.setImageResource(id);
        // imageView.setImageResource(imageId[position]);
        return view;
    }

    private class ViewHolder {
        TextView time, account, amount,cat;
        ImageView imageView;
    }
}