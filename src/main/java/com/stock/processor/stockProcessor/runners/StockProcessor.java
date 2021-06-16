package com.stock.processor.stockProcessor.runners;

import com.stock.processor.stockProcessor.models.Stock;
import com.stock.processor.stockProcessor.readers.StockDataReader;
import com.stock.processor.stockProcessor.writers.StockDataWriter;
import lombok.AllArgsConstructor;
import org.apache.spark.sql.Dataset;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

@AllArgsConstructor
public class StockProcessor implements ApplicationRunner {

    private final StockDataReader stockDataReader;
    private final StockDataWriter stockDataWriter;

    @Override
    public void run(ApplicationArguments args) {
        Dataset<Stock> data = stockDataReader.readRawStockData();
        stockDataWriter.processStockData(data);
    }
}
