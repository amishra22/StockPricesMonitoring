package com.example.service;

import java.util.Collection;
import java.util.Map;

import com.example.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.MyStock;

@Service
public class StockService {
    @Autowired
    private StockDao stockDao;

    public Collection<MyStock> getLatestStockPrices() {
        return this.stockDao.getLatestStockPrices();
    }


    public void deleteStock(String symbol) {
        this.stockDao.deleteStock(symbol);
    }

    public void addCompStock(MyStock myStock) {
        this.stockDao.addCompStock(myStock);
    }

    public Map<String, Double> getHistoricalData(String symbol) {
        return stockDao.getHistoricalData(symbol);
    }

    //public void addCompStock()

}
