package com.stock.processor.stockProcessor.readers;

import com.stock.processor.stockProcessor.models.Stock;
import org.apache.spark.sql.Dataset;

public interface StockDataReader {
    Dataset<Stock> readRawStockData();
}
