package com.example.sam.movieproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.movieproject.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;

import static com.example.sam.movieproject.BuildConfig.API_KEY;

/**
 * Created by sam on 8/23/17.
 */

public class MovieDetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details);

        TextView tvOriginalTitle = (TextView) findViewById(R.id.original_title);
        ImageView ivPoster = (ImageView) findViewById(R.id.poster);
        TextView tvOverView = (TextView) findViewById(R.id.overview);
        TextView tvVoteAverage = (TextView) findViewById(R.id.vote_average);
        TextView tvReleaseDate = (TextView) findViewById(R.id.release_date);



        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");

        String title = movie.getmTitle();
        if (title == null) {
            tvOriginalTitle.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        tvOriginalTitle.setText(movie.getmTitle());


        Picasso.with(this)
                .load(movie.getPosterPath())
                .resize(185,
                275)
                .error(R.drawable.failure)
                .placeholder(R.drawable.loading)
                .into(ivPoster);


        String overView = movie.getmOverView();
        if (overView == null) {
            tvOverView.setTypeface(null, Typeface.BOLD_ITALIC);
            overView = getResources().getString(R.string.no_summary_bro);
        }
        tvOverView.setText(overView);
        tvVoteAverage.setText(movie.getDetailedVoteAverage());


        String releaseDate = movie.getmReleaseDate();
      if(releaseDate == null) {
            tvReleaseDate.setTypeface(null, Typeface.BOLD_ITALIC);
            releaseDate = getResources().getString(R.string.no_release_date_found);
        }
        tvReleaseDate.setText(releaseDate);
        String ID = movie.getmID();

    }

    }



