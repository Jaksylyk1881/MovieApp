package com.example.movieapp.utils;

import android.util.JsonToken;

import com.example.movieapp.data.Movie;
import com.example.movieapp.data.Review;
import com.example.movieapp.data.Video;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class JSONUtils {
    //for info of all movies
    private static final String KEY_ID = "id";
    private static final String KEY_ORIGINAL_TITLE = "original_title";
    private static final String KEY_OVERVIEW ="overview";
    private static final String KEY_BACKDROP_PATH = "backdrop_path";
    private static final String KEY_POSTER_PATH = "poster_path";
    private static final String KEY_RELEASE_DATE = "release_date";
    private static final String KEY_VOTE_AVERAGE = "vote_average";
    private static final String KEY_POPULARITY = "popularity";

    //for trailers
    private static final String KEY_KEY_VIDEO = "key";
    private static final String KEY_NAME = "name";

    //for reviews
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_CONTENT = "content";




    public static final String BASE_YOUTUBE_URL="https://www.youtube.com/watch?v=";
    public static final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/";
    public static final String SMALL_POSTER_SIZE = "w185";
    public static final String BIG_POSTER_SIZE = "w780";
    public static final String BACK_DROP_SIZE = "w780";

    public static ArrayList<Review> parseJSONReviews(JSONObject jsonObject){
        ArrayList<Review> reviews = new ArrayList<>();
        if (jsonObject==null){
            return reviews;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i =0; i<jsonArray.length(); i++){
                JSONObject jsonReviews = jsonArray.getJSONObject(i);
                String author = jsonReviews.getString(KEY_AUTHOR);
                String content = jsonReviews.getString(KEY_CONTENT);
                Review review = new Review(author,content);
                reviews.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public static ArrayList<Video> parseJSONVideos(JSONObject jsonObject){
        ArrayList<Video> videos = new ArrayList<>();
        if (jsonObject==null){
            return videos;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("results");
            for (int i =0; i<jsonArray.length(); i++){
                JSONObject jsonVideo = jsonArray.getJSONObject(i);
                String name = jsonVideo.getString(KEY_NAME);
                String link =BASE_YOUTUBE_URL+jsonVideo.getString(KEY_KEY_VIDEO);
                Video video = new Video(name,link);
                videos.add(video);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }



    public static ArrayList<Movie> parseJSON(JSONObject jsonObject){
        ArrayList<Movie> movies = new ArrayList<>();
        if(jsonObject==null){
            return movies;
        }
        try {
            JSONArray jsonArrayMovies = jsonObject.getJSONArray("results");
            for (int i = 0; i<jsonArrayMovies.length(); i++){
                JSONObject jsonMovie = jsonArrayMovies.getJSONObject(i);
                int id = jsonMovie.getInt(KEY_ID);
                String  original_title = jsonMovie.getString(KEY_ORIGINAL_TITLE);
                String overview = jsonMovie.getString(KEY_OVERVIEW);
                String backdropPath = jsonMovie.getString(KEY_BACKDROP_PATH);
                String posterPath = BASE_POSTER_URL+SMALL_POSTER_SIZE+jsonMovie.getString(KEY_POSTER_PATH);
                String bigPosterPath = BASE_POSTER_URL+BIG_POSTER_SIZE+jsonMovie.getString(KEY_POSTER_PATH);
                String releaseDate = jsonMovie.getString(KEY_RELEASE_DATE);
                double vote_average = Double.parseDouble(jsonMovie.getString(KEY_VOTE_AVERAGE));
                double  popularity = Double.parseDouble(jsonMovie.getString(KEY_POPULARITY));
                movies.add(new Movie(id,original_title,overview,backdropPath,posterPath, bigPosterPath, releaseDate,vote_average,popularity));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }
}
