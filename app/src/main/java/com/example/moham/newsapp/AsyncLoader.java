package com.example.moham.newsapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;
import java.util.List;

public class AsyncLoader extends AsyncTaskLoader<List<News>> {
    public AsyncLoader(Context context) {
        super(context);
    }

    @Override
    public List<News> loadInBackground() {
        Log.d("loadInBackground", " loadInBackground");
        List<News> fetchedData = null;

        fetchedData = ConnectionFunctions.fetchNews("https://content.guardianapis.com/search?api-key=c1db84b5-3056-4332-8372-09cfac9f5b6b");

        return fetchedData;
    }

}
