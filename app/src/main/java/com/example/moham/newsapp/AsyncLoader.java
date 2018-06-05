package com.example.moham.newsapp;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class AsyncLoader extends AsyncTaskLoader<List<News>> {
    String URL;
    public AsyncLoader(Context context, String URL) {
        super(context);
        this.URL = URL;
    }

    @Override
    public List<News> loadInBackground() {
        Log.d("loadInBackground", " loadInBackground");
        List<News> fetchedData;


        fetchedData = ConnectionFunctions.fetchNews(URL);

        return fetchedData;
    }

}
