package com.job.a.wants.hugo.flickr_client;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.job.a.wants.hugo.flickr_client.model.Photo;

import java.util.ArrayList;

/**
 * Created by hugo on 06/09/17.
 */

public class ListPhotoAdapter extends RecyclerView.Adapter<ListPhotoAdapter.ViewHolder> {
    private ArrayList<Photo> dataset;
    private Context context;
    private OnItemClickedListener itemClickedListener;

    public interface OnItemClickedListener {
        public void onItemClicked(int itemIndex);
    }

    public void setItemClickedListener(OnItemClickedListener itemClickedListener) {
        this.itemClickedListener = itemClickedListener;
    }

    public Photo getItem(int position){
        return this.dataset.get(position);
    }

    public ListPhotoAdapter(Context context){
        this.dataset = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_thumbnail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Photo photo = dataset.get(position);
        Glide.with(context)
                .load(photo.getThumbnailUrl())
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.thumbnailImageView);

        holder.thumbnailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               ListPhotoAdapter.this.itemClickedListener.onItemClicked(position);
            }
        });
    }

    @Override
    public int getItemCount(){
        return dataset.size();
    }

    public void setDataset(ArrayList<Photo> photos) {
        this.dataset.addAll(photos);
        notifyDataSetChanged();
    }

    public void clearDataSet(){
        this.dataset.clear();
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnailImageView;

        public ViewHolder(View itemView){
            super(itemView);

            thumbnailImageView = (ImageView) itemView.findViewById(R.id.thumbnailImageView);
        }
    }
}
