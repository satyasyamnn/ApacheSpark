package com.stock.processor.stockProcessor.writers;

import com.stock.processor.stockProcessor.models.Stock;
import org.apache.spark.sql.Dataset;

public interface StockDataWriter {
    void processStockData(Dataset<Stock> dataSet);
}
