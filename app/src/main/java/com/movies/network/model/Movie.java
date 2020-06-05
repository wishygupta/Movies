package com.movies.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("id")
    @Expose
    int movieId;
    @SerializedName("poster_path")
    @Expose
    String posterPath;
    @SerializedName("original_title")
    @Expose
    String title;
    @SerializedName("release_date")
    @Expose
    String releaseDate;
    @SerializedName("vote_count")
    @Expose
    int votes;
    @SerializedName("vote_average")
    @Expose
    float popularity;
    @SerializedName("overview")
    @Expose
    String overview;
    @SerializedName("genres")
    @Expose
    List<Genre> genres;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public float getPopularity() {
        return popularity;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public String getOverview() {
        return overview;
    }

    public List<Genre> getGenres() {
        return genres;
    }
}
