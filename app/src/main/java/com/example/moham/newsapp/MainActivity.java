package com.example.moham.newsapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    RecyclerView recyclerView;
    MyAdapter adapter;
    TextView massage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, new ArrayList<News>());
        recyclerView.setAdapter(adapter);
        massage= findViewById(R.id.textView);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        if(isConnected) {
            getSupportLoaderManager().initLoader(0, null, this).forceLoad();
            massage.setVisibility(View.INVISIBLE);
            massage.setText(this.getString(R.string.no_news_massage));
        } else {
            massage.setText(this.getString(R.string.no_internet_massage));
            massage.setVisibility(View.VISIBLE);
        }
        }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int id, @Nullable Bundle args) {
        Log.d("onCreateLoader", " onCreateLoader");
        return new AsyncLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> data) {
        Log.d("onLoaderFinish", " onLoaderFinish");
        if(data != null && data.size() != 0) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter = new MyAdapter(this, data);
            recyclerView.setAdapter(adapter);
        } else {
            recyclerView.setVisibility(View.INVISIBLE);
            massage.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        Log.d("onLoaderReset", " onLoaderReset");
    }

}