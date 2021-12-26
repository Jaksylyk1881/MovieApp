package com.example.movieapp.data;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movie.class,FavoriteMovie.class},version = 5,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME = "movies.db";
    private static MovieDatabase movieDatabase;
    private static final Object LOCK = new Object();

    public static MovieDatabase getInstance(Context context) {
        synchronized (LOCK) {
            if (movieDatabase == null) {
                movieDatabase = Room.databaseBuilder(context, MovieDatabase.class, DB_NAME).fallbackToDestructiveMigration().build();
            }
            return movieDatabase;
        }
    }

    public abstract MoviesDao moviesDao();
}
