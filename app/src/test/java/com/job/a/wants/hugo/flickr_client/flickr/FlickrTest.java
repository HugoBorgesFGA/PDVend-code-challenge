package com.job.a.wants.hugo.flickr_client.flickr;

import com.job.a.wants.hugo.flickr_client.model.Photo;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by hugo on 06/09/17.
 */
public class FlickrTest {
    boolean testFinished = false;

    private void assertPhoto(Photo photo){
        assertNotNull(photo.getId());
        assertNotNull(photo.getThumbnailUrl());
        assertNotNull(photo.getTitle());
        assertNotNull(photo.getUrl());

        System.out.println("Photo: ");
        System.out.println("\t ID: " + photo.getId());
        System.out.println("\t Title: " + photo.getTitle());
        System.out.println("\t Thumbnail: " + photo.getThumbnailUrl());
        System.out.println("\t URL: " + photo.getUrl());
        System.out.println("\n");
    }

    @Test
    public void testGetSizePhotos(){
        String FLICKR_APP_ID = "b121c9d238ee4226633b1eb1b67d17f7";
        String FLICKR_APP_SECRET = "";

        Flickr flickr = new Flickr(FLICKR_APP_ID, FLICKR_APP_SECRET);
        this.testFinished = false;

        String photoID = "36917700512";
        flickr.getPhoto(photoID, new Flickr.FlickrPhotoTakenHandler() {
            @Override
            public void onPhotoTaken(boolean success, Photo photo) {
                if(success){
                    assertPhoto(photo);
                }
                FlickrTest.this.testFinished = true;
            }
        });

        while(!testFinished){
            try {
                Thread.sleep(1000);
                System.out.println("Waiting assynchronous event...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testSearchPhotosBadArguments() {
        String FLICKR_APP_ID = "b121c9d238ee4226633b1eb1b67d17f7";
        String FLICKR_APP_SECRET = "";
        this.testFinished = false;

        Flickr flickr = new Flickr(FLICKR_APP_ID, FLICKR_APP_SECRET);

        boolean result = flickr.searchPhotos("miami", 0, 1, new Flickr.FlickrSearchFinishedHandler(){
            @Override
            public void onSearchFinished(boolean success, ArrayList<Photo> photos) {

            }
        });
        assertFalse(result);

        result = flickr.searchPhotos("miami", 1, 0, new Flickr.FlickrSearchFinishedHandler(){
            @Override
            public void onSearchFinished(boolean success, ArrayList<Photo> photos) {

            }
        });
        assertFalse(result);

        result = flickr.searchPhotos("", 10, 1, new Flickr.FlickrSearchFinishedHandler(){
            @Override
            public void onSearchFinished(boolean success, ArrayList<Photo> photos) {

            }
        });
        assertFalse(result);
    }

    @Test
    public void testSearchPhotos() {
        String FLICKR_APP_ID = "b121c9d238ee4226633b1eb1b67d17f7";
        String FLICKR_APP_SECRET = "";
        this.testFinished = false;

        Flickr flickr = new Flickr(FLICKR_APP_ID, FLICKR_APP_SECRET);

        boolean result = flickr.searchPhotos("miami", 20, 1, new Flickr.FlickrSearchFinishedHandler(){
            @Override
            public void onSearchFinished(boolean success, ArrayList<Photo> photos) {
                if (success){
                    assertEquals(20, photos.size());

                    for(Photo photo : photos){
                        assertPhoto(photo);
                    }
                }
                FlickrTest.this.testFinished = true;
            }
        });

        assertTrue(result);

        while(!testFinished){
            try {
                Thread.sleep(1000);
                System.out.println("Waiting assynchronous event...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}