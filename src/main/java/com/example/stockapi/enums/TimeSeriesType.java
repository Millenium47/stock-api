package com.example.stockapi.enums;

public enum TimeSeriesType {
    DAILY("Time Series (Daily)", "TIME_SERIES_DAILY"),
    WEEKLY("Time Series (Weekly)", "TIME_SERIES_WEEKLY"),
    MONTHLY("Time Series (Monthly)", "TIME_SERIES_MONTHLY");

    private final String key;
    private final String apiFunction;

    TimeSeriesType(String key, String apiFunction) {
        this.key = key;
        this.apiFunction = apiFunction;
    }

    public String getKey() {
        return this.key;
    }

    public String getApiFunction() {
        return this.apiFunction;
    }
}
