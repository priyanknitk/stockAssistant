package com.assistant.priyank.android.assistant.Models;

/**
 * Created by primanda on 4/16/2017.
 */

public  class StockData  {
    public String StockTicker;
    public String StockValue;

    public StockData() {}

    public StockData(StockDataModel stockDataModel) {
        this.StockTicker = stockDataModel.t;
        this.StockValue = stockDataModel.l_cur;
    }
}

