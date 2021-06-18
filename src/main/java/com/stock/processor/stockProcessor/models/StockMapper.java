package com.stock.processor.stockProcessor.models;

import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StockMapper implements MapFunction<Row, Stock> {
    @Override
    public Stock call(Row row) throws Exception {
        Stock stock = new Stock();
        stock.setPricingDate(parseDate(row.getAs("pricingDate")));
        stock.setStockName(row.getAs("stockName"));
        stock.setOpenPrice(row.getAs("openPrice"));
        stock.setHighPrice(row.getAs("highPrice"));
        stock.setLowPrice(row.getAs("lowPrice"));
        stock.setClosePrice(row.getAs("closePrice"));
        stock.setTotalShares(row.getAs("totalShares"));
        stock.setTotalTrades(row.getAs("totalTrades"));
        stock.setSpreadHighLow(row.getAs("spreadHighLow"));
        stock.setSpreadOpenClose(row.getAs("spreadOpenClose"));
        return stock;
    }

    private Date parseDate(String dateToParse) throws ParseException {
        Date pricingDate = new SimpleDateFormat("d-MMM-yyyy", Locale.ENGLISH).parse(dateToParse);
        return pricingDate;
    }
}
