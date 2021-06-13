package com.stock.processor.stockProcessor.readers;

import com.stock.processor.stockProcessor.configuration.ApplicationConfiguration;
import com.stock.processor.stockProcessor.models.Stock;
import org.apache.commons.io.FileUtils;
import org.apache.spark.sql.*;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StockDataReaderImpl implements StockDataReader{

    private final ApplicationConfiguration config;
    private final SQLContext sqlContext;

    public StockDataReaderImpl(ApplicationConfiguration config, SQLContext sqlContext) {
        this.config = config;
        this.sqlContext = sqlContext;
    }

    @Override
    public Dataset<Row> readRawStockData() {
        String filesToRead = config.getInputPath() + "/*." + config.getInputFileFormat();
        return  sqlContext.read().format(config.getInputFileFormat()).options(getOptions()).load(filesToRead);
    }

    @Override
    public Dataset<Stock> cleanUpStockDataColumns(Dataset<Row> dataSet) {
        Dataset<Row> newDataSet =  dropColumns(dataSet);
        newDataSet = alterColumnNames(newDataSet);
        newDataSet = addCustomColumns(newDataSet);
        Encoder<Stock> encoder = Encoders.bean(Stock.class);
        return newDataSet.as(encoder);
    }

    @Override
    public void processStockData(Dataset<Stock> dataSet) throws IOException {
        String pathToSave = config.getOutputPath() +"/" + "stockProcessing";
        Path path = Paths.get(pathToSave);
        dataSet.write().mode(SaveMode.Overwrite).parquet(pathToSave);
    }

    private Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("header", "true");
        options.put("inferSchema", "true");
        return options;
    }

    private Dataset<Row> dropColumns(Dataset<Row> dataSet) {
        List<String> excludeFields = Arrays.asList("% Deli. Qty to Traded Qty", "Deliverable Quantity", "Spread Close-Open", "Spread High-Low", "Total Turnover (Rs.)", "WAP");
        Seq<String> cols =  JavaConverters.collectionAsScalaIterableConverter(excludeFields).asScala().toSeq();
        return dataSet.drop(cols);
    }

    private Dataset<Row> alterColumnNames(Dataset<Row> dataSet) {
        return dataSet.withColumnRenamed("Open Price", "openPrice")
                      .withColumnRenamed("High Price", "highPrice")
                      .withColumnRenamed("Low Price", "lowPrice")
                      .withColumnRenamed("Close Price", "closePrice")
                      .withColumnRenamed("Spread High-Low", "spreadHighLow")
                      .withColumnRenamed("Spread Close-Open", "spreadCloseOpen")
                      .withColumnRenamed("No.of Shares", "totalShares")
                      .withColumnRenamed("No. of Trades", "totalTrades")
                      .withColumnRenamed("Date", "pricingDate");
    }

    private Dataset<Row> addCustomColumns(Dataset<Row> dataSet) {
        return  dataSet.withColumn("spreadHighLow", functions.callUDF("spread", dataSet.col("highPrice"), dataSet.col("lowPrice")))
                       .withColumn("spreadOpenClose", functions.callUDF("spread", dataSet.col("openPrice"), dataSet.col("closePrice")));

    }
}
