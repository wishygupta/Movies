package com.movies.view;

import androidx.annotation.NonNull;
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
import com.movies.network.model.Movie;
import com.movies.view.adapter.MovieDetailActivity;
import com.movies.view.adapter.PlayingAdapter;
import com.movies.view.adapter.PopularAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PopularAdapter.MovieClickListener,PlayingAdapter.PlayingMovieClickListener {
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
    List<Movie> popularList = new ArrayList<>();
    int totalPages;
    int pageNum = 1;

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
        popularAdapter = new PopularAdapter(this);
        playingMovies.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        playingMovies.setAdapter(playingAdapter);
        final LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        popularMovies.setLayoutManager(llm);
        popularMovies.setAdapter(popularAdapter);
        popularAdapter.setClickListener(this);
        playingAdapter.setClickListener(this);
        popularMovies.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (llm.findLastCompletelyVisibleItemPosition() == popularList.size() - 1) {
                    if (pageNum == totalPages) {
                        return;
                    }
                    pageNum++;
                    fetchPopularMovies();
                }
            }
        });

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

        apiCall.popular(pageNum).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                presenter.stopLoading();
                if (response.body() != null) {
                    totalPages = response.body().getTotalPages();
                    pageNum = response.body().getPageNum();
                    popularList.addAll(response.body().getMovieList());
                    popularAdapter.setDataList(popularList);
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
    public void playingMovieClicked(int moviedId) {
        moveToNextScreen(moviedId);
    }

    @Override
    public void movieClicked(int moviedId) {
        moveToNextScreen(moviedId);
    }

    private void moveToNextScreen(int movId){
        Intent intent = new Intent(this, MovieDetailActivity.class);
        intent.putExtra("movieId", movId);
        startActivity(intent);
    }
}
