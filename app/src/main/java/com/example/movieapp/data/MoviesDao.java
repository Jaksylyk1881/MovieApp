package com.example.movieapp.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MoviesDao {
    @Query("SELECT * FROM movies")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM movies WHERE id=:movieId")
    Movie getMovieById(int movieId);
    @Insert
    void insertMovie(Movie movie);
    @Delete
    void deleteMovie(Movie movie);
    @Query("DELETE FROM movies")
    void deleteAllMovies();


    @Query("SELECT * FROM favoriteMovie")
    LiveData<List<FavoriteMovie>> getAllFavoriteMovies();

    @Query("SELECT * FROM favoriteMovie WHERE id=:movieId")
    FavoriteMovie getFavoriteMovieById(int movieId);
    @Insert
    void insertFavoriteMovie(FavoriteMovie favoriteMovie);
    @Delete
    void deleteFavoriteMovie(FavoriteMovie favoriteMovie);
    @Query("DELETE FROM favoriteMovie")
    void deleteAllFavoriteMovies();
}
