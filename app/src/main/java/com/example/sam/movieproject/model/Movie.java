package com.example.sam.movieproject.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

/**
 * Created by sam on 8/10/17.
 */

public class Movie implements Parcelable{
    public Movie () {}


    public String mTitle;

    public String getmTitle() {
        return mTitle;
    }

    public String mPoster;

    public String getPosterPath() {
        return mPoster;
    }
    public String getFullPosterLink(){
        final String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";
        return TMDB_POSTER_BASE_URL + mPoster;

    }

    public String mOverView;

    public String getmOverView() {
        return mOverView;
    }

    public Double mVoteAverage;

    public Double getmVoteAverage() {
        return mVoteAverage;
    }

    public String getDetailedVoteAverage() {
        return String.valueOf(getmVoteAverage()) + "/10";
    }

    public String mReleaseDate;


    public java.lang.String getmReleaseDate() {
        return mReleaseDate;
    }

    public String mID;

    public String getmID(){return mID; }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeString(mPoster);
        dest.writeString(mOverView);
        dest.writeValue(mVoteAverage);
        dest.writeString(mReleaseDate);
        dest.writeString(mID);
    }

        private Movie (Parcel in) {
        mTitle = in.readString();
        mPoster = in.readString();
        mOverView = in.readString();
        mVoteAverage = (Double) in.readValue(Double.class.getClassLoader());
        mReleaseDate = in.readString();
            mID = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    }

