package com.example.sam.movieproject;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by sam on 11/10/17.
 */

public class CustomCursorRecyclerViewAdapter extends CursorRecylerViewAdapter{
    Context mContext;
    public CustomCursorRecyclerViewAdapter(Context context, Cursor cursor){
        super(context, cursor);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
@Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
    View v = LayoutInflater.from(mContext).inflate(R.layout.post_pix, parent, false);
    return new CustomViewHolder(v);
}

@Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor){

    CustomViewHolder holder = (CustomViewHolder) viewHolder;
    cursor.moveToPosition(cursor.getPosition());
    holder.setData(cursor);
}
@Override
    public int getItemCount(){
    return super.getItemCount();
}

@Override
    public int getItemViewType(int position){
    return 0;
}


}
