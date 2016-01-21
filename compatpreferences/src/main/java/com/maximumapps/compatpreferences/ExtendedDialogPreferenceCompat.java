package com.maximumapps.compatpreferences;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;



public abstract class ExtendedDialogPreferenceCompat extends DialogPreference implements
        DialogInterface.OnDismissListener, DialogInterface.OnClickListener {

    private AlertDialog mDialog;
    private AlertDialog.Builder mBuilder;
    private int mWhichButtonClicked;
    private boolean mNeedInputMethod = false;


    public ExtendedDialogPreferenceCompat(Context context) {
        this(context, null);
    }


    public ExtendedDialogPreferenceCompat(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.dialogPreferenceStyle);
    }


    public ExtendedDialogPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }


    public ExtendedDialogPreferenceCompat(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public void showDialog(Bundle state) {
        Context context = getContext();

        mWhichButtonClicked = DialogInterface.BUTTON_NEGATIVE;

        mBuilder = new AlertDialog.Builder(context)
                .setTitle(getDialogTitle())
                .setIcon(getDialogIcon())
                .setPositiveButton(getPositiveButtonText(), this)
                .setNegativeButton(getNegativeButtonText(), this);

        View contentView = onCreateDialogView();
        if (contentView != null) {
            onBindDialogView(contentView);
            mBuilder.setView(contentView);
        } else {
            mBuilder.setMessage(getDialogMessage());
        }

//        onPrepareDialogBuilder(mBuilder);

//        getPreferenceManager().registerOnActivityDestroyListener(this);

        // Create the dialog
        final Dialog dialog = mDialog = mBuilder.create();
        if (state != null) {
            dialog.onRestoreInstanceState(state);
        }
        if (needInputMethod()) {
            requestInputMethod(dialog);
        }
        dialog.setOnDismissListener(this);
        dialog.show();
    }


    public AlertDialog getDialog() {
        return mDialog;
    }


    protected View onCreateDialogView() {
        if (getDialogLayoutResource() == 0) {
            return null;
        }

        LayoutInflater inflater = LayoutInflater.from(mBuilder.getContext());
        return inflater.inflate(getDialogLayoutResource(), null);
    }


    @Override
    public void onBindViewHolder(PreferenceViewHolder holder) {
        super.onBindViewHolder(holder);

        onInitPreferenceView(holder);
    }


    @CallSuper
    protected void onBindDialogView(View view) {
        View dialogMessageView = view.findViewById(android.R.id.message);

        if (dialogMessageView != null) {
            final CharSequence message = getDialogMessage();
            int newVisibility = View.GONE;

            if (!TextUtils.isEmpty(message)) {
                if (dialogMessageView instanceof TextView) {
                    ((TextView) dialogMessageView).setText(message);
                }

                newVisibility = View.VISIBLE;
            }

            if (dialogMessageView.getVisibility() != newVisibility) {
                dialogMessageView.setVisibility(newVisibility);
            }
        }

        onDialogOpened(view);
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        mWhichButtonClicked = which;
    }


    @Override
    public void onDismiss(DialogInterface dialog) {

//        getPreferenceManager().unregisterOnActivityDestroyListener(this);
        mDialog = null;
        onDialogClosed(mWhichButtonClicked == DialogInterface.BUTTON_POSITIVE);
    }


    protected boolean needInputMethod() {
        return mNeedInputMethod;
    }


    public void setNeedInputMethod(boolean needInputMethod) {
        mNeedInputMethod = needInputMethod;
    }


    /**
     * Sets the required flags on the dialog window to enable input method window to show up.
     */
    private void requestInputMethod(Dialog dialog) {
        Window window = dialog.getWindow();
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


    protected abstract void onInitPreferenceView(PreferenceViewHolder viewHolder);


    protected abstract void onDialogOpened(View rootView);


    protected abstract void onDialogClosed(boolean positiveResult);
}
