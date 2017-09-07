package com.job.a.wants.hugo.flickr_client;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.job.a.wants.hugo.flickr_client.flickr.Flickr;
import com.job.a.wants.hugo.flickr_client.model.Photo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRAS_PHOTO_URL = "EXTRAS_PHOTO_URL";
    public static final String EXTRAS_PHOTO_TITLE = "EXTRAS_PHOTO_TITLE";
    public static final String EXTRAS_PHOTO_DESCRIPTION = "EXTRAS_PHOTO_DESCRIPTION";

    private static final String TAG = "FlickrClient";

    private static final String FLICKR_APP_ID = "b121c9d238ee4226633b1eb1b67d17f7";
    private static final String FLICKR_APP_SECRET = "";
    private Flickr flickr;

    private static final int ITEMS_PER_PAGE = 10;
    private int currentPage = 1;

    private boolean readyToLoad = true;
    private String queryString = "";

    private RecyclerView recyclerView;
    private ListPhotoAdapter listPhotoAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.flickr = new Flickr(FLICKR_APP_ID, FLICKR_APP_SECRET);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        listPhotoAdapter = new ListPhotoAdapter(this);
        recyclerView.setAdapter(listPhotoAdapter);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy > 0){
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (readyToLoad){
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount){
                            readyToLoad = false;

                            currentPage++;
                            getData();
                        }
                    }
                }
            }
        });

        listPhotoAdapter.setItemClickedListener(new ListPhotoAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int itemIndex) {
                Photo photo = listPhotoAdapter.getItem(itemIndex);

                Intent intent = new Intent(getApplicationContext(), PhotoDetailActivity.class);
                intent.putExtra(EXTRAS_PHOTO_TITLE, photo.getId());
                intent.putExtra(EXTRAS_PHOTO_URL, photo.getUrl());
                intent.putExtra(EXTRAS_PHOTO_DESCRIPTION, photo.getTitle());
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem item = menu.findItem(R.id.menuSearch);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search for flickr photos");
        searchView.setIconified(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast toast = Toast.makeText(MainActivity.this, "Searching...", Toast.LENGTH_SHORT);
                toast.show();

                clearDisplayPictures();

                queryString = query;
                getData();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()){
                    clearDisplayPictures();
                }

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void clearDisplayPictures(){
        listPhotoAdapter.clearDataSet();
        queryString = "";
        currentPage = 1;
    }

    private void getData(){
        if (this.queryString.isEmpty()) return;

        flickr.searchPhotos(this.queryString, ITEMS_PER_PAGE, currentPage, new Flickr.FlickrSearchFinishedHandler() {
            public void onSearchFinished(boolean success, ArrayList<Photo> photos) {
                readyToLoad = true;

                if (success) {
                    if (photos.size() == 0) {
                        Toast toast = Toast.makeText(MainActivity.this, "No results were found", Toast.LENGTH_SHORT);
                        toast.show();
                        return;
                    }

                    Toast toast = Toast.makeText(MainActivity.this, "Loading", Toast.LENGTH_SHORT);
                    toast.show();

                    listPhotoAdapter.setDataset(photos);
                } else {
                    Toast toast = Toast.makeText(MainActivity.this, "Search failed!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
    }
}
