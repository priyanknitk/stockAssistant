package com.assistant.priyank.android.assistant;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.assistant.priyank.android.assistant.Models.StockData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by primanda on 4/16/2017.
 */

public class AssistantAdapter extends RecyclerView.Adapter<AssistantAdapter.AssistantViewHolder> {


    List<StockData> mStockDataList;

    public AssistantAdapter() {
        mStockDataList = new ArrayList<>();
    }


    public void setStockDataList(List<StockData> stockDataList) {
        mStockDataList = stockDataList;
    }


    @Override
    public AssistantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View thisItemsView = inflater.inflate(R.layout.list_item_layout,
                parent, false);
        return new AssistantViewHolder(thisItemsView);
    }

    @Override
    public void onBindViewHolder(AssistantViewHolder holder, int position) {
        if (position < 0 || position >= mStockDataList.size()) return;
        holder.bind(mStockDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mStockDataList.size();
    }

    public class AssistantViewHolder extends RecyclerView.ViewHolder {

        public AssistantViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(StockData stockData) {
            View view = this.itemView;
            TextView quoteNameTextView = (TextView) view.findViewById(R.id.quoteNameTextView);
            TextView quoteValueTextView = (TextView) view.findViewById(R.id.quoteValueTextView);
            quoteNameTextView.setText(stockData.StockTicker);
            quoteValueTextView.setText(Html.fromHtml(stockData.StockValue));
        }
    }
}
