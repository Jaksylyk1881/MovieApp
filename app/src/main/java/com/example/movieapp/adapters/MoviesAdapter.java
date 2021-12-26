package com.example.movieapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.R;
import com.example.movieapp.data.Movie;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {
    List<Movie> movies;
    private OnPosterClickListener onPosterClickListener;
    private OnReachListener onReachListener;

    public void setOnReachListener(OnReachListener onReachListener) {
        this.onReachListener = onReachListener;
    }

    public void setOnPosterClickListener(OnPosterClickListener onPosterClickListener) {
        this.onPosterClickListener = onPosterClickListener;
    }

    public MoviesAdapter() {
        movies = new ArrayList<>();
    }

   public interface OnPosterClickListener{
        void onPosterClick(int position);
    }
    public interface OnReachListener{
        void onReach();
    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item,parent,false);
        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.MoviesViewHolder holder, int position) {
        if (movies.size()>=20 && position>movies.size()-4&&onReachListener!=null){
            onReachListener.onReach();
        }
        Movie movie = movies.get(position);
        Picasso.get().load(movie.getPosterPath()).into(holder.smallImagePoster);
//        Glide.with(context).load(movie.getPosterPath()).into(holder.smallImagePoster);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public List<Movie> getMovies() {
        return movies;
    }
    public void addMovies(List<Movie> movies) {
        this.movies.addAll(movies);
        notifyDataSetChanged();
    }

    public void clear(){
        this.movies.clear();
        notifyDataSetChanged();
    }
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

     class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView smallImagePoster;
        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            smallImagePoster = itemView.findViewById(R.id.smallImagePoster);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onPosterClickListener!=null){
                        onPosterClickListener.onPosterClick(getPosition());
                    }
                }
            });
        }
    }
}
