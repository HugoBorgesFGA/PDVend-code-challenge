package com.job.a.wants.hugo.flickr_client.flickr;

import com.job.a.wants.hugo.flickr_client.flickr.responses.photos.*;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by hugo on 05/09/17.
 */

public interface FlickrClient {
    // https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=API_KEY&format=json&nojsoncallback=1&text=san+andres
    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1")
    Call<FlickrSearch> searchPhotoByText(
            @Query("api_key") String apiKey,
            @Query("text") String freeText,
            @Query("per_page") Integer perPage,
            @Query("page") Integer page
    );

    // https://api.flickr.com/services/rest/?method=flickr.photos.getSizes&format=json&nojsoncallback=1&api_key=API_KEY&photo_id=36656651346
    @GET("?method=flickr.photos.getSizes&format=json&nojsoncallback=1")
    Call<FlickrGetSize> getPhoto(
            @Query("api_key") String apiKey,
            @Query("photo_id") String photo
    );
}
