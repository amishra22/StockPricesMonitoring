package com.example.dao;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.example.model.MyStock;

@Repository
public class StockDaoImpl implements StockDao {

    protected final JdbcTemplate jdbc;

    @Autowired
    public StockDaoImpl(JdbcTemplate jbc){
        this.jdbc = jbc;
    }

    @Override
    public Collection<MyStock> getLatestStockPrices() {
        List<MyStock> listOfStocks = new ArrayList<MyStock>();
        String query = "SELECT * from real_time_stock_info";
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while(rs.next()) {
            MyStock myStock = new MyStock();
            myStock.setSymbol(rs.getString("symbol"));
            myStock.setPrice(rs.getDouble("stock_value"));
            myStock.setTimeStamp(rs.getString("time_val"));
            myStock.setName(rs.getString("comp_name"));
            listOfStocks.add(myStock);
        }
        return listOfStocks;
    }

    @Override
    public void deleteStock(String symbol) {
        // TODO Auto-generated method stub
        String delQueryReal = "DELETE FROM real_time_stock_info WHERE symbol=" + "\'" + symbol + "\'";
        String delQueryAll = "DELETE FROM all_time_stock_info WHERE symbol=" + "\'" + symbol + "\'";
        jdbc.execute(delQueryReal);
        jdbc.execute(delQueryAll);
    }


    @Override
    public void addCompStock(MyStock myStock) {
        String symbol = myStock.getSymbol();
        double price = myStock.getPrice();
        String ts = myStock.getTimeStamp();
        String compName = myStock.getName();
        String delQuery = "DELETE FROM real_time_stock_info WHERE symbol=" + "\'" + symbol + "\'";
        jdbc.execute(delQuery);
        String insertQueryReal = "INSERT INTO real_time_stock_info " +  "VALUES(" + "\'" + symbol + "\'" + "," + "\'"+ compName + "\'" +
                "," +price + "," + "\'" + ts + "\'" +")";
        String insertQueryAll = "INSERT INTO all_time_stock_info " + "VALUES(" + "\'" + symbol + "\'" + "," + "\'"+ compName + "\'" +
                "," +price + "," + "\'" + ts + "\'" +")";
        jdbc.execute(insertQueryReal);
        jdbc.execute(insertQueryAll);
    }

    @Override
    public List<MyStock> getHistoricalData(String symbol) {
        Map<String, Double> map = new TreeMap<String,Double>();
        Set<String> newSet = new HashSet<>();
        List<MyStock> stocks = new ArrayList<>();
        String query = "SELECT * from all_time_stock_info WHERE symbol=" + "\'" + symbol + "\'";
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while(rs.next()){
            if(!newSet.contains(rs.getString("time_val"))) {
                MyStock myStock = new MyStock();
                myStock.setSymbol(rs.getString("symbol"));
                myStock.setPrice(rs.getDouble("stock_value"));
                myStock.setTimeStamp(rs.getString("time_val"));
                myStock.setName(rs.getString("comp_name"));
                newSet.add(rs.getString("time_val"));
                stocks.add(myStock);
            }

        }
        Collections.sort(stocks, new Comparator<MyStock>() {
            @Override
            public int compare(MyStock o1, MyStock o2) {
                return o1.getTimeStamp().compareTo(o2.getTimeStamp());
            }
        });

        return stocks;
    }
}
