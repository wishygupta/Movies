package com.movies.view.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.flexbox.FlexboxLayout;
import com.movies.AppPresenter;
import com.movies.R;
import com.movies.dag.MyApplication;
import com.movies.network.NetworkCall;
import com.movies.network.NetworkConstant;
import com.movies.network.model.Genre;
import com.movies.network.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity {
    @Inject
    NetworkCall apiCall;
    @Inject
    AppPresenter presenter;
    @BindView(R.id.movieImg)
    ImageView movieImg;
    @BindView(R.id.movieName)
    AppCompatTextView movieName;
    @BindView(R.id.movieDate)
    AppCompatTextView movieDate;
    @BindView(R.id.movieDuration)
    AppCompatTextView movieDuration;
    @BindView(R.id.movieDescription)
    AppCompatTextView movieDescription;
    @BindView(R.id.genresView)
    FlexboxLayout genresView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        ButterKnife.bind(this);
        ((MyApplication) this.getApplication()).getAppComponent().inject(this);
        init();
        fetchMovieDetail();
    }

    private void fetchMovieDetail() {
        presenter.showLoading();
        int movId = getIntent().getIntExtra("movieId", 0);
        apiCall.movieDetail(movId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                presenter.stopLoading();
                if (response.body() != null) {
                    Movie movie = response.body();
                    if (movie.getPosterPath() != null) {
                        StringBuilder imgUrl = new StringBuilder();
                        imgUrl.append(NetworkConstant.IMG_URL);
                        imgUrl.append("w200");
                        imgUrl.append(movie.getPosterPath());
                        Glide.with(MovieDetailActivity.this).load(imgUrl.toString()).into(movieImg);
                    }
                    if (movie.getTitle() != null)
                        movieName.setText(movie.getTitle());
                    if (movie.getReleaseDate() != null) {
                        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd yyyy");
                        Date date = null;
                        try {
                            date = dt.parse(movie.getReleaseDate());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        movieDate.setText(dt1.format(date));
                    }
                    movieDuration.setText(movie.getVotes() + " Votes");
                    if (movie.getOverview() != null)
                        movieDescription.setText(movie.getOverview());
                    if (movie.getGenres() != null)
                        addGenresToLayout(movie.getGenres());

                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                presenter.stopLoading();
            }
        });

    }

    private void addGenresToLayout(List<Genre> genres) {
        for (int i = 0; i < genres.size(); i++) {
            TextView genre = new TextView(this);
            FlexboxLayout.LayoutParams params=new FlexboxLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(10,0,10,0);
            genre.setLayoutParams(params);
            genre.setText(genres.get(i).getName());
            genre.setTextColor(ContextCompat.getColor(this, android.R.color.white));
            genre.setPadding(15, 10, 15, 10);
            genre.setBackground(getResources().getDrawable(R.drawable.rounded_rectangle));
            genresView.addView(genre);


        }
    }

    private void init() {
        presenter.createLoadingDialog(this);
    }

}
