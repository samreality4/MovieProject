package com.example.sam.movieproject.remote;

import android.graphics.Movie;

import com.example.sam.movieproject.MovieDetailActivity;
import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.model.OtherDataResult;
import com.example.sam.movieproject.model.ReviewsResults;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import static android.R.attr.key;

/**
 * Created by sam on 10/1/17.
 */

public interface APIService {

   @GET("movie/{id}/videos")
   Call <OtherDataResult> getMovieTrailer(@Path("id") String ID, @Query("api_key") String API_KEY);

   @GET("movie/{id}/reviews")
   Call <ReviewsResults> getMovieReviews(@Path("id") String ID, @Query("api_key") String API_KEY);
}
