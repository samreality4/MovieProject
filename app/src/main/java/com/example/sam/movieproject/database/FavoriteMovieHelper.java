package com.example.sam.movieproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by sam on 9/27/17.
 */

public class FavoriteMovieHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FavoriteMovieDB";



    public FavoriteMovieHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    @Override
public void onCreate(SQLiteDatabase db) {

    db.execSQL(FavorContract.MovieEntry.CREATE_FAVORITE_TABLE);
}


@Override
public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
        db.execSQL("DROP TABLE IF EXIST" + FavorContract.MovieEntry.TABLE_FAVOR);
        onCreate(db);
    }
}








}
