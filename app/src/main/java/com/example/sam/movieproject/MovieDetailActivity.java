package com.example.sam.movieproject;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.movieproject.model.Movie;
import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.remote.APIService;
import com.example.sam.movieproject.remote.ApiUtils;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import retrofit2.Response;

import static com.example.sam.movieproject.BuildConfig.API_KEY;

/**
 * Created by sam on 8/23/17.
 */

public class MovieDetailActivity extends AppCompatActivity {

    private APIService mAPIService;

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detailed,menu);
        return super.onCreateOptionsMenu(menu);}

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
        if (releaseDate == null) {
            tvReleaseDate.setTypeface(null, Typeface.BOLD_ITALIC);
            releaseDate = getResources().getString(R.string.no_release_date_found);
        }
        tvReleaseDate.setText(releaseDate);

        String ID = movie.getmID();
        String trailerKeyUrl = "http://api.themoviedb.org/3/movie/" + ID + "/videos?api_key=" + API_KEY;

        mAPIService = ApiUtils.getAPIService();


    public void openBrowser(View view) {
        String url = OtherData.YOUTUBE_TRAILER_LINK;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_BROWSABLE);
        intent.setData(Uri.parse(url));
        startActivity(intent);
}
public void sendPost(String key) {
    mAPIService.savePost(key).enqueue(new Callback<Post>(){
        @Override
        public void onResponse(Call<Post> call, Response<Post> response) {
            if(response.isSuccessful()) {
                showResponse(response.body().toString());
                Log.i(TAG, "post submitted to API." + reponse.body().toString());
            }
        }
        @Override
        public void onFailure(Call<Post> call, Throwable t) {
            Log.e(TAG, "Unable to sumbit post to API");
        }
    });
    public void showResponse(String response) {
        if(mResponseTv.setText(response));
    }
}


}

}
