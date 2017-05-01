package com.assistant.priyank.android.assistant.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.assistant.priyank.android.assistant.Models.StockDataModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

/**
 * Created by primanda on 4/16/2017.
 */

public class Utilities {
    public static StockDataModel[] ParseFinanceResponse(String reponseString) {
        reponseString = reponseString.substring(3);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        return gson.fromJson(reponseString, StockDataModel[].class);
    }

    public static boolean IsConnectedToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
