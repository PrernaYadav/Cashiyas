package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bounside.mj.cashiya.R;
import com.squareup.picasso.Picasso;

/**
 * Created by hp on 3/20/2017.
 */

public class BannerRecyclerAdpater extends RecyclerView.Adapter<BannerRecyclerAdpater.ViewHolder> {

    private int items[];
    private int itemLayout;

    public BannerRecyclerAdpater(int items[], int itemLayout) {
        this.items = items;
        this.itemLayout = itemLayout;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        int item = items[position];
//        holder.image.setImageBitmap(null);
        holder.image.setImageResource(items[position]);

//        Picasso.with(holder.image.getContext()).cancelRequest(holder.image);
//        Picasso.with(holder.image.getContext()).load(item.getImage()).into(holder.image);
//        holder.itemView.setTag(item);

    }

    @Override
    public int getItemCount() {
        return items.length;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);

            image = (ImageView) itemView.findViewById(R.id.banner_image);

        }
    }
}
