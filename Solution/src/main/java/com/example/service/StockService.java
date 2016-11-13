package com.example.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

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
        if(symbol == null || symbol.length() < 1) {
            return;
        }
        System.out.println("String is " + symbol);
        symbol = symbol.substring(1, symbol.length() - 1);
        Stock stock = YahooFinance.get(symbol);
        if(stock!= null) {
            java.util.Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd-hh-mm-ss");
            String dateToStr = format.format(date);
            MyStock myStock = new MyStock();
            myStock.setSymbol(stock.getSymbol());
            myStock.setPrice(stock.getQuote(true).getPrice().doubleValue());
            myStock.setName(stock.getName());
            myStock.setTime(dateToStr);
            this.stockDao.addCompStock(myStock);
        }
    }

    public Map<String, Double> getHistoricalData(String symbol) {
        if(symbol == null || symbol.length() < 1) {
            return null;
        }
        return stockDao.getHistoricalData(symbol);
    }

}
