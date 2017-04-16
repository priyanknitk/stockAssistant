package com.assistant.priyank.android.assistant.Models;

/**
 * Created by primanda on 4/16/2017.
 */

public  class StockData  {
    public String StockTicker;
    public String StockValue;
    public String Change;
    public ChangeDirection ChangeDirection;

    public StockData() {}

    public StockData(StockDataModel stockDataModel) {
        this.StockTicker = stockDataModel.t;
        this.StockValue = stockDataModel.l_cur;
        this.Change = stockDataModel.c.substring(1);
        if(stockDataModel.c.charAt(0) == '+') {
            this.ChangeDirection = ChangeDirection.UP;
        }
        else {
            this.ChangeDirection = ChangeDirection.DOWN;
        }
    }
}

