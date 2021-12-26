package com.example.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.movieapp.adapters.ReviewAdapter;
import com.example.movieapp.adapters.TrailerAdapter;
import com.example.movieapp.data.FavoriteMovie;
import com.example.movieapp.data.MainViewModel;
import com.example.movieapp.data.Movie;
import com.example.movieapp.data.Review;
import com.example.movieapp.data.Video;
import com.example.movieapp.utils.JSONUtils;
import com.example.movieapp.utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageViewBigPoster;
    private TextView textViewOriginalTitle;
    private TextView textViewVote;
    private TextView textViewReleaseDate;
    private TextView textViewOverview;
    private ImageView imageViewStar;
    private MainViewModel viewModel;
    private Movie movie;
    private int id;
    private FavoriteMovie favoriteMovie;
    private RecyclerView recyclerViewTrailers;
    private RecyclerView recyclerViewReviews;
    private TrailerAdapter trailerAdapter;
    private ReviewAdapter reviewAdapter;
    private ScrollView scrollViewDetail;
    private static String lang;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        lang = Locale.getDefault().getLanguage();
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        imageViewBigPoster = findViewById(R.id.imageViewBigPoaster);
        textViewOriginalTitle = findViewById(R.id.textViewOriginalTitle);
        textViewReleaseDate = findViewById(R.id.textViewReleaseDate);
        textViewVote = findViewById(R.id.textViewRating);
        textViewOverview = findViewById(R.id.textViewOverView);
        imageViewStar = findViewById(R.id.imageViewStarOn);
        recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        scrollViewDetail = findViewById(R.id.scrollViewDetail);

        Intent intent = getIntent();
        if (intent!=null&&intent.hasExtra("id")){
            id = intent.getIntExtra("id",0);
        }
        else {
            finish();
        }
        movie = viewModel.GetMovieById(id);
//        Log.i("aaa",movie.getBigPosterPath());
        Picasso.get().load(movie.getBigPosterPath()).placeholder(R.drawable.ic_baseline_image_24).into(imageViewBigPoster);
        textViewOriginalTitle.setText(movie.getOriginal_title());
        textViewReleaseDate.setText(movie.getReleaseDate());
        textViewVote.setText(Double.toString(movie.getVote_average()));
        textViewOverview.setText(movie.getOverview());
        setFavoriteMovie();
        reviewAdapter = new ReviewAdapter();
        trailerAdapter = new TrailerAdapter();
        recyclerViewReviews.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewTrailers.setLayoutManager(new LinearLayoutManager(this));
        JSONObject jsonObjectReviews = NetworkUtils.getJSONObjectReview(id,lang);
        JSONObject jsonObjectVideos = NetworkUtils.getJSONObjectVideo(id,lang);
        ArrayList<Review> reviews = JSONUtils.parseJSONReviews(jsonObjectReviews);
        ArrayList<Video> videos = JSONUtils.parseJSONVideos(jsonObjectVideos);
        reviewAdapter.setReviews(reviews);
        trailerAdapter.setTrailers(videos);
        recyclerViewTrailers.setAdapter(trailerAdapter);
        recyclerViewReviews.setAdapter(reviewAdapter);
        trailerAdapter.setOnTrailerClickListener(new TrailerAdapter.OnTrailerClickListener() {
            @Override
            public void onClick(String link) {
                Intent intentToYoutube = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                startActivity(intentToYoutube);
            }
        });
        scrollViewDetail.smoothScrollTo(0,0);
    }

    public void onClickAddToFavorite(View view) {
        if (favoriteMovie==null){
            viewModel.InsertFavoriteMovie(new FavoriteMovie(movie));
        }else {
            viewModel.DeleteFavoriteMovie(favoriteMovie);
        }
        setFavoriteMovie();
    }

    public void setFavoriteMovie(){
        favoriteMovie = viewModel.GetFavoriteMovieById(id);
        if (favoriteMovie==null){
            imageViewStar.setImageResource(R.drawable.ic_star_off);
        }else {
            imageViewStar.setImageResource(R.drawable.ic_star_on);
        }
    }
}