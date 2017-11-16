package com.example.sam.movieproject.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sam on 10/18/17.
 */

public class FavorContract {
    public static final String AUTHORITY = "com.example.sam.movieproject";
    public static final String PATH_TASKS = "FavoriteMovieDB";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/");


    public static final class MovieEntry implements BaseColumns {

        public static final String TABLE_FAVOR = "Favorite_Movies";
        public static final String KEY_FAVOR_POSTER = "poster_path";
        public static final String KEY_FAVOR_ID = "id";
        public static final String KEY_FAVOR_TITLE = "original_title";
        public static final String KEY_FAVOR_OVERVIEW = "overview";
        public static final String KEY_VOTING_AVERAGE = "vote_average";
        public static final String KEY_RELEASE_DATE = "release_date";

        public static String CREATE_FAVORITE_TABLE = "CREATE TABLE " + TABLE_FAVOR +
                " (" + _ID + " INTEGER PRIMARY KEY," + KEY_FAVOR_ID + KEY_FAVOR_POSTER + " TEXT,"
                + " TEXT," + KEY_FAVOR_TITLE + " TEXT," + KEY_FAVOR_OVERVIEW + " TEXT,"
                + KEY_VOTING_AVERAGE + " TEXT," + KEY_RELEASE_DATE + " TEXT)";
        public static String[] Columns = new String[]{_ID, KEY_FAVOR_ID, KEY_FAVOR_POSTER, KEY_FAVOR_POSTER, KEY_FAVOR_OVERVIEW, KEY_VOTING_AVERAGE, KEY_RELEASE_DATE

        };

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();
        public static final String CONTENT_DIR_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/"  + AUTHORITY + "/" + TABLE_FAVOR;
        public static final String CONTENT_ITEM_TYPE=
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_FAVOR;

        public static Uri buildMovieUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI,id);
        }

    }
}