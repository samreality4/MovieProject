package com.example.sam.movieproject;

import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.movieproject.model.Movie;

/**
 * Created by sam on 11/11/17.
 */

public class CustomViewHolder extends RecyclerView.ViewHolder {
    TextView movieTitle;
    TextView overView;
    TextView voteAverage;
    TextView releaseDate;
    ImageView moviePoster;
    ImageView moviePoster1;


    public CustomViewHolder(View itemView) {
        super(itemView);
        movieTitle = (TextView) itemView.findViewById(R.id.original_title);
        overView = (TextView) itemView.findViewById(R.id.overview);
        releaseDate = (TextView) itemView.findViewById(R.id.release_date);
        voteAverage = (TextView) itemView.findViewById(R.id.vote_average);
        moviePoster = (ImageView) itemView.findViewById(R.id.left_image1);
        moviePoster1 = (ImageView) itemView.findViewById(R.id.poster);


    }

    public void setData(Cursor c){
        Movie movie = new Movie();
    movie.mTitle = c.getString(c.getColumnIndex("original_title"));
    movie.mOverView = c.getString(c.getColumnIndex("overview"));
        movie.mReleaseDate = c.getString(c.getColumnIndex("release_date"));
        movie.mVoteAverage = c.getDouble(c.getColumnIndex("vote_average"));
        movie.mID = c.getString(c.getColumnIndex("id"));
        movie.mPoster = c.getString(c.getColumnIndex("poster_path"));

    }

}
