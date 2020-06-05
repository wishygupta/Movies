package com.movies.dag;


import com.movies.view.MainActivity;
import com.movies.view.adapter.MovieDetailActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by wishy.gupta on 13-03-2018.
 */

@Singleton
@Component(modules = {AppModule.class, AppClient.class})
public interface AppComponent {

    void inject(MainActivity activity);
    void inject(MovieDetailActivity activity);




}