package com.example.sam.movieproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.sam.movieproject.model.Movie;
import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.model.OtherDataResult;
import com.example.sam.movieproject.model.Reviews;
import com.example.sam.movieproject.model.ReviewsResults;
import com.example.sam.movieproject.remote.APIService;
import com.example.sam.movieproject.remote.ApiClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by sam on 10/13/17.
 */

public class ReviewActivity extends AppCompatActivity{
    private static final String TAG = ReviewActivity.class.getSimpleName();
    public final String API_KEY = BuildConfig.API_KEY;

    private RecyclerView recyclerView;

    private ReviewsAdapter reviewsAdapter;

    List<Reviews> reviewsList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recyclerview_reviews);

        Intent intent = getIntent();

        String ID = (String) intent.getExtras().get("keyID");





        APIService apiService = ApiClient.getClient().create(APIService.class);
        Call<ReviewsResults> call = apiService.getMovieReviews(ID, API_KEY);
        call.enqueue(new Callback<ReviewsResults>() {
            @Override
            public void onResponse(Call<ReviewsResults> call, Response<ReviewsResults> response) {
                reviewsList = response.body().getResults();
                Log.i("list", response.body().toString());


                reviewsAdapter = new ReviewsAdapter(ReviewActivity.this, reviewsList);
                recyclerView = (RecyclerView) findViewById(R.id.reviews_reyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                recyclerView.setAdapter(reviewsAdapter);

            }


            @Override
            public void onFailure(Call<ReviewsResults> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });


    }
}