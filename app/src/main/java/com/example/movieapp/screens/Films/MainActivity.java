package com.example.movieapp.screens.Films;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp.DetailActivity;
import com.example.movieapp.FavoriteActivity;
import com.example.movieapp.R;
import com.example.movieapp.adapters.MoviesAdapter;
import com.example.movieapp.data.MainViewModel;
import com.example.movieapp.data.Movie;
import com.example.movieapp.utils.JSONUtils;
import com.example.movieapp.utils.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<JSONObject> {
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private Switch aSwitch;
    private TextView textViewPopularity;
    private TextView textViewTopRated;
    private MainViewModel viewModel;
    private static final int loaderId = 222;
    private LoaderManager loaderManager;
    private ProgressBar progressBar;
    private static String lang;

    private static int page = 1;
    private static int methodOfSort;
    private static boolean isLoading = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_items,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu:
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.fav_menu:
                Intent intentFav = new Intent(this, FavoriteActivity.class);
                startActivity(intentFav);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public int getCountOfColumn(){
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = (int) (displayMetrics.widthPixels/ displayMetrics.density);
        return width/185>=2?width/185:2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lang = Locale.getDefault().getLanguage();
        loaderManager = LoaderManager.getInstance(this);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
//        URL url = NetworkUtils.generateURL(NetworkUtils.POPULARITY,0);
//        Log.i("aaa",url.toString());
        progressBar = findViewById(R.id.progressBarLoading);
        recyclerView = findViewById(R.id.moviesRecyclerView);
        aSwitch = findViewById(R.id.switchFilms);
        textViewPopularity = findViewById(R.id.sortByPopularity);
        textViewTopRated = findViewById(R.id.sortByTopRated);
        recyclerView.setLayoutManager(new GridLayoutManager(this, getCountOfColumn()));
        moviesAdapter = new MoviesAdapter();
        recyclerView.setAdapter(moviesAdapter);
        aSwitch.setChecked(true);
        aSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            page = 1;
            setMethodOfSort(isChecked);
        });
        aSwitch.setChecked(false);
        moviesAdapter.setOnPosterClickListener(new MoviesAdapter.OnPosterClickListener() {
            @Override
            public void onPosterClick(int position) {
                Movie movie = moviesAdapter.getMovies().get(position);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id",movie.getId());
                startActivity(intent);
            }
        });
        moviesAdapter.setOnReachListener(new MoviesAdapter.OnReachListener() {
            @Override
            public void onReach() {
                if (!isLoading) {
                    downloadData(methodOfSort,page);
                }
            }
        });

        LiveData<List<Movie>> moviesFromLiveData = viewModel.getMovies();
        moviesFromLiveData.observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                if (page==1){
                    moviesAdapter.setMovies(movies);
                }
            }
        });
    }

    private void setMethodOfSort(boolean isChecked) {

        if (isChecked){
            methodOfSort = NetworkUtils.TOP_RATED;
            textViewTopRated.setTextColor(getResources().getColor(R.color.purple_200));
            textViewPopularity.setTextColor(getResources().getColor(R.color.white));
        }else {
            methodOfSort = NetworkUtils.POPULARITY;
            textViewPopularity.setTextColor(getResources().getColor(R.color.purple_200));
            textViewTopRated.setTextColor(getResources().getColor(R.color.white));
        }
        downloadData(methodOfSort,page);
    }

    private void downloadData(int methodOfSort, int page){
        URL url = NetworkUtils.generateURL(methodOfSort,page,lang);
        Bundle bundle = new Bundle();
        bundle.putString("url", url.toString());
        loaderManager.restartLoader(loaderId,bundle,this);
    }
    public void onClickTextPopularity(View view) {
        setMethodOfSort(false);
        aSwitch.setChecked(false);
    }

    public void onClickTextTopRated(View view) {
        setMethodOfSort(true);
        aSwitch.setChecked(true);
    }

    @NonNull
    @Override
    public Loader<JSONObject> onCreateLoader(int id, @Nullable  Bundle args) {
        NetworkUtils.JSONLoader jsonLoader = new NetworkUtils.JSONLoader(this,args);
        jsonLoader.setOnStartLoadingListener(new NetworkUtils.JSONLoader.OnStartLoadingListener() {
            @Override
            public void onStartLoading() {
                isLoading=true;
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        return jsonLoader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<JSONObject> loader, JSONObject data) {
        ArrayList<Movie> movies = JSONUtils.parseJSON(data);
        if (!movies.isEmpty()){
            if (page==1) {
                viewModel.DeleteAllMovies();
                moviesAdapter.clear();
            }
            for (Movie movie:movies) {
                viewModel.InsertMovie(movie);
            }
            moviesAdapter.addMovies(movies);
            page++;
        }
        isLoading=false;
        progressBar.setVisibility(View.INVISIBLE);
        loaderManager.destroyLoader(loaderId);
    }

    @Override
    public void onLoaderReset(@NonNull  Loader<JSONObject> loader) {

    }
}