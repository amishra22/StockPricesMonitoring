package com.example.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.jersey.spi.dispatch.RequestDispatcher;
import jdk.nashorn.internal.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import com.example.model.MyStock;
import com.example.service.StockService;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;

@RestController
@RequestMapping("/")
public class StockController {

    @Autowired
    StockService stockService;

    /*@RequestMapping(value = "/add/{sym_name}", method = RequestMethod.GET)
    public Collection<MyStock> addCompStock(@RequestParam("sym_name") String symbol) throws IOException {
        Stock stock = YahooFinance.get(symbol);

        if(stock!= null) {
            java.util.Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd-hh-mm-ss");
            String dateToStr = format.format(date);


            MyStock myStock = new MyStock();
            myStock.setSymbol(stock.getSymbol());
            myStock.setPrice(stock.getQuote(true).getPrice().doubleValue());
            myStock.setName(stock.getName());
            System.out.println("Time " + dateToStr);
            myStock.setTime(dateToStr);
            //System.out.println("Name : " + stock.getName() + " Price : " + myStock.getPrice());

            stockService.addCompStock(myStock);
            return stockService.getLatestStockPrices();
        }
        return null;
    }
*/
    @RequestMapping(value = "addComp/sym_name", method = RequestMethod.POST)
    public void addCompStock(@RequestBody String symbol) throws IOException {
        System.out.println("Hello for ajax " + symbol);
        symbol = symbol.substring(1, symbol.length() - 1);
        Stock stock = YahooFinance.get(symbol);
        String res = "fail";
        if(stock!= null) {
            java.util.Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd-hh-mm-ss");
            String dateToStr = format.format(date);


            MyStock myStock = new MyStock();
            myStock.setSymbol(stock.getSymbol());
            myStock.setPrice(stock.getQuote(true).getPrice().doubleValue());
            myStock.setName(stock.getName());
            System.out.println("Time " + dateToStr);
            myStock.setTime(dateToStr);
            //System.out.println("Name : " + stock.getName() + " Price : " + myStock.getPrice());

            stockService.addCompStock(myStock);
            res = "suc";
        }
    }

    @RequestMapping(value = "getLatest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<MyStock> getLatestStockPrices(){
        return stockService.getLatestStockPrices();
    }

    /*@RequestMapping(value = "/getAll/sym_name", method = RequestMethod.POST)
    public Map<String,Double> getHistoricalData(@RequestParam("sym_name") String symbol){
        return stockService.getHistoricalData(symbol);
    }*/

    @RequestMapping(value = "/getAll/sym_name", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Produces("application/json")
    public Map<String,Double> getHistoricalData(@RequestBody String symbol) {
        System.out.println("Symbol name is " + symbol);
        symbol = symbol.substring(1,symbol.length()-1);
        return stockService.getHistoricalData(symbol);
    }

    @RequestMapping(value ="/remove/{sym_name}", method = RequestMethod.GET)
    public void deleteStock(@RequestParam("sym_name") String symbol) {
        symbol = symbol.substring(1,symbol.length()-1);
        stockService.deleteStock(symbol);
    }
}
