package com.example.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Abhinav on 13-11-2016.
 */
public class MyStockTest {

    private MyStock myStock;

    @Before
    public void Setup(){
        myStock = new MyStock();
    }

    @Test
    public void testGetTimeStamp() throws Exception {
        myStock.setTimeStamp("10:10:10");
        assertTrue(myStock.getTimeStamp().equals(new String("10:10:10")));
    }

    @Test
    public void testSetTimeStamp() throws Exception {
        myStock.setTimeStamp("10:10:10");
        assertTrue(myStock.timeStamp.equals(new String("10:10:10")));
    }

    @Test
    public void testGetName() throws Exception {
        myStock.setName("LogicMonitor");
        assertTrue(myStock.getName().equals(new String("LogicMonitor")));
    }

    @Test
    public void testSetName() throws Exception {
        myStock.setName("LogicMonitor");
        assertTrue(myStock.getName().equals(new String("LogicMonitor")));
    }

    @Test
    public void testGetSymbol() throws Exception {
        myStock.setSymbol("LM");
        assertTrue(myStock.getSymbol().equals(new String("LM")));
    }

    @Test
    public void testSetSymbol() throws Exception {
        myStock.setSymbol("LM");
        assertTrue(myStock.getSymbol().equals(new String("LM")));
    }

    @Test
    public void testGetPrice() throws Exception {
        double DELTA = 1e-15;
        myStock.setPrice(1234.56);
        assertEquals(myStock.getPrice(),1234.56,DELTA);
    }

    @Test
    public void testSetPrice() throws Exception {
        double DELTA = 1e-15;
        myStock.setPrice(1234.56);
        assertEquals(myStock.getPrice(),1234.56,DELTA);
    }
}