package com.stock.processor.stockProcessor.readers;

import com.stock.processor.stockProcessor.models.Stock;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

public interface StockDataReader {
    Dataset<Row> readRawStockData();
    Dataset<Stock> cleanUpStockDataColumns(Dataset<Row> dataSet);
    void processStockData(Dataset<Stock> dataSet);
}
