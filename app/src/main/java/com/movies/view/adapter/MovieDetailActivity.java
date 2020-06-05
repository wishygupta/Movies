package com.movies.view.adapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.movies.AppPresenter;
import com.movies.R;
import com.movies.dag.MyApplication;
import com.movies.network.NetworkCall;
import com.movies.network.NetworkConstant;
import com.movies.network.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
                if(response.body()!=null){
                    Movie movie=response.body();
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
                    if(movie.getOverview()!=null)
                        movieDescription.setText(movie.getOverview());
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                presenter.stopLoading();
            }
        });

    }

    private void init() {
        presenter.createLoadingDialog(this);
    }

}
