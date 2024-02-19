package com.example.stockapi.controller;

import com.example.stockapi.enums.ApiProviders;
import com.example.stockapi.factory.StockServiceFactory;
import com.example.stockapi.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/stocks")
public class StockControllerImpl {
    private final StockServiceFactory serviceFactory;

    @Autowired
    public StockControllerImpl(StockServiceFactory serviceFactory) {
        this.serviceFactory = serviceFactory;
    }

    @GetMapping("/daily/{symbol}")
    public String getTimeSeriesDaily(@PathVariable String symbol) {
        StockService stockService = serviceFactory.getService(ApiProviders.ALPHA_VANTAGE);
        return stockService.fetchTimeSeriesDaily(symbol);
    }
}
