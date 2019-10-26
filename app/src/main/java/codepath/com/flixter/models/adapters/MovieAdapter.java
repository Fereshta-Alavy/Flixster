package codepath.com.flixter.models.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.util.List;

import codepath.com.flixter.DetailActivity;
import codepath.com.flixter.R;
import codepath.com.flixter.models.Movie;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.viewHolder>{

    Context context;
    List<Movie> movies;


    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View movieView= LayoutInflater.from(context).inflate(R.layout.item_movie,parent,false);
        return new viewHolder(movieView);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        //Get the movie at the passed in position
        Movie movie = movies.get(position);
        //Bind the movie data into the VH
        holder.bind(movie);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;
        ImageView movieIcon;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle= itemView.findViewById(R.id.tvTitle);
            tvOverview= itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            movieIcon = itemView.findViewById(R.id.movieIcon);
            container = itemView.findViewById(R.id.container);
        }

        public void bind(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverView());
            Glide.with(context).load(movie.getPosterPath()).into(ivPoster);
             container.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     //navigate to new activity when tap on this item
                     Intent i = new Intent(context , DetailActivity.class);
                     i.putExtra("movie", Parcels.wrap(movie));
                     context.startActivity(i);
                 }
             });
        }
    }
}
