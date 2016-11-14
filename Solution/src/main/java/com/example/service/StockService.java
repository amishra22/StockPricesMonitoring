package com.example.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.example.dao.StockDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.model.MyStock;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

@Service
public class StockService {
    @Autowired
    private StockDao stockDao;

    public Collection<MyStock> getLatestStockPrices() {
        return this.stockDao.getLatestStockPrices();
    }


    public void deleteStock(String symbol) {
        if(symbol == null || symbol.length()<1) {
            return;
        }
        this.stockDao.deleteStock(symbol);
    }

    public void addCompStock(String symbol) throws IOException {
        if(symbol == null || symbol.length() <= 2) {
            return;
        }
        symbol = symbol.substring(1, symbol.length() - 1);
        Stock stock = null;
        try {
            stock = YahooFinance.get(symbol);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(stock!= null) {
            java.util.Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd-hh-mm-ss");
            String dateToStr = format.format(date);
            MyStock myStock = new MyStock();
            myStock.setSymbol(stock.getSymbol());
            myStock.setPrice(stock.getQuote(true).getPrice().doubleValue());
            myStock.setName(stock.getName());
            myStock.setTimeStamp(dateToStr);
            this.stockDao.addCompStock(myStock);
        }
    }

    public List<MyStock> getHistoricalData(String symbol) {
        if(symbol == null || symbol.length() < 1) {
            return null;
        }
        return stockDao.getHistoricalData(symbol);
    }

    public void updateEveryFiveMin() {
        Collection<MyStock> stocks = stockDao.getLatestStockPrices();
        for( MyStock stock : stocks) {
            try {
                String tempStr = stock.getSymbol();
                tempStr = "\"" + tempStr + "\"";
                this.addCompStock(tempStr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
