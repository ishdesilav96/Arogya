package com.example.arogya.manager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkManager {

    static boolean isNetWorkAvailable;

    public static boolean isAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        isNetWorkAvailable = activeNetworkInfo != null && activeNetworkInfo.isConnected();

        if (isNetWorkAvailable) {
            new Connected();
        }

        if (!isNetWorkAvailable) {
           // UIHelper.showOkAlertDialog(context.getString(R.string.no_internet_title), context.getString(R.string.no_internet_message), context);
            Toast.makeText(context, "No Connection", Toast.LENGTH_SHORT).show();
            //            UIHelper.showLongToast(context, context.getString(R.string.no_internet_message));
        }

        return isNetWorkAvailable;
    }

    private static class Connected extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                URL url = new URL("https://www.google.com/");
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(1000);
                httpURLConnection.connect();
                if (httpURLConnection.getResponseCode() == 200) {
                    isNetWorkAvailable = true;
                } else {
                    isNetWorkAvailable = false;
                }
            } catch (IOException e) {
                e.printStackTrace();
                isNetWorkAvailable = false;
            }
            return null;
        }
    }

}
