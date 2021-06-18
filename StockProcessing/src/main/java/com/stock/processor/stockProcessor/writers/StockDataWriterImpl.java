package com.stock.processor.stockProcessor.writers;

import com.stock.processor.stockProcessor.configuration.ApplicationConfiguration;
import com.stock.processor.stockProcessor.configuration.DatabaseConfiguration;
import com.stock.processor.stockProcessor.models.Stock;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SaveMode;

import java.util.Properties;

public class StockDataWriterImpl implements StockDataWriter {

    private final ApplicationConfiguration config;
    private final DatabaseConfiguration dbConfig;

    public StockDataWriterImpl(ApplicationConfiguration config, DatabaseConfiguration dbConfig) {
        this.config = config;
        this.dbConfig = dbConfig;
    }

    @Override
    public void processStockData(Dataset<Stock> dataSet) {
        dataSet.write().mode(SaveMode.Overwrite).parquet(config.getOutputPath());
    }

    @Override
    public void saveToPersistentStore(Dataset<Stock> dataSet) {
        Properties prop = new Properties();
        prop.setProperty("driver", dbConfig.getDriver());
        prop.setProperty("user", dbConfig.getUserName());
        prop.setProperty("password", dbConfig.getPassWord());
        dataSet.write().mode(SaveMode.Overwrite).jdbc(dbConfig.getDbUrl(), "Stocks", prop);
    }
}
