package com.stock.processor.stockProcessor.runners;

import com.stock.processor.stockProcessor.models.Stock;
import com.stock.processor.stockProcessor.readers.StockDataReader;
import com.stock.processor.stockProcessor.writers.StockDataWriter;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StockProcessor implements ApplicationRunner {

    @Autowired
    private final StockDataReader stockDataReader;
    @Autowired
    private final StockDataWriter stockDataWriter;

    public StockProcessor(StockDataReader stockDataReader, StockDataWriter stockDataWriter) {
        this.stockDataReader = stockDataReader;
        this.stockDataWriter = stockDataWriter;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Dataset<Stock> data =  stockDataReader.readRawStockData();
        stockDataWriter.processStockData(data);
    }
}
