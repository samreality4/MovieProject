package com.example.sam.movieproject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.sam.movieproject.model.OtherData;
import com.example.sam.movieproject.model.OtherDataResult;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by sam on 10/12/17.
 */

public class OtherDataAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private LayoutInflater inflater;
    private List<OtherData> mData;
    public trailerClickListener listener;

    public interface trailerClickListener {
        void onTrailerItemClick(View v, int position);}



    public OtherDataAdapter(Context context, List<OtherData> list) {
        this.mContext = context;
        this.listener = (trailerClickListener) context;
        this.mData = list;
        inflater = LayoutInflater.from(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trailer_thumbnail, parent, false);
        final OtherDataAdapter.MyHolder myHolder = new MyHolder(view);
        view.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (listener != null) {
                    listener.onTrailerItemClick(v, myHolder.getAdapterPosition());

                }
            }
        });

        return myHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OtherDataAdapter.MyHolder myHolder = (OtherDataAdapter.MyHolder) holder;
        OtherData current = mData.get(position);
        String youTubeThumbnail = "http://img.youtube.com/vi/" + current.getKey() + "/0.jpg";


        Picasso.with(mContext)
                .load(youTubeThumbnail)
                .resize(44, 40)
                .error(R.drawable.failure)
                .placeholder(R.drawable.loading)
                .into(myHolder.youTubeThumb);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    class MyHolder extends RecyclerView.ViewHolder {
        ImageView youTubeThumb;


        public MyHolder(View itemView) {
            super(itemView);
            youTubeThumb = (ImageView) itemView.findViewById(R.id.youtube_thumb);


        }
    }

}
