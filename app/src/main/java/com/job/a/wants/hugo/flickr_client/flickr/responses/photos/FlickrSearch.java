package com.job.a.wants.hugo.flickr_client.flickr.responses.photos;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by hugo on 05/09/17.
 */

public class FlickrSearch {
    public class Photo{
        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public class Photos{
        private String page;
        private String pages;
        private String perpage;
        private String total;
        private ArrayList<Photo> photo;

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public String getPerpage() {
            return perpage;
        }

        public void setPerpage(String perpage) {
            this.perpage = perpage;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public ArrayList<Photo> getPhoto() {
            return photo;
        }

        public void setPhoto(ArrayList<Photo> photo) {
            this.photo = photo;
        }
    }

    private Photos photos;
    private String stat;

    public Photos getPhotos() {
        return photos;
    }

    public void setPhotos(Photos photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
