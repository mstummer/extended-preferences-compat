package com.maximumapps.extendedpreferencescompat;

import android.os.Bundle;

import com.maximumapps.compatpreferences.ExtendedPreferenceFragmentCompat;


public class MyPreferenceFragment extends ExtendedPreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
