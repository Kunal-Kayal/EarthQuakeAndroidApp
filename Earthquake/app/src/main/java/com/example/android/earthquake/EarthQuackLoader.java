package com.example.android.earthquake;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;


import java.util.List;

public class EarthQuackLoader extends AsyncTaskLoader<List<EQuack>> {

    private String mUrl;
    private final String LOG_TAG = EarthQuackLoader.class.getName();
    public EarthQuackLoader(Context context, String url) {
        super(context);
        mUrl =url;
        Log.i(LOG_TAG,"This is Constructor in EarthQuickLoader Class");
    }
    @Override
    public List<EQuack> loadInBackground() {
        Log.i(LOG_TAG, " This is in the background load in loader");
        if(mUrl==null)return null;
        return QueryUtils.fetchEarthquakesData(mUrl);

    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, " This is in the start load in loader");
        forceLoad();
    }
}
