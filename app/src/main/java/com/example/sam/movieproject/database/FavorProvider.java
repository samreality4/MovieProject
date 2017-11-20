package com.example.sam.movieproject.database;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.sam.movieproject.MovieDetailActivity;

import java.net.URI;

/**
 * Created by sam on 10/18/17.
 */

public class FavorProvider extends ContentProvider {

    private SQLiteOpenHelper mSqliteOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int MOVIE = 0;
    private static final int MOVIE_WITH_ID = 200;

    private static UriMatcher buildUriMatcher(){
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = FavorContract.AUTHORITY;

        matcher.addURI(authority, FavorContract.MovieEntry.TABLE_FAVOR, MOVIE);
        matcher.addURI(authority, FavorContract.MovieEntry.TABLE_FAVOR + "/#", MOVIE_WITH_ID);

        return matcher;

    }


    @Override
    public boolean onCreate(){
        mSqliteOpenHelper = new FavoriteMovieHelper(getContext());
        return true;
    }
    @Override
    synchronized public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                                     String[] selectionArgs, String sortOrder){
        final SQLiteDatabase db = mSqliteOpenHelper.getReadableDatabase();
        Cursor mCursor;

        switch (sUriMatcher.match(uri)){
            case MOVIE:{
                mCursor = db.query(FavorContract.MovieEntry.TABLE_FAVOR,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            }

            default:
                throw new IllegalArgumentException("uri not recognized!");



        }
        mCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return mCursor;

    }
@Override
    public String getType(Uri uri) {
    final int match = sUriMatcher.match(uri);

    switch (match){
        case MOVIE:{
            return FavorContract.MovieEntry.CONTENT_DIR_TYPE;
        }
        case MOVIE_WITH_ID:{
            return FavorContract.MovieEntry.CONTENT_ITEM_TYPE;
        }
        default:{
            throw new IllegalArgumentException("unknown URI man!");
        }
    }
}

@Override
    public Uri insert(Uri uri, ContentValues values) {
    final SQLiteDatabase db = mSqliteOpenHelper.getWritableDatabase();
    long _id;
    Uri returnUri;


    switch (sUriMatcher.match(uri)) {
        case MOVIE:
            _id = db.insert(FavorContract.MovieEntry.TABLE_FAVOR, null, values);
            if (_id > 0) {
                returnUri = FavorContract.MovieEntry.buildMovieUri(_id);
                db.close();


            } else {
                throw new UnsupportedOperationException("Unable to insert rows into: " + uri);

            }
            break;
            default:
                throw new UnsupportedOperationException("unknow uri: " + uri);
        }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;

    }


@Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
    final SQLiteDatabase db = mSqliteOpenHelper.getWritableDatabase();
    int rows = 0;
    switch (sUriMatcher.match(uri)) {
        case MOVIE:
            db.delete(FavorContract.MovieEntry.TABLE_FAVOR, selection, selectionArgs);
            break;
        default:
            throw new UnsupportedOperationException("unknown uri" + uri);
    }


        getContext().getContentResolver().notifyChange(uri, null);


    return rows;
}


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        return -1;
    }
}
