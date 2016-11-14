package com.example.dao;

import com.example.model.MyStock;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by Abhinav on 13-11-2016.
 */
public class StockDaoImplTest {

    @Mock
    private JdbcTemplate mockJdbcTemplate;

    @Mock
    private SqlRowSet mockSqlRowSet;

    private StockDaoImpl sdi;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        String mockSymbol = "FBA";
        String mockTime = "20:20:20 10:10:10";
        Double mockValue = 100.234;
        String mockName = "Few Bussiness";

        when(mockSqlRowSet.getString("symbol")).thenReturn(mockSymbol);
        when(mockSqlRowSet.getString("time_val")).thenReturn(mockTime);
        when(mockSqlRowSet.getString("comp_name")).thenReturn(mockName);
        when(mockSqlRowSet.getDouble("stock_value")).thenReturn(mockValue);
        when(mockSqlRowSet.next()).thenReturn(true).thenReturn(false);
        when(mockJdbcTemplate.queryForRowSet(any(String.class))).thenReturn(mockSqlRowSet);

        sdi = new StockDaoImpl(mockJdbcTemplate);
    }

    @Test
    public void testGetLatestStockPrices() throws Exception {

        Collection<MyStock> output =  sdi.getLatestStockPrices();
        double DELTA = 1e-15;
        for(MyStock ms : output){
            assertEquals("FBA", ms.getSymbol());
            assertEquals("20:20:20 10:10:10", ms.getTimeStamp());
            assertEquals(100.234,ms.getPrice(), DELTA);
            assertEquals("Few Bussiness", ms.getName());
        }
    }

    @Test
    public void testDeleteStock() throws Exception {
        String temp = "FBA";
        sdi.deleteStock(temp);
        verify(mockJdbcTemplate, times(1)).execute("DELETE FROM real_time_stock_info WHERE symbol='FBA'");
        verify(mockJdbcTemplate,times(1)).execute("DELETE FROM all_time_stock_info WHERE symbol='FBA'");
    }

    @Test
    public void testAddCompStock() throws Exception {
        MyStock myStock = new MyStock();
        myStock.setName("abc");
        myStock.setTimeStamp("10:10:10 12:12:12");
        myStock.setPrice(123.12);
        myStock.setSymbol("A");
        sdi.addCompStock(myStock);
        verify(mockJdbcTemplate, times(1)).execute("DELETE FROM real_time_stock_info WHERE symbol='A'");
        verify(mockJdbcTemplate, times(1)).execute("INSERT INTO real_time_stock_info VALUES('A','abc',123.12,'10:10:10 12:12:12')");
        verify(mockJdbcTemplate, times(1)).execute("INSERT INTO all_time_stock_info VALUES('A','abc',123.12,'10:10:10 12:12:12')");
    }

    @Test
    public void testGetHistoricalData() throws Exception {
        String temp = "FBA";
        double DELTA = 1e-15;
        List<MyStock> output = sdi.getHistoricalData(temp);
        assertEquals(1,output.size());

    }
}