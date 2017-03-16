package com.dreammist.profileviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by kevinthomas on 3/15/17.
 */

public class PhotoRecyclerViewAdapter  extends RecyclerView.Adapter<PhotoRecyclerViewAdapter.PhotoViewHolder> {

    private List<String> itemList;
    private Context context;

    public PhotoRecyclerViewAdapter(Context context, List<String> itemList) {
        this.itemList = itemList;
        this.context = context;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_grid_item, null);
        PhotoViewHolder viewHolder = new PhotoViewHolder(layoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        //holder.photoView.setImageResource(itemList.get(position).getPhoto());
        Picasso.with(context).load(itemList.get(position)).into(holder.photoView);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_item_imageview) ImageView photoView;
        public PhotoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}

