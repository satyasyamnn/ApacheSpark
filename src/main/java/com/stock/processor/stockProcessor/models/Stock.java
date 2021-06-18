package com.stock.processor.stockProcessor.models;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
public class Stock implements Serializable {
    private Date pricingDate;
    private String stockName;
    private Double openPrice;
    private Double highPrice;
    private Double lowPrice;
    private Double closePrice;
    private Integer totalShares;
    private Integer totalTrades;
    private Double spreadHighLow;
    private Double spreadOpenClose;
}
