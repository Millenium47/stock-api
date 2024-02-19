package com.example.stockapi.controller;

import com.example.stockapi.enums.ApiProviders;
import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.factory.StockServiceFactory;
import com.example.stockapi.model.StockDataModel;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/v1/stocks")
@CrossOrigin("http://localhost:3000")
public class StockControllerImpl {
    private final StockServiceFactory serviceFactory;

    @Autowired
    public StockControllerImpl(StockServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @GetMapping("/daily/{symbol}")
    public ArrayList<StockDataModel> getTimeSeriesDaily(@PathVariable String symbol) {
        StockService stockService = serviceFactory.getService(ApiProviders.ALPHA_VANTAGE);

        return stockService.fetchTimeSeries(symbol, TimeSeriesType.DAILY);
    }

    @GetMapping("/weekly/{symbol}")
    public ArrayList<StockDataModel> getTimeSeriesWeekly(@PathVariable String symbol) {
        StockService stockService = serviceFactory.getService(ApiProviders.ALPHA_VANTAGE);

        return stockService.fetchTimeSeries(symbol, TimeSeriesType.WEEKLY);
    }

    @GetMapping("/monthly/{symbol}")
    public ArrayList<StockDataModel> getTimeSeriesMonthly(@PathVariable String symbol) {
        StockService stockService = serviceFactory.getService(ApiProviders.ALPHA_VANTAGE);

        return stockService.fetchTimeSeries(symbol, TimeSeriesType.MONTHLY);
    }

}
