package com.stock.processor.stockProcessor.writers;

import com.stock.processor.stockProcessor.configuration.ApplicationConfiguration;
import com.stock.processor.stockProcessor.models.Stock;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SaveMode;

public class StockDataWriterImpl implements StockDataWriter {

    private final ApplicationConfiguration config;

    public StockDataWriterImpl(ApplicationConfiguration config) {
        this.config = config;
    }

    @Override
    public void processStockData(Dataset<Stock> dataSet) {
        dataSet.write().mode(SaveMode.Overwrite).parquet(config.getOutputPath());
    }
}
