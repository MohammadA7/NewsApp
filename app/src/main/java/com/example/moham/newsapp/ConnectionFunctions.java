package com.example.moham.newsapp;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class ConnectionFunctions {

    private static URL createUrl(String urlString) {

        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }
    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";

        if(url == null)
            return jsonResponse;

        HttpURLConnection urlConnection  = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if(urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("Error", "Error response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();

            if(inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return jsonResponse;
    }
    private static String readFromStream(InputStream inputStream) {
        StringBuilder output = new StringBuilder();
        if(inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = null;
            try {
                line = reader.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (line != null) {
                output.append(line);
                try {
                    line = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return output.toString();
    }
    private static List<News> extractFeatureFromJson(String newsJson) {
        if(TextUtils.isEmpty(newsJson))
            return null;

        List<News> news = new ArrayList<>();

        JSONObject jsonObject = null;
        Log.d("String Json", newsJson);
        try {
            jsonObject = new JSONObject(newsJson);
            jsonObject = jsonObject.getJSONObject("response");
            JSONArray newsArray = jsonObject.getJSONArray("results");

            for(int i=0; i < newsArray.length();i++) {
                JSONObject currentObject = newsArray.getJSONObject(i);
                String sectionName = currentObject.getString("sectionName");
                String publicationDate = currentObject.optString("webPublicationDate");
                publicationDate = publicationDate.substring(0, 10);
                String title = currentObject.getString("webTitle");
                String webUrl = currentObject.getString("webUrl");
                String author = "";
                int stringSpliter = title.indexOf("|");

                if(stringSpliter != -1) {
                    author = "By " + title.substring(stringSpliter+1, title.length()).trim();
                    title = title.substring(0, stringSpliter).trim();
                }
                News current = new News(title,webUrl, sectionName, author, publicationDate);

                news.add(current);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("First news", news.get(0).getTitle());
        return news;
    }
    public static List<News> fetchNews(String requestUrl) {

        URL url = createUrl(requestUrl);

        String jsonResponse = makeHttpRequest(url);

        return extractFeatureFromJson(jsonResponse);
    }
}
