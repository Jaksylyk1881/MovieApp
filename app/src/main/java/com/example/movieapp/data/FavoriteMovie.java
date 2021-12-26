package com.example.movieapp.data;

import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(tableName = "favoriteMovie")
public class FavoriteMovie extends Movie {
    public FavoriteMovie(int uniqueId,int id, String original_title, String overview, String backdropPath, String posterPath, String bigPosterPath, String releaseDate, double vote_average, double popularity) {
        super(uniqueId,id, original_title, overview, backdropPath, posterPath, bigPosterPath, releaseDate, vote_average, popularity);
    }

    @Ignore
    public FavoriteMovie(Movie movie){
        super(movie.getUniqueId(),movie.getId(),movie.getOriginal_title(),movie.getOverview(),movie.getBackdropPath(),movie.getPosterPath(),movie.getBigPosterPath(),movie.getReleaseDate(),movie.getVote_average(),movie.getPopularity());
    }
}
