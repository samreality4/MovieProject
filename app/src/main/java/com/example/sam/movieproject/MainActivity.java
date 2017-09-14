package com.example.sam.movieproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcel;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.sam.movieproject.model.Movie;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.http.Url;

import static android.R.attr.data;
import static android.R.attr.debuggable;
import static android.R.attr.id;
import static android.R.attr.listChoiceBackgroundIndicator;
import static android.provider.Settings.Global.getString;
import static org.apache.http.params.CoreConnectionPNames.CONNECTION_TIMEOUT;

public class MainActivity extends AppCompatActivity implements MovieAdapter.CustomItemClickListener {
        public static final int CONNECTION_TIMEOUT = 10000;
        public static final int READ_TIMEOUT = 15000;
        private RecyclerView moviePostersList;
        private MovieAdapter movieAdapter;
        Context context;
        List<Movie> list = new ArrayList<>();
        String url1 = "https://api.themoviedb.org/3/discover/movie?api_key=enter_api_key&sort_by=vote_average.desc&most_popular.desc";
        String url2 = "http://api.themoviedb.org/3/movie/popular?api_key=enter_api_key";




    @Override
    public void onItemClick(View v, int position) {
        Intent intent = new Intent(this, MovieDetailActivity.class);
        Movie movie = list.get(position);
        intent.putExtra("Movie", movie);
        startActivity(intent);
        Log.d("clicked position:", String.valueOf(position));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new AsyncFetch().execute(url2);
        context = getApplicationContext();

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
                new AsyncFetch().execute(url1);
                Toast toast = Toast.makeText(context,"Top Rated", Toast.LENGTH_SHORT);
                toast.show();
                return true;
            case R.id.most_popular:
                new AsyncFetch().execute(url2);
                Toast toast1 = Toast.makeText(context,"Most Popular", Toast.LENGTH_SHORT);
                toast1.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class AsyncFetch extends AsyncTask<String, Void, String> {
        ProgressDialog pdLoading = new ProgressDialog(MainActivity.this);
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


}






