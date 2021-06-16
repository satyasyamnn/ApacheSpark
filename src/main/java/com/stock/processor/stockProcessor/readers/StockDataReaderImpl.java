package com.stock.processor.stockProcessor.readers;

import com.stock.processor.stockProcessor.configuration.ApplicationConfiguration;
import com.stock.processor.stockProcessor.models.Stock;
import org.apache.spark.sql.*;
import scala.collection.JavaConverters;
import scala.collection.Seq;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.spark.sql.functions.input_file_name;

public class StockDataReaderImpl implements StockDataReader {

    private final ApplicationConfiguration config;
    private final SQLContext sqlContext;

    public StockDataReaderImpl(ApplicationConfiguration config, SQLContext sqlContext) {
        this.config = config;
        this.sqlContext = sqlContext;
    }

    @Override
    public Dataset<Stock> readRawStockData() {
        Dataset<Row> data = readData();
        return encodeToStockDataColumns(data);
    }

    private Dataset<Row> readData() {
        String filesToRead = config.getInputPath() + "/*." + config.getInputFileFormat();
        Dataset<Row> data = sqlContext.read().format(config.getInputFileFormat()).options(getOptions()).load(filesToRead);
        data = data.withColumn("stockName", functions.callUDF("filename", input_file_name()));
        return data;
    }

    private Dataset<Stock> encodeToStockDataColumns(Dataset<Row> dataSet) {
        Dataset<Row> newDataSet = dropColumns(dataSet);
        newDataSet = alterColumnNames(newDataSet);
        newDataSet = addCustomColumns(newDataSet);
        Encoder<Stock> encoder = Encoders.bean(Stock.class);
        return newDataSet.as(encoder);
    }


    private Map<String, String> getOptions() {
        Map<String, String> options = new HashMap<>();
        options.put("header", "true");
        options.put("inferSchema", "true");
        return options;
    }

    private Dataset<Row> dropColumns(Dataset<Row> dataSet) {
        List<String> excludeFields = Arrays.asList("% Deli. Qty to Traded Qty", "Deliverable Quantity", "Spread Close-Open", "Spread High-Low", "Total Turnover (Rs.)", "WAP");
        Seq<String> cols = JavaConverters.collectionAsScalaIterableConverter(excludeFields).asScala().toSeq();
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
        return dataSet.withColumn("spreadHighLow", functions.callUDF("spread", dataSet.col("highPrice"), dataSet.col("lowPrice")))
                .withColumn("spreadOpenClose", functions.callUDF("spread", dataSet.col("openPrice"), dataSet.col("closePrice")));

    }
}
