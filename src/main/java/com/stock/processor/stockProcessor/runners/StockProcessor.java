package com.stock.processor.stockProcessor.runners;

import com.stock.processor.stockProcessor.models.Stock;
import com.stock.processor.stockProcessor.readers.StockDataReader;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StockProcessor implements ApplicationRunner {

    @Autowired
    private StockDataReader stockDataReader;

    public StockProcessor(StockDataReader stockDataReader) {
        this.stockDataReader = stockDataReader;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Dataset<Row> data =  stockDataReader.readRawStockData();
        Dataset<Stock> stocks  = stockDataReader.cleanUpStockDataColumns(data);
        stockDataReader.processStockData(stocks);
    }
}
