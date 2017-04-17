package com.assistant.priyank.android.assistant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.assistant.priyank.android.assistant.Models.StockData;
import com.assistant.priyank.android.assistant.Models.StockDataModel;
import com.assistant.priyank.android.assistant.Utilities.NetworkUtils;
import com.assistant.priyank.android.assistant.Utilities.StockNewsActivity;
import com.assistant.priyank.android.assistant.Utilities.StockStorageUtil;
import com.assistant.priyank.android.assistant.Utilities.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<StockData>>, SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView mRecyclerView;
    private AssistantAdapter mAdapter;
    private SharedPreferences mSharedPreferences;
    private static final int STOCK_LOADER = 715;
    private static final String FINANCE_BASE_URL = "http://finance.google.com/finance/info";
    private StockStorageUtil mStockStorageUtil;
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new AssistantAdapter();
        mRecyclerView.setAdapter(mAdapter);
        Loader<List<StockData>> stockLoader = getSupportLoaderManager().getLoader(STOCK_LOADER);
        if(stockLoader != null) {
            getSupportLoaderManager().restartLoader(STOCK_LOADER, null, this);
        }
        else {
            getSupportLoaderManager().initLoader(STOCK_LOADER, null, this);
        }

        mSharedPreferences =  PreferenceManager.getDefaultSharedPreferences(this);
        mStockStorageUtil = new StockStorageUtil(this);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                mStockStorageUtil.deleteStockTicker(viewHolder.getAdapterPosition());
                getSupportLoaderManager().restartLoader(STOCK_LOADER, null, MainActivity.this);
            }
        });
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getSupportLoaderManager().destroyLoader(STOCK_LOADER);
    }

    @Override
    public Loader onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<List<StockData>>(this) {
            @Override
            protected void onStartLoading() {
                ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
                mProgressBar.setVisibility(View.VISIBLE);
                forceLoad();
            }

            @Override
            public List<StockData> loadInBackground() {
                List<StockData> stockDataList = new ArrayList<StockData>();
                String stockDataString = mStockStorageUtil.getTickers();
                if(TextUtils.isEmpty(stockDataString))
                {
                    return stockDataList;
                }
                try {
                    String response = NetworkUtils.GetResponseFromHttpUrl(NetworkUtils.GetUrl(FINANCE_BASE_URL, stockDataString));
                    StockDataModel[] stockDataModelArray = Utilities.ParseFinanceResponse(response);
                    for (StockDataModel stockDataModel :
                            stockDataModelArray) {
                        stockDataList.add(new StockData(stockDataModel));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return stockDataList;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<List<StockData>> loader, List<StockData> data) {
        ProgressBar mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.INVISIBLE);
        mAdapter.setStockDataList(data);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mAdapter.setStockDataList(new ArrayList<StockData>());
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getSupportLoaderManager().restartLoader(STOCK_LOADER, null, this);
    }

    public void onAddButtonClick(View view) {
        EditText stockTicker = (EditText) findViewById(R.id.add_stock_edit_text);
        if(TextUtils.isEmpty(stockTicker.getText().toString())) {
            return;
        }
        mStockStorageUtil.addTicker(stockTicker.getText().toString());
        view.clearFocus();
        stockTicker.setText("");
    }
    public void OnTileClick(View view) {
        TextView quoteNameTextView = (TextView) view.findViewById(R.id.quoteNameTextView);
        Intent intent = new Intent(view.getContext(), StockNewsActivity.class);
        intent.putExtra("TICKER", quoteNameTextView.getText().toString());
        startActivity(intent);
    }
}
