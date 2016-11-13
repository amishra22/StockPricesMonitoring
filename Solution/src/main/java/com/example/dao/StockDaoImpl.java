package com.example.dao;

import java.security.Timestamp;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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
        // TODO Auto-generated method stub

        List<MyStock> listOfStocks = new ArrayList<MyStock>();
        String query = "SELECT * from real_time_stock_info";
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while(rs.next()) {
            MyStock myStock = new MyStock();
            myStock.setSymbol(rs.getString("symbol"));
            myStock.setPrice(rs.getDouble("stock_value"));
            myStock.setTime(rs.getString("time_val"));
            myStock.setName(rs.getString("comp_name"));
            listOfStocks.add(myStock);
        }
        //System.out.println("getLatest");
        return listOfStocks;
        //return myStock;
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
        // TODO Add
        String symbol = myStock.getSymbol();
        double price = myStock.getPrice();
        String ts = myStock.getTime();
        String compName = myStock.getName();
        String delQuery = "DELETE FROM real_time_stock_info WHERE symbol=" + "\'" + symbol + "\'";
        jdbc.execute(delQuery);
        String insertQueryReal = "INSERT INTO real_time_stock_info " +  "VALUES(" + "\'" + symbol + "\'" + "," + "\'"+ compName + "\'" +
                "," +price + "," + "\'" + ts + "\'" +")";
        String insertQueryAll = "INSERT INTO all_time_stock_info " + "VALUES(" + "\'" + symbol + "\'" + "," + "\'"+ compName + "\'" +
                "," +price + "," + "\'" + ts + "\'" +")";
        jdbc.execute(insertQueryReal);
        jdbc.execute(insertQueryAll);
        //System.out.println("Added successfully");
    }

    @Override
    public Map<String, Double> getHistoricalData(String symbol) {
        Map<String, Double> map = new TreeMap<String,Double>();
        String query = "SELECT * from all_time_stock_info WHERE symbol=" + "\'" + symbol + "\'";
        SqlRowSet rs = jdbc.queryForRowSet(query);
        while(rs.next()){
            String time = rs.getString("time_val");
            Double price = rs.getDouble("stock_value");
            if(!map.containsKey(time)) {
                map.put(time,price);
            }
        }
        for(Map.Entry<String,Double> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
        //System.out.println("getHist");
        return map;
    }
}
