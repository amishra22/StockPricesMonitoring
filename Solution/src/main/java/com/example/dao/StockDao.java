package com.example.dao;

import com.example.model.MyStock;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Abhinav on 12-11-2016.
 */
public interface StockDao {
    Collection<MyStock> getLatestStockPrices();

    void deleteStock(String symbol);

    void addCompStock(MyStock myStock);

    List<MyStock> getHistoricalData(String symbol);

}
