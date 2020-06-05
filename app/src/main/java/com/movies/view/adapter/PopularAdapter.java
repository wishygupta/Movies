package com.movies.view.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.movies.R;
import com.movies.network.NetworkConstant;
import com.movies.network.model.Movie;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wishy.gupta on 24-02-2018.
 */

public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {
    List<Movie> movieList = new ArrayList<>();
    MovieClickListener clickListener;

    public PopularAdapter() {
    }

    public void setDataList(List<Movie> movieList) {
        this.movieList=movieList;
        notifyDataSetChanged();
    }

    public void setClickListener(MovieClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.element_popular, parent, false);
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
            Glide.with(holder.itemView.getContext()).load(imgUrl.toString()).into(holder.movieImg);
        }
        if (movieList.get(position).getTitle() != null)
            holder.movieName.setText(movieList.get(position).getTitle());
        if (movieList.get(position).getReleaseDate() != null) {
            SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat dt1 = new SimpleDateFormat("MMM dd yyyy");
            Date date = null;
            try {
                date = dt.parse(movieList.get(position).getReleaseDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.movieDate.setText(dt1.format(date));
        }
        holder.movieDuration.setText(movieList.get(position).getVotes() + " Votes");
        holder.rating.setText(movieList.get(position).getPopularity() * 10 + "");
        if (movieList.get(position).getPopularity() > 5) {
            holder.rating.setFinishedStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_dark));
            holder.rating.setUnfinishedStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_green_light));
        } else {
            holder.rating.setUnfinishedStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_orange_dark));
            holder.rating.setFinishedStrokeColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.holo_orange_light));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null)
                    clickListener.movieClicked(movieList.get(position).getMovieId());
            }
        });
    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.movieImg)
        ImageView movieImg;
        @BindView(R.id.movieName)
        TextView movieName;
        @BindView(R.id.movieDate)
        TextView movieDate;
        @BindView(R.id.movieDuration)
        TextView movieDuration;
        @BindView(R.id.rating)
        DonutProgress rating;


        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    public interface MovieClickListener {
        void movieClicked(int moviedId);
    }


}
