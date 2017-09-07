package com.job.a.wants.hugo.flickr_client;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class PhotoDetailActivity extends AppCompatActivity{
    private ImageView photoImageView;
    private TextView titleTextView;
    private TextView descriptionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);

        this.photoImageView = (ImageView) findViewById(R.id.photoImageView);
        this.titleTextView = (TextView) findViewById(R.id.titleTextView);
        this.descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        
        String photoUrl = getIntent().getStringExtra(MainActivity.EXTRAS_PHOTO_URL);
        String photoTitle = getIntent().getStringExtra(MainActivity.EXTRAS_PHOTO_TITLE);
        String photoDescription = getIntent().getStringExtra(MainActivity.EXTRAS_PHOTO_DESCRIPTION);

        titleTextView.setText(photoTitle);
        descriptionTextView.setText(photoDescription);
        Glide.with(this)
                .load(photoUrl)
                .placeholder(R.drawable.placeholder)
                .centerCrop()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(photoImageView);
    }
}
