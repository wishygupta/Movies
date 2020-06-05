package com.movies.view.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.movies.R;
import com.movies.network.NetworkConstant;
import com.movies.network.model.Movie;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wishy.gupta on 24-02-2018.
 */

public class PlayingAdapter extends RecyclerView.Adapter<PlayingAdapter.ViewHolder> {
    List<Movie> movieList=new ArrayList<>();
    PlayingMovieClickListener clickListener;
    public PlayingAdapter() {
    }

    public void setDataList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_playing, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (movieList.get(position).getPosterPath() != null) {
            StringBuilder imgUrl = new StringBuilder();
            imgUrl.append(NetworkConstant.IMG_URL);
            imgUrl.append("w200");
            imgUrl.append(movieList.get(position).getPosterPath());
            Glide.with(holder.itemView.getContext()).load(imgUrl.toString()).
                    apply(new RequestOptions().override(150, 250)).into(holder.movieImg);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.playingMovieClicked(movieList.get(position).getMovieId());
            }
        });

    }

    public void setClickListener(PlayingMovieClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieImg)
        AppCompatImageView movieImg;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public interface PlayingMovieClickListener {
        void playingMovieClicked(int moviedId);
    }

}
