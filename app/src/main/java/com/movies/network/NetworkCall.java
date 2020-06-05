package com.movies.network;

import com.movies.network.model.ApiResponse;
import com.movies.network.model.Movie;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface NetworkCall {
    @GET(NetworkConstant.PLAYING)
    Call<ApiResponse> currentPlaying();

    @GET(NetworkConstant.POPULAR)
    Call<ApiResponse> popular(@Query("page") int pageNum);


    @GET(NetworkConstant.MOVIE_DETAIL)
    Call<Movie> movieDetail(@Path("MOVIE_ID") int itemId);


}
