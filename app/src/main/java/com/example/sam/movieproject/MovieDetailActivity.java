package com.example.sam.movieproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.test.espresso.core.deps.guava.base.Throwables;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sam.movieproject.model.Movie;
import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.model.OtherDataResult;
import com.example.sam.movieproject.model.Reviews;
import com.example.sam.movieproject.remote.APIService;
import com.example.sam.movieproject.remote.ApiClient;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static android.R.id.checkbox;
import static android.R.id.list;

/**
 * Created by sam on 8/23/17.
 */

public class MovieDetailActivity extends AppCompatActivity implements OtherDataAdapter.trailerClickListener{
    List<Movie> list = new ArrayList<>();

    private static final String TAG = MovieDetailActivity.class.getSimpleName();


    public final String API_KEY = BuildConfig.API_KEY;

   private RecyclerView recyclerView;

    private OtherDataAdapter otherDataAdapter;
    List<OtherData> trailerList;
    List<Movie> movieList;
    String keyID;
    String title;
    String overView;
    String releaseDate;
    String votingAverage;
    Context context;

//todo add onitemselectedlistener to listen to the state.


        @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.movie_details);


            TextView tvOriginalTitle = (TextView) findViewById(R.id.original_title);
            ImageView ivPoster = (ImageView) findViewById(R.id.poster);
            TextView tvOverView = (TextView) findViewById(R.id.overview);
            TextView tvVoteAverage = (TextView) findViewById(R.id.vote_average);

            Button readReview = (Button) findViewById(R.id.read_reviews);
            readReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MovieDetailActivity.this, ReviewActivity.class);
                    intent.putExtra("keyID", keyID);
                    Log.i("keyID", keyID);
                    startActivity(intent);

                }
            });


        TextView tvReleaseDate = (TextView) findViewById(R.id.release_date);


        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra("Movie");

        title = movie.getmTitle();

        if (title == null) {
            tvOriginalTitle.setTypeface(null, Typeface.BOLD_ITALIC);
        }

        tvOriginalTitle.setText(title);



        Picasso.with(this)
                .load(movie.getPosterPath())
                .resize(185,
                        275)
                .error(R.drawable.failure)
                .placeholder(R.drawable.loading)
                .into(ivPoster);


        overView = movie.getmOverView();
        if (overView == null) {
            tvOverView.setTypeface(null, Typeface.BOLD_ITALIC);
            overView = getResources().getString(R.string.no_summary_bro);
        }
        tvOverView.setText(overView);

        votingAverage = movie.getDetailedVoteAverage();

        tvVoteAverage.setText(votingAverage);

            releaseDate = movie.getmReleaseDate();
        if (releaseDate == null) {
            tvReleaseDate.setTypeface(null, Typeface.BOLD_ITALIC);
            releaseDate = getResources().getString(R.string.no_release_date_found);
        }
        tvReleaseDate.setText(releaseDate);

        String ID = movie.getmID();
        keyID = movie.getmID();


        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<OtherDataResult> call = apiService.getMovieTrailer(ID,API_KEY);
        call.enqueue(new Callback<OtherDataResult>() {
            @Override
            public void onResponse(Call<OtherDataResult> call, Response <OtherDataResult> response) {
                trailerList = response.body().getResults();
                Log.i("list",response.body().toString());




                otherDataAdapter = new OtherDataAdapter(MovieDetailActivity.this,trailerList);
                recyclerView = (RecyclerView) findViewById(R.id.thumb_recylerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
                recyclerView.setAdapter(otherDataAdapter);

                }




            @Override
            public void onFailure(Call<OtherDataResult> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

            final CheckBox checkBox = (CheckBox) findViewById(R.id.favorited_checkBox);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
            final SharedPreferences.Editor editor = preferences.edit();
            if (preferences.contains("checked"+ keyID) && preferences.getBoolean("checked" + keyID, false)) {
                checkBox.setChecked(true);
            } else {
                checkBox.setChecked(false);
            }


            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        editor.putBoolean("checked" + keyID, true);
                        editor.apply();
                        FavoriteMovieHelper mFmHelper = new FavoriteMovieHelper(getApplicationContext());
                        SQLiteDatabase db = mFmHelper.getWritableDatabase();

                        ContentValues values = new ContentValues();
                        values.put(FavorContract.MovieEntry.KEY_FAVOR_ID,keyID);
                        values.put(FavorContract.MovieEntry.KEY_FAVOR_OVERVIEW,overView);
                        values.put(FavorContract.MovieEntry.KEY_FAVOR_TITLE, title);
                        values.put(FavorContract.MovieEntry.KEY_RELEASE_DATE, releaseDate);
                        values.put(FavorContract.MovieEntry.KEY_VOTING_AVERAGE,votingAverage);

                        db.insert(FavorContract.MovieEntry.TABLE_FAVOR,null, values);
                        db.close();

                    } else {
                        editor.putBoolean("checked" + keyID, false);
                        editor.apply();
                    }


                }
            });

    }




    public void onTrailerItemClick(View v, int position) {
        OtherData otherData = trailerList.get(position);
        String YOUTUBE_TRAILER_LINK="https://www.youtube.com/watch?v=";
        String url = YOUTUBE_TRAILER_LINK+otherData.getKey();
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }



}


