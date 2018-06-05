package com.example.moham.newsapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<News>> {
    RecyclerView recyclerView;
    MyAdapter adapter;
    TextView massage;
    final String GUARDIAN_API_URL = "https://content.guardianapis.com/search";

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

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String category = sharedPreferences.getString(getString(R.string.settings_news_category_key), getString(R.string.settings_news_category_default));
        String stringDate = sharedPreferences.getString(getString(R.string.settings_news_date_key), getString(R.string.settings_news_date_default));
        Log.d("stringDate ", stringDate);
        SimpleDateFormat format = new SimpleDateFormat(
                "dd/MM/yyyy");
        Date myDate = null;
        try {
            myDate = format.parse(stringDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        format.applyPattern("yyyy-MM-dd");
        String finalDate = format.format(myDate);

        Log.d("finalDate", finalDate);

        Uri baseUri = Uri.parse(GUARDIAN_API_URL);

        Uri.Builder builder = baseUri.buildUpon();

        builder.appendQueryParameter("section", category);
        builder.appendQueryParameter("from-date", finalDate);
        builder.appendQueryParameter("api-key",getString(R.string.api_key));


        return new AsyncLoader(this, builder.toString());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}