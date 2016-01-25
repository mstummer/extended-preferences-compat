package com.maximumapps.extendedpreferencescompat;

import android.os.Bundle;

import com.maximumapps.compatpreferences.ExtendedPreferenceFragmentCompat;


public class MyPreferenceFragment extends ExtendedPreferenceFragmentCompat {


    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        // define the container to swap the fragments into
        setFragmentContainerId(R.id.fragment_container);
        // set pref resource as usual
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}