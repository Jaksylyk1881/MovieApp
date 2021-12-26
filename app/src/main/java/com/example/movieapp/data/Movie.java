package com.example.movieapp.data;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
@Entity(tableName = "movies")
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private int uniqueId;
    private int id;
    private String original_title;
    private String overview;
    private String backdropPath;
    private String posterPath;
    private String releaseDate;
    private double vote_average;
    private double popularity;
    private String bigPosterPath;

    public Movie(int uniqueId,int id, String original_title, String overview, String backdropPath, String posterPath, String bigPosterPath, String releaseDate, double vote_average, double popularity) {
        this.uniqueId = uniqueId;
        this.id = id;
        this.original_title = original_title;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.releaseDate = releaseDate;
        this.vote_average = vote_average;
        this.popularity = popularity;
    }

    @Ignore
    public Movie(int id, String original_title, String overview, String backdropPath, String posterPath, String bigPosterPath, String releaseDate, double vote_average, double popularity) {
        this.id = id;
        this.original_title = original_title;
        this.overview = overview;
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.bigPosterPath = bigPosterPath;
        this.releaseDate = releaseDate;
        this.vote_average = vote_average;
        this.popularity = popularity;
    }

    public int getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(int uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getBigPosterPath() {
        return bigPosterPath;
    }

    public void setBigPosterPath(String bigPosterPath) {
        this.bigPosterPath = bigPosterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }
}
