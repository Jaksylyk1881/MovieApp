package com.example.movieapp.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.example.movieapp.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NetworkUtils {

    private static final String BAS_URL = "https://api.themoviedb.org/3/discover/movie";
    private static final String BASE_URL_REVIEWS = "https://api.themoviedb.org/3/movie/%s/reviews";
    private static final String BASE_URL_VIDEOS = "https://api.themoviedb.org/3/movie/%s/videos";


    private static final String PARAM_API_KEY = "api_key";
    private static final String PARAM_LANG = "language";
    private static final String PARAM_SORT_BY = "sort_by";
    private static final String PARAM_PAGE = "page";
    private static final String PARAM_VOTE_COUNT = "vote_count.gte";


    private static final String API_KEY = "4e1808f48673a589b0697bd4184f1edc";
    private static final String POPULARITY_SORT = "popularity.desc";
    private static final String TOP_RATED_SORT = "vote_average.desc";
    private static final String MIN_VOTE_COUNT = "10000";

    public static final int POPULARITY = 0;
    public static final int TOP_RATED = 1;

    public static URL generateURLReviews(int id, String lang){
        URL result = null;
        Uri uri = Uri.parse(String.format(BASE_URL_REVIEWS,id)).buildUpon()
                .appendQueryParameter(PARAM_LANG,lang)
                .appendQueryParameter(PARAM_API_KEY,API_KEY).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static URL generateURLVideos(int id, String lang){
        URL result = null;
        Uri uri = Uri.parse(String.format(BASE_URL_VIDEOS,id)).buildUpon()
                .appendQueryParameter(PARAM_LANG,lang)
                .appendQueryParameter(PARAM_API_KEY,API_KEY).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static URL generateURL(int sort_by, int page, String lang){
         URL result = null;
         String chooseSort = "";
         if (sort_by == 0){
             chooseSort=POPULARITY_SORT;
         }else {
             chooseSort=TOP_RATED_SORT;
         }
         Uri uri = Uri.parse(BAS_URL).buildUpon()
                 .appendQueryParameter(PARAM_API_KEY,API_KEY)
                 .appendQueryParameter(PARAM_LANG,lang)
                 .appendQueryParameter(PARAM_SORT_BY,chooseSort)
                 .appendQueryParameter(PARAM_VOTE_COUNT,MIN_VOTE_COUNT)
                 .appendQueryParameter(PARAM_PAGE,Integer.toString(page)).build();
        try {
            result = new URL(uri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return result;
    }
    public static JSONObject getJSONObject(int sortBy,int page, String lang){
        JSONObject result = null;
        URL url = generateURL(sortBy, page,lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static JSONObject getJSONObjectReview(int id, String lang){
        JSONObject result = null;
        URL url = generateURLReviews(id,lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static JSONObject getJSONObjectVideo(int id, String lang){
        JSONObject result = null;
        URL url = generateURLVideos(id,lang);
        try {
            result = new JSONLoadTask().execute(url).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static class JSONLoader extends AsyncTaskLoader<JSONObject>{

        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;

        public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }

        public interface OnStartLoadingListener{
            void onStartLoading();
        }
        public JSONLoader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onStartLoadingListener!=null){
                onStartLoadingListener.onStartLoading();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null){
                return null;
            }
            String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject result = null;
            if (url == null) {
                return null;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
    }

    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject> {
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) {
                return result;
            }
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) urls[0].openConnection();
                InputStream inputStream = connection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);
                StringBuilder builder = new StringBuilder();
                String line = reader.readLine();
                while (line != null) {
                    builder.append(line);
                    line = reader.readLine();
                }
                result = new JSONObject(builder.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return result;
        }
    }
}
