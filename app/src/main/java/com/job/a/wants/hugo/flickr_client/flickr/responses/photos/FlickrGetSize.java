package com.job.a.wants.hugo.flickr_client.flickr.responses.photos;

import java.util.ArrayList;

/**
 * Created by hugo on 05/09/17.
 */

public class FlickrGetSize {
    public class Size{
        private String label;
        private int width;
        private int height;
        private String source;

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }
    }

    public class Sizes{
        private String canblog;
        private String canprint;
        private String candownload;
        private ArrayList<Size> size;

        public String getCanblog() {
            return canblog;
        }

        public void setCanblog(String canblog) {
            this.canblog = canblog;
        }

        public String getCanprint() {
            return canprint;
        }

        public void setCanprint(String canprint) {
            this.canprint = canprint;
        }

        public String getCandownload() {
            return candownload;
        }

        public void setCandownload(String candownload) {
            this.candownload = candownload;
        }

        public ArrayList<Size> getSize() {
            return size;
        }

        public void setSize(ArrayList<Size> size) {
            this.size = size;
        }
    }

    private Sizes sizes;
    private String stat;

    public Sizes getSizes() {
        return sizes;
    }

    public void setSizes(Sizes sizes) {
        this.sizes = sizes;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
