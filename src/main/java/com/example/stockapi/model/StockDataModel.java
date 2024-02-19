package com.example.stockapi.model;

import java.math.BigDecimal;

public class StockDataModel {
    private String date;
    private BigDecimal value;

    public StockDataModel(String date, BigDecimal value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
