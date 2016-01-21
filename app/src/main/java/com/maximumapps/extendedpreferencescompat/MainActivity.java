package com.maximumapps.extendedpreferencescompat;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.maximumapps.compatpreferences.ExtendedAppCompatActivity;


public class MainActivity extends ExtendedAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // let the Activity know what container to swap the fragments into
        setFragmentContainerId(R.id.fragment_container);

        if (savedInstanceState == null) {
            // Create the fragment only when the activity is created for the first time.
            // ie. not after orientation changes
            Fragment fragment = getSupportFragmentManager().findFragmentByTag(MyPreferenceFragment.TAG);
            if (fragment == null) {
                fragment = new MyPreferenceFragment();
                showFragment(fragment, MyPreferenceFragment.TAG, false);
            }
        }
    }
}
