package com.example.sam.movieproject;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.movieproject.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sam on 11/19/17.
 */

public class CursorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<Movie> mData;
    public CursorAdapter.CustomCursorItemClickListener listener;
    String TMDB_POSTER_BASE_URL = "https://image.tmdb.org/t/p/w185";



    public interface CustomCursorItemClickListener {
        void onCursorItemClick(View v, int position);}


    public CursorAdapter(Context context, List<Movie> list) {
        this.mContext = context;
        this.listener = (CursorAdapter.CustomCursorItemClickListener) context;
        this.mData = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.post_pix, parent, false);
        final CursorAdapter.MyHolder myHolder = new CursorAdapter.MyHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (listener != null) {
                    listener.onCursorItemClick(v, myHolder.getAdapterPosition());

                }
            }
        });
        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CursorAdapter.MyHolder myHolder = (CursorAdapter.MyHolder) holder;
        Movie current = mData.get(position);


        Picasso.with(mContext)
                .load(TMDB_POSTER_BASE_URL + current.getPosterPath())
                .resize(185, 278)
                .error(R.drawable.failure)
                .placeholder(R.drawable.loading)
                .into(myHolder.moviePoster);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    class MyHolder extends RecyclerView.ViewHolder {
        TextView movieTitle;
        TextView overView;
        TextView voteAverage;
        TextView releaseDate;
        ImageView moviePoster;
        ImageView moviePoster1;


        public MyHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.original_title);
            overView = (TextView) itemView.findViewById(R.id.overview);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date);
            voteAverage = (TextView) itemView.findViewById(R.id.vote_average);
            moviePoster = (ImageView) itemView.findViewById(R.id.left_image1);
            moviePoster1 = (ImageView) itemView.findViewById(R.id.poster);


        }
    }


}

