package com.example.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.List;
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

    @Scheduled(fixedRate = 300000)
    public void updateEveryFiveMin(){
        stockService.updateEveryFiveMin();
    }

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
