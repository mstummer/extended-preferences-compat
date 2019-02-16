package com.maximumapps.extendedpreferencescompat;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.preference.PreferenceViewHolder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.maximumapps.compatpreferences.ExtendedDialogPreferenceCompat;


public class IntegerPreferenceCompat extends ExtendedDialogPreferenceCompat {
    private TextView mTextViewPreference;
    private NumberPicker mNumberPickerDialog;
    private int mDefaultValue;


    public IntegerPreferenceCompat(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // required for use in XMLs
    }


    public IntegerPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setWidgetLayoutResource(R.layout.pref_integer_widget);
        setDialogLayoutResource(R.layout.pref_integer_dialog);
        setPositiveButtonText("OK");
        setNegativeButtonText("Cancel");
        setNeedInputMethod(true);
    }


    public int getValue() {
        return getPersistedInt(mDefaultValue);
    }


    public void setValue(int val) {
        persistInt(val);
        showValue(val);
    }


    public void showValue(int val) {
        if (mTextViewPreference != null) {
            mTextViewPreference.setText(Integer.toString(val));
        }
    }


    /**
     * How to deal with android:defaultValue
     */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index) {
        mDefaultValue = a.getInt(index, mDefaultValue);
        return mDefaultValue;
    }


    /**
     * What to do when the preference is being displayed
     */
    @Override
    protected void onInitPreferenceView(PreferenceViewHolder viewHolder) {
        mTextViewPreference = ((TextView) viewHolder.findViewById(R.id.textView));
        showValue(getValue());
    }


    /**
     * What to do when the preference_dialog is being displayed
     */
    @Override
    protected void onDialogOpened(View rootView) {
        mNumberPickerDialog = ((NumberPicker) rootView.findViewById(R.id.numberPicker));
        mNumberPickerDialog.setMinValue(0);
        mNumberPickerDialog.setMaxValue(100);
        mNumberPickerDialog.setValue(getValue());
        mNumberPickerDialog.requestFocus();
    }


    /**
     * What to do when the preference_dialog is being dismissed
     */
    @Override
    protected void onDialogClosed(boolean positiveResult) {
        if (positiveResult) {
            setValue(mNumberPickerDialog.getValue());
        }
    }
}
