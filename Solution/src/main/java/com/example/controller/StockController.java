package com.example.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import com.example.model.MyStock;
import com.example.service.StockService;
import javax.ws.rs.Produces;

@RestController
@RequestMapping("/")
@EnableScheduling
@Component
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
/*
    @Scheduled(fixedRate = 300000)
    public void updateEveryFiveMin(){
        System.out.println("Calling next Update after 5 min starting -> " + new Date());
        stockService.updateEveryFiveMin();
    }*/

    @RequestMapping(value = "addComp/sym_name", method = RequestMethod.POST)
    public void addCompStock(@RequestBody String symbol){
        try {
            stockService.addCompStock(symbol);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "getLatest", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<MyStock> getLatestStockPrices(){
        return stockService.getLatestStockPrices();
    }

    @RequestMapping(value = "/getAll/sym_name", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Produces("application/json")
    public List<MyStock> getHistoricalData(@RequestParam(value = "sym_name") String symbol) {
        return stockService.getHistoricalData(symbol);
    }

    @RequestMapping(value ="/remove/sym_name", method = RequestMethod.GET)
    public void deleteStock(@RequestParam("sym_name") String symbol) {
        stockService.deleteStock(symbol);
    }
}
