package com.example.moham.newsapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
    }
    public static class NewsFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            Preference newsDate = findPreference(getString(R.string.settings_news_date_key));
            bindPreferenceSummaryToValue(newsDate);

            Preference category = findPreference(getString(R.string.settings_news_category_key));
            bindPreferenceSummaryToValue(category);
        }

        private void bindPreferenceSummaryToValue(Preference preference) {
        preference.setOnPreferenceChangeListener(this);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
        String preferenceString = preferences.getString(preference.getKey(), "");
        onPreferenceChange(preference, preferenceString);
        }

        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            String value = newValue.toString();
            if(preference instanceof ListPreference) {
               ListPreference listPreference = (ListPreference) preference;
               int prefIndex = listPreference.findIndexOfValue(value);
               if(prefIndex >= 0) {
                   CharSequence[] labels = listPreference.getEntries();
                   preference.setSummary(labels[prefIndex]);
               }
            } else {
                preference.setSummary(value);
            }
            return true;
        }
    }
}
