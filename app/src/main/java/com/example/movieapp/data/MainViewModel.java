package com.example.movieapp.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {
//    private static final String API_KEY = "4e1808f48673a589b0697bd4184f1edc";
//    private static final String POPULARITY_SORT = "popularity.desc";
//    private static final String TOP_RATED_SORT = "vote_average.desc";
//    private static final String MIN_VOTE_COUNT = "10000";
//    private static String lang = Locale.getDefault().getLanguage();
//
//
//    public static final int POPULARITY = 0;
//    public static final int TOP_RATED = 1;


//    private CompositeDisposable compositeDisposable;
    private static MovieDatabase database;
    private LiveData<List<Movie>> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;
    public MainViewModel(@NonNull Application application) {
        super(application);
        database = MovieDatabase.getInstance(getApplication());
        movies = database.moviesDao().getAllMovies();
        favoriteMovies = database.moviesDao().getAllFavoriteMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }
    public LiveData<List<FavoriteMovie>> getFavoriteMovies() {
        return favoriteMovies;
    }



//    public void loadData(int methodOfSort,int page){
//        compositeDisposable = new CompositeDisposable();
//        String chooseSort = "";
//        if (methodOfSort == 0){
//            chooseSort=POPULARITY_SORT;
//        }else {
//            chooseSort=TOP_RATED_SORT;
//        }
//        ApiFactory apiFactory = ApiFactory.getInstance();
//        ApiService apiService = apiFactory.getApiService();
//        Disposable disposable = apiService.getFilms(API_KEY,lang,chooseSort,Integer.toString(page),MIN_VOTE_COUNT)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Consumer<Results>() {
//                    @Override
//                    public void accept(Results results) throws Exception {
//
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//
//                    }
//                });
//
//    }



    public Movie GetMovieById(int movieId){
        try {
            return new GetMovieTaskById().execute(movieId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetMovieTaskById extends AsyncTask<Integer,Void,Movie>{

        @Override
        protected Movie doInBackground(Integer... strings) {
            if (strings!=null&&strings.length>0){
                return database.moviesDao().getMovieById(strings[0]);
            }
            return null;
        }
    }



    public void InsertMovie(Movie movie){
        new InsertMovieTask().execute(movie);
    }
    private static class InsertMovieTask extends AsyncTask<Movie,Void,Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies!=null&&movies.length>0){
                database.moviesDao().insertMovie(movies[0]);
            }
            return null;
        }
    }


    public void DeleteMovie(Movie movie){
        new DeleteMovieTask().execute(movie);
    }
    private static class DeleteMovieTask extends AsyncTask<Movie,Void,Void>{

        @Override
        protected Void doInBackground(Movie... movies) {
            if (movies!=null&&movies.length>0){
                database.moviesDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }


    public void DeleteAllMovies(){
        new DeleteAllMovieTask().execute();
    }
    private static class DeleteAllMovieTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... movies) {
                database.moviesDao().deleteAllMovies();
            return null;
        }
    }












    public FavoriteMovie GetFavoriteMovieById(int movieId){
        try {
            return new GetFavoriteMovieTaskById().execute(movieId).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static class GetFavoriteMovieTaskById extends AsyncTask<Integer,Void,FavoriteMovie>{

        @Override
        protected FavoriteMovie doInBackground(Integer... strings) {
            if (strings!=null&&strings.length>0){
                return database.moviesDao().getFavoriteMovieById(strings[0]);
            }
            return null;
        }
    }



    public void InsertFavoriteMovie(FavoriteMovie movie){
        new InsertFavoriteMovieTask().execute(movie);
    }
    private static class InsertFavoriteMovieTask extends AsyncTask<FavoriteMovie,Void,Void>{

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies!=null&&movies.length>0){
                database.moviesDao().insertFavoriteMovie(movies[0]);
            }
            return null;
        }
    }


    public void DeleteFavoriteMovie(FavoriteMovie movie){
        new DeleteFavoriteMovieTask().execute(movie);
    }
    private static class DeleteFavoriteMovieTask extends AsyncTask<FavoriteMovie,Void,Void>{

        @Override
        protected Void doInBackground(FavoriteMovie... movies) {
            if (movies!=null&&movies.length>0){
                database.moviesDao().deleteFavoriteMovie(movies[0]);
            }
            return null;
        }
    }


    public void DeleteAllFavoriteMovies(){
        new DeleteAllFavoriteMovieTask().execute();
    }
    private static class DeleteAllFavoriteMovieTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... movies) {
            database.moviesDao().deleteAllFavoriteMovies();
            return null;
        }
    }


//    @Override
//    protected void onCleared() {
//        compositeDisposable.dispose();
//        super.onCleared();
//    }
}
