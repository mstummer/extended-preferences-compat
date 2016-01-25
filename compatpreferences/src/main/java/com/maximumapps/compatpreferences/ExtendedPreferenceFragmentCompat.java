package com.maximumapps.compatpreferences;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;


public abstract class ExtendedPreferenceFragmentCompat extends PreferenceFragmentCompat implements
        PreferenceFragmentCompat.OnPreferenceStartScreenCallback {


    public static final String TAG = "EXTENDED_PREFERENCE_FRAGMENT_COMPAT";
    private int mFragmentContainerId;


    public ExtendedPreferenceFragmentCompat newInstance() {
        try {
            return this.getClass().newInstance();
        } catch (java.lang.InstantiationException ie) {
            throw new RuntimeException(ie);
        } catch (IllegalAccessException ie) {
            throw new RuntimeException(ie);
        }
    }


    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        if (preference instanceof ExtendedDialogPreferenceCompat) {
            ((ExtendedDialogPreferenceCompat) preference).showDialog(null);
        } else
            super.onDisplayPreferenceDialog(preference);
    }


    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat, PreferenceScreen preferenceScreen) {
        ExtendedPreferenceFragmentCompat fragment = newInstance();

        Bundle args = new Bundle();
        args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, preferenceScreen.getKey());
        fragment.setArguments(args);

        showFragment(fragment, TAG, true);
        return true;
    }


    @Override
    public Fragment getCallbackFragment() {
        return this;
    }


    public void setFragmentContainerId(int fragmentContainerId) {
        mFragmentContainerId = fragmentContainerId;
    }


    public void showFragment(Fragment fragment, String tag, boolean addToBackStack) {
        if (mFragmentContainerId == 0)
            throw new Error("You must call setFragmentContainerId(int) in onCreatePreferences()!");

        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(mFragmentContainerId, fragment, tag);
        if (addToBackStack) transaction.addToBackStack(null);
        transaction.commit();
    }
}
