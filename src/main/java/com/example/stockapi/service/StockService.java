package com.example.stockapi.service;

import com.example.stockapi.enums.TimeSeriesType;
import com.example.stockapi.model.StockDataModel;

import java.util.ArrayList;

public interface StockService {
    ArrayList<StockDataModel> fetchTimeSeries(String symbol, TimeSeriesType timeSeriesType);

    ArrayList<StockDataModel> fetchTimeSeriesIntraday(String symbol, TimeSeriesType timeSeriesType, String interval);
}