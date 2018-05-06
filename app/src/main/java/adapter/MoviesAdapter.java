package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bounside.mj.cashiya.R;

import java.util.List;

/**
 * Created by Neeraj Sain on 7/20/2016.
 */
public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    public List<Movie> moviesList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, year, genre,types;
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title1);
            genre = (TextView) view.findViewById(R.id.genre1);
            year = (TextView) view.findViewById(R.id.year1);
            icon = (ImageView) view.findViewById(R.id.img1);
            types = (TextView) view.findViewById(R.id.type1);
        }
    }

    public MoviesAdapter(List<Movie> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = moviesList.get(position);
        holder.title.setText(movie.getTitle());
        holder.genre.setText(movie.getGenre());
        holder.year.setText(movie.getYear());
        holder.types.setText(movie.getType());
        holder.icon.setImageResource(movie.geticon());
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}