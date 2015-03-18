package com.example.android.newsvocabdictionary;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.SearchRecentSuggestionsProvider;
import android.database.Cursor;
import android.net.Uri;
import android.content.SearchRecentSuggestionsProvider;
import android.provider.SearchRecentSuggestions;


/**
 * Created by Android on 18-03-2015.
 */
public class SampleRecentSuggestionsProvider
        extends SearchRecentSuggestionsProvider {

    public static final String AUTHORITY =
            SampleRecentSuggestionsProvider.class.getName();

    public static final int MODE = DATABASE_MODE_QUERIES;

    public SampleRecentSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }



}