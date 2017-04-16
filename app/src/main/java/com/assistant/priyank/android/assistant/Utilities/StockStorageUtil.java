package com.assistant.priyank.android.assistant.Utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.ArrayList;

/**
 * Created by primanda on 4/16/2017.
 */

public class StockStorageUtil {

    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private static final String STOCK_LIST_KEY = "stock_list";

    public StockStorageUtil(Context context) {
        this.mContext = context;
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }

    public Boolean addTicker(String ticker) {
        StringBuilder builder;
        if(mSharedPreferences.contains(STOCK_LIST_KEY)) {
            builder = new StringBuilder(mSharedPreferences.getString(STOCK_LIST_KEY, ""));
        }
        else {
            builder = new StringBuilder("");
        }
        if(TextUtils.isEmpty(builder)) {
            builder.append(ticker);
        }
        else {
            builder.append("," + ticker);
        }

        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(STOCK_LIST_KEY, builder.toString());
        editor.commit();
        return true;
    }

    public String getTickers()
    {
        String stockDataString = "";
        if(mSharedPreferences.contains(STOCK_LIST_KEY)) {
            stockDataString = mSharedPreferences.getString(STOCK_LIST_KEY, "");
        }
        return stockDataString;
    }

    public void deleteStockTicker(int position) {
        String tickers = getTickers();
        String[] tickerArray = tickers.split(",");
        StringBuilder newTickerList = new StringBuilder("");
        for(int i = 0; i < tickerArray.length; i++) {
            if (i == position) {
                continue;
            }
            newTickerList.append(tickerArray[i] + ",");
        }
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(STOCK_LIST_KEY, newTickerList.toString());
        editor.commit();
    }
}
