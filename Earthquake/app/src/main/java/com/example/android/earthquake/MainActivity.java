package com.example.android.earthquake;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EQuack>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=4";
    private static final int EARTHQUAKE_LOADER_ID = 1;
    private final String LOG_TAG = MainActivity.class.getName();
    ArrayAdapter<EQuack> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a fake list of earthquake locations.


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        List<EQuack> earthquakes = new ArrayList<EQuack>();

        // Create a new {@link ArrayAdapter} of earthquakes
        adapter = new WordAdaptar(MainActivity.this, earthquakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                openWebPage(earthquakes.get(i).getUrl());
            }
        });
        earthquakeListView.setEmptyView(findViewById(R.id.empty_textview));
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            Log.i(LOG_TAG, " This is in the initLoader");
            loaderManager.initLoader(EARTHQUAKE_LOADER_ID, null, this);

        }else{
            ProgressBar progressBar =(ProgressBar)findViewById(R.id.progressor_bar);
            progressBar.setVisibility(View.GONE);
            TextView textView =(TextView)findViewById(R.id.empty_textview);
            textView.setText("No internet Connetion! Check Your Connection");
        }


    }
    public void openWebPage(String url) {
        try {
            Uri webpage = Uri.parse(url);
            Intent myIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No application can handle this request. Please install a web browser or check your URL.",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public Loader<List<EQuack>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.i(LOG_TAG, " This is in the onCreateLoader");
        return new EarthQuackLoader(this,USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<EQuack>> loader, List<EQuack> eQuacks) {
        ProgressBar progressBar =(ProgressBar)findViewById(R.id.progressor_bar);
        progressBar.setVisibility(View.GONE);
        Log.i(LOG_TAG, " This is in the onLoadFinished");
        TextView empty_text = (TextView)findViewById(R.id.empty_textview);
        empty_text.setText("No EarthQuacks Found");
        adapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (eQuacks != null && !eQuacks.isEmpty()) {
            adapter.addAll(eQuacks);
        }
    }
    @Override
    public void onLoaderReset(Loader<List<EQuack>> loader) {
        Log.i(LOG_TAG, " This is in the onLoaderReset");
        adapter.clear();

    }

}