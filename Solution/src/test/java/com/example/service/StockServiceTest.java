package com.example.service;

import com.example.dao.StockDao;
import com.example.model.MyStock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.*;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Abhinav on 13-11-2016.
 */
public class StockServiceTest {
    @Mock
    private StockDao mockStockDao;

    private StockService stockService;

    @Before
    public void Setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetLatestStockPrices() throws Exception {

    }

    @Test
    public void testDeleteStock() throws Exception {

    }

    @Test
    public void testAddCompStock() throws Exception {

    }

    @Test
    public void testGetHistoricalData() throws Exception {

    }

    @Test
    public void testUpdateEveryFiveMin() throws Exception {

    }
}