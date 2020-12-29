package com.example.arogya.helpers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.arogya.R;
import com.example.arogya.models.SnackType;

import com.google.android.material.snackbar.Snackbar;


public class UIHelper {

    public static void showLongSnackbar(View view, String message, SnackType snackType, Context context) {
        Snackbar snackbar = Snackbar.make(view.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View snackView = snackbar.getView();
        if (snackType == SnackType.ERROR) {
            snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else if (snackType == SnackType.WARNING) {
            snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        } else {
            snackView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        }
        TextView snackTextView = snackView.findViewById(com.google.android.material.R.id.snackbar_text);
        snackTextView.setTextColor(Color.WHITE);
        snackTextView.setMaxLines(2);
        Typeface font = Typeface.createFromAsset(context.getAssets(), "fonts/avenir_medium.ttf");
        snackTextView.setTypeface(font);

        snackbar.show();
    }

    public static void showOkAlertDialog(String title, String message, Context context) {

        String ok =  "Ok";
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton(ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .show();
    }


    public static void showAlert(String title, String message, Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setCancelable(false)
                .setMessage(message)
                .show();

    }

    public static void disableElement(View view) {
        view.setEnabled(false);
        view.setAlpha(0.5f);
    }

    public static void enableElement(View view) {
        view.setEnabled(true);
        view.setAlpha(1.0f);
    }

    public static void hideSoftKeyBoard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (activity.getCurrentFocus() != null) {
            try {
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void setupUI(View view, final Activity activity) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideSoftKeyBoard(activity);
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity);
            }
        }
    }



}