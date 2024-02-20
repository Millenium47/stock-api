package com.example.stockapi.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

public class StockDataModel implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final String date;
    private final BigDecimal value;

    public StockDataModel(String date, BigDecimal value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public BigDecimal getValue() {
        return value;
    }

}
