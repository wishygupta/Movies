package com.movies.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.movies.AppPresenter;
import com.movies.R;
import com.movies.dag.MyApplication;
import com.movies.network.NetworkCall;
import com.movies.network.model.ApiResponse;
import com.movies.view.adapter.MovieDetailActivity;
import com.movies.view.adapter.PlayingAdapter;
import com.movies.view.adapter.PopularAdapter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PopularAdapter.MovieClickListener {
    @Inject
    NetworkCall apiCall;
    @Inject
    AppPresenter presenter;
    PlayingAdapter playingAdapter;
    PopularAdapter popularAdapter;
    @BindView(R.id.playingMovies)
    RecyclerView playingMovies;
    @BindView(R.id.popularMovies)
    RecyclerView popularMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        ((MyApplication) this.getApplication()).getAppComponent().inject(this);
        init();
        fetchPlayingMovies();
    }

    private void init() {
        presenter.createLoadingDialog(this);
        playingAdapter = new PlayingAdapter();
        popularAdapter = new PopularAdapter();
        playingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        playingMovies.setAdapter(playingAdapter);
        popularMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        popularMovies.setAdapter(popularAdapter);
        popularAdapter.setClickListener(this);
    }

    private void fetchPlayingMovies() {
        presenter.showLoading();
        apiCall.currentPlaying().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                presenter.stopLoading();
                if (response.body().getMovieList() != null) {
                    playingAdapter.setDataList(response.body().getMovieList());
                    fetchPopularMovies();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                presenter.stopLoading();

            }
        });
    }

    private void fetchPopularMovies() {
        presenter.showLoading();
        apiCall.popular().enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                presenter.stopLoading();
                if (response.body().getMovieList() != null) {
                    popularAdapter.setDataList(response.body().getMovieList());
                    popularAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                presenter.stopLoading();
            }
        });
    }

    @Override
    public void movieClicked(int moviedId) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieId", moviedId);
        startActivity(intent);
    }
}
