package loan;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bounside.mj.cashiya.R;

import java.util.List;

/**
 * Created by Tamanna on 10/6/2016.
 */

public class MovieAdapter_emi extends RecyclerView.Adapter<MovieAdapter_emi.MyViewHolder> {
    Context context;
    public List<Movie_emi> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title,bankname;
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.emi_datas);
            icon = (ImageView) view.findViewById(R.id.bankemi_img);
            bankname = (TextView) view.findViewById(R.id.emi_bankname);

        }
    }


    public MovieAdapter_emi( Context context , List<Movie_emi> moviesList) {
        this.moviesList = moviesList;
        this.context=context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.emi_structure, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie_emi movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.bankname.setText(movie.getbank());
        holder.icon.setImageResource(movie.geticon());
        //holder.icon.setImageBitmap(movie.geticon());


//        Picasso.with(context).load(movie.geticon())
//                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}
