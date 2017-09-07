package com.job.a.wants.hugo.flickr_client.flickr;

import android.util.Log;

import com.job.a.wants.hugo.flickr_client.flickr.responses.photos.FlickrGetSize;
import com.job.a.wants.hugo.flickr_client.flickr.responses.photos.FlickrSearch;
import com.job.a.wants.hugo.flickr_client.model.Photo;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hugo on 05/09/17.
 */

public class Flickr {
    private static final String TAG = "Flickr";
    private static final String FLICKR_API_URL = "https://api.flickr.com/services/rest/";
    private Retrofit retrofit;
    private FlickrClient flickrClient;

    private String apiKey;
    private String apiSecret; // RFU

    public Flickr(String apiKey, String apiSecret) {
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;

        this.retrofit = new Retrofit.Builder()
                .baseUrl(FLICKR_API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        this.flickrClient = retrofit.create(FlickrClient.class);
    }

    public interface FlickrPhotoTakenHandler{
        void onPhotoTaken(boolean success, Photo photo);
    }

    public boolean getPhoto(String photoID, final FlickrPhotoTakenHandler handler) {
        if (photoID.isEmpty()) return false;

        Call<FlickrGetSize> flickrGetSizeCall = flickrClient.getPhoto(this.apiKey, photoID);

        flickrGetSizeCall.enqueue(new Callback<FlickrGetSize>() {
            @Override
            public void onResponse(Call<FlickrGetSize> call, Response<FlickrGetSize> response) {
                if (response.isSuccessful()) {
                    FlickrGetSize flickrGetSize = response.body();
                    Photo photo = new Photo();

                    ArrayList<FlickrGetSize.Size> availableSizes;
                    try{
                        availableSizes = flickrGetSize.getSizes().getSize();
                    }catch(Exception e){
                        handler.onPhotoTaken(false, null);
                        return;
                    }

                    photo.setUrl(availableSizes.get(availableSizes.size() - 1).getSource());
                    for(FlickrGetSize.Size item : availableSizes) {
                        if (item.getLabel().equals("Thumbnail") ||
                            item.getLabel().equals("Small"))
                        {
                            photo.setThumbnailUrl(item.getSource());
                        }

                        if (item.getWidth() > 500){
                            photo.setUrl(item.getSource());
                            break;
                        }
                    }

                    handler.onPhotoTaken(true, photo);
                }else {
                    handler.onPhotoTaken(false, null);
                }
            }

            @Override
            public void onFailure(Call<FlickrGetSize> call, Throwable t) {
                handler.onPhotoTaken(false, null);
            }
        });

        return false;
    }


    public interface FlickrSearchFinishedHandler{
        void onSearchFinished(boolean success, ArrayList<Photo> photos);
    }

    public boolean searchPhotos(String text, final int perPage, int page, final FlickrSearchFinishedHandler handler) {
        if (page < 1) return false;
        if (perPage < 1) return false;
        if (text.isEmpty()) return false;

        Call<FlickrSearch> flickrSearchCall = flickrClient.searchPhotoByText(this.apiKey, text, perPage, page);

        flickrSearchCall.enqueue(new Callback<FlickrSearch>() {
            @Override
            public void onResponse(Call<FlickrSearch> call, Response<FlickrSearch> response) {
                if (response.isSuccessful()) {
                    FlickrSearch flickrSearch = response.body();
                    ArrayList<FlickrSearch.Photo> responsePhotos = flickrSearch.getPhotos().getPhoto();
                    final ArrayList<Photo> photos = new ArrayList<Photo>();
                    for(final FlickrSearch.Photo photo : responsePhotos)
                    {
                        // Got the photos IDs and their titles, must do another request
                        // for getting the thumbnail and picture Url
                        final Photo temp = new Photo();
                        temp.setId(photo.getId());
                        temp.setTitle(photo.getTitle());

                        getPhoto(temp.getId(), new FlickrPhotoTakenHandler() {
                            @Override
                            public void onPhotoTaken(boolean success, Photo photo) {
                                if (success){
                                    temp.setUrl(photo.getUrl());
                                    temp.setThumbnailUrl(photo.getThumbnailUrl());
                                    photos.add(temp);

                                    if (photos.size() == perPage) {
                                        handler.onSearchFinished(true, photos);
                                    }
                                }else{
                                    handler.onSearchFinished(false, photos);
                                }
                            }
                        });
                    }
                } else {
                    handler.onSearchFinished(false, null);
                }
            }

            @Override
            public void onFailure(Call<FlickrSearch> call, Throwable t) {
                handler.onSearchFinished(false, null);
            }
        });

        return true;
    }


}
