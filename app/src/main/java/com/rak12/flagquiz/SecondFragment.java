package com.rak12.flagquiz;

import android.os.Bundle;
import android.preference.PreferenceFragment;

public class SecondFragment extends PreferenceFragment {
    // creates preferences GUI from preferences.xml file in res/xml
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addPreferencesFromResource(R.xml.prefrences); // load from XML
    }
}

