package com.example.stockapi.service;

import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;

import java.util.List;

public interface StockService {
    List<StockDataModel> fetchTimeSeries(String symbol, TimeSeriesType timeSeriesType);
}