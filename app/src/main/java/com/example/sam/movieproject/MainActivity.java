package com.example.sam.movieproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.sam.movieproject.database.FavorContract;
import com.example.sam.movieproject.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity implements MovieAdapter.CustomItemClickListener, CursorAdapter.CustomCursorItemClickListener,LoaderManager.LoaderCallbacks<Cursor>{
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;
        private RecyclerView moviePostersList;
        private MovieAdapter movieAdapter;
        private CursorAdapter cursorAdapter;
        Context context;
        List<Movie> list = new ArrayList<>();
        List<Movie> cursorlist = new ArrayList<>();
        public final String API_KEY = BuildConfig.API_KEY;
        String url1 = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY+"&sort_by=vote_average.desc&most_popular.desc&append_to_response=video";
        String url2 = "http://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&append_to_response=video";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(isNetworkAvailable()) {
            new AsyncFetch().execute(url2);
        }else{
            final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                    "No internet connection.",
                    Snackbar.LENGTH_SHORT);
        snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                R.color.colorPrimary));
        snackbar.setAction(R.string.try_again, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    context = getApplicationContext();

        }


    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Movie movie = list.get(position);
        intent.putExtra("Movie", movie);
        startActivity(intent);
        Log.d("clicked position:", String.valueOf(position));

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
        public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case R.id.top_rated:
                if(isNetworkAvailable()){
                new AsyncFetch().execute(url1);
                Toast toast = Toast.makeText(context,"Top Rated", Toast.LENGTH_SHORT);
                toast.show();
                return true;}else{
                    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "No internet connection.",
                            Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorPrimary));
                    snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                }

            case R.id.most_popular:
                if(isNetworkAvailable()){

                new AsyncFetch().execute(url2);
                Toast toast1 = Toast.makeText(context,"Most Popular", Toast.LENGTH_SHORT);
                toast1.show();
                return true;}else{
                    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "No internet connection.",
                            Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorPrimary));
                    snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                }

            case R.id.favorited_movie:
                if(isNetworkAvailable()){
                    getSupportLoaderManager().initLoader(0, null, this);


                    Toast toast1 = Toast.makeText(context,"Favorite Movies", Toast.LENGTH_SHORT);
                    toast1.show();
                    return true;}else{
                    final Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                            "No internet connection.",
                            Snackbar.LENGTH_SHORT);
                    snackbar.setActionTextColor(ContextCompat.getColor(getApplicationContext(),
                            R.color.colorPrimary));
                    snackbar.setAction(R.string.try_again, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                        }
                    }).show();
                }
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
       return new CursorLoader(this,
               FavorContract.MovieEntry.CONTENT_URI,
               null,
               null,
               null,
               null);
        }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        cursorlist.clear();
        moviesFromCursor(data);
        cursorAdapter = new CursorAdapter(MainActivity.this, cursorlist);
        moviePostersList = (RecyclerView) findViewById(R.id.poster_pix);
        moviePostersList.setLayoutManager(new GridLayoutManager(context, 2));
        moviePostersList.setAdapter(cursorAdapter);
        }



    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    public void onCursorItemClick(View v, int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Movie movie = cursorlist.get(position);
        intent.putExtra("Movie", movie);
        startActivity(intent);
    }


    public class AsyncFetch extends AsyncTask<String, Void, String> {
        public ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
        HttpURLConnection conn;
        URL url = null;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                url = new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return e.toString();
            }
            try {
                conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("GET");

                conn.setDoOutput(true);
            } catch (IOException e1) {
                e1.printStackTrace();
                return e1.toString();
            }
            try {
                int response_code = conn.getResponseCode();
                Log.d("Response error", Integer.toString(response_code));
                Log.d("shyt", conn.getResponseMessage());
                if (response_code == HttpURLConnection.HTTP_OK) {

                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }
                    return (result.toString());
                } else {

                    return ("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String results) {
            final String TAG_RESULTS = "results";
            final String TAG_ORIGINAL_TITLE = "original_title";
            final String TAG_POSTER_PATH = "poster_path";
            final String TAG_OVERVIEW = "overview";
            final String TAG_VOTE_AVERAGE = "vote_average";
            final String TAG_RELEASE_DATE = "release_date";
            final String TAG_ID = "id";


            pdLoading.dismiss();


            pdLoading.dismiss();

            try {
                list.clear();
                JSONObject jobj = new JSONObject(results);
                JSONArray jArray = jobj.getJSONArray(TAG_RESULTS);

                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject json_data = jArray.getJSONObject(i);
                    Movie moviedata = new Movie();
                    moviedata.mPoster = json_data.getString(TAG_POSTER_PATH);
                    moviedata.mOverView = json_data.getString(TAG_OVERVIEW);
                    moviedata.mTitle = json_data.getString(TAG_ORIGINAL_TITLE);
                    moviedata.mReleaseDate = json_data.getString(TAG_RELEASE_DATE);
                    moviedata.mVoteAverage = json_data.getDouble(TAG_VOTE_AVERAGE);
                    moviedata.mID = json_data.getString(TAG_ID);

                    list.add(moviedata);



                }


                moviePostersList = (RecyclerView) findViewById(R.id.poster_pix);
                movieAdapter = new MovieAdapter(MainActivity.this,list);
                moviePostersList.setLayoutManager(new GridLayoutManager(context, 2));
                moviePostersList.setAdapter(movieAdapter);



            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private List<Movie> moviesFromCursor(Cursor cursor){

        if (cursor != null) {
            if (cursor.moveToFirst()){
                do{
                    Movie movie = new Movie();
                    movie.mID = cursor.getString(cursor.getColumnIndex(FavorContract.MovieEntry.KEY_FAVOR_ID));
                    movie.mPoster= cursor.getString(cursor.getColumnIndex(FavorContract.MovieEntry.KEY_FAVOR_POSTER));
                    movie.mOverView = cursor.getString(cursor.getColumnIndex(FavorContract.MovieEntry.KEY_FAVOR_OVERVIEW));
                    movie.mReleaseDate=cursor.getString(cursor.getColumnIndex(FavorContract.MovieEntry.KEY_RELEASE_DATE));
                    movie.mTitle=cursor.getString(cursor.getColumnIndex(FavorContract.MovieEntry.KEY_FAVOR_TITLE));
                    movie.mVoteAverage = cursor.getDouble(cursor.getColumnIndex(FavorContract.MovieEntry.KEY_VOTING_AVERAGE));

                    cursorlist.add(movie);
                }while(cursor.moveToNext());
            }
        }
        return cursorlist;
    }
    @Override
    public void onStop(){
        super.onStop();
        getSupportLoaderManager().destroyLoader(0);
    }


}





