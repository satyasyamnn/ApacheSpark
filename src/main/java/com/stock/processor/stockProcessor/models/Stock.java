package com.stock.processor.stockProcessor.models;

import lombok.*;
import java.io.Serializable;

@Getter
@Setter
public class Stock implements Serializable {
    private String pricingDate;
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
