package com.example.sam.movieproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.model.Reviews;

import java.util.List;

/**
 * Created by sam on 10/13/17.
 */

class ReviewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private LayoutInflater inflater;
    private List<Reviews> mData;

    public ReviewsAdapter(Context context, List<Reviews> list){
        this.mData = list;
        inflater = LayoutInflater.from(context);

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_reviews, parent, false);
        final ReviewsAdapter.MyHolder myHolder = new MyHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ReviewsAdapter.MyHolder myHolder = (ReviewsAdapter.MyHolder) holder;
        Reviews current = mData.get(position);
        myHolder.movieReviewsAuthor.setText(current.getAuthor());
        myHolder.movieReviewsContent.setText(current.getContent());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class MyHolder extends RecyclerView.ViewHolder {
        TextView movieReviewsAuthor;
        TextView movieReviewsContent;


        public MyHolder(View itemView) {
            super(itemView);
            movieReviewsAuthor = (TextView) itemView.findViewById(R.id.movie_reviews_author);
            movieReviewsContent = (TextView) itemView.findViewById(R.id.movie_reviews_content);


        }
    }
}
