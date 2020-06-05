package com.movies.network;

public class NetworkConstant {

    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String POPULAR = "movie/popular?api_key=55957fcf3ba81b137f8fc01ac5a31fb5&language=en-US&page=1";
    public static final String MOVIE_DETAIL = "movie/{MOVIE_ID}?api_key=55957fcf3ba81b137f8fc01ac5a31fb5";
    public static final String PLAYING = "movie/now_playing?language=en-US&page=undefined&api_key=55957fcf3ba81b137f8fc01ac5a31fb5";
    public static final String IMG_URL = "https://image.tmdb.org/t/p/";

}
