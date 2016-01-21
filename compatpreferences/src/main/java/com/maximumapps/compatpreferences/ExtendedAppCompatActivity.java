package com.maximumapps.compatpreferences;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;


public class ExtendedAppCompatActivity extends AppCompatActivity
        implements ExtendedPreferenceFragmentCompat.ShowFragment {

    private int mFragmentContainerId = 0;


    @Override
    public void showFragment(Fragment fragment, String tag, boolean addToBackStack) {
        if (mFragmentContainerId == 0)
            throw new Error("Activity must call setFragmentContainerId()!");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(mFragmentContainerId, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }


    public void setFragmentContainerId(int mFragmentContainerId) {
        this.mFragmentContainerId = mFragmentContainerId;
    }
}