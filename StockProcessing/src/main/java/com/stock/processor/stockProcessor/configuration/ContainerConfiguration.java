package com.stock.processor.stockProcessor.configuration;

import com.stock.processor.stockProcessor.readers.StockDataReader;
import com.stock.processor.stockProcessor.readers.StockDataReaderImpl;
import com.stock.processor.stockProcessor.runners.StockProcessor;
import com.stock.processor.stockProcessor.writers.StockDataWriter;
import com.stock.processor.stockProcessor.writers.StockDataWriterImpl;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ContainerConfiguration {

    @Autowired
    public DatabaseConfiguration dbConfig;

    @Bean
    public StockProcessor stockProcessor(StockDataReader stockDataReader, StockDataWriter stockDataWriter) {
        return new StockProcessor(stockDataReader, stockDataWriter);
    }

    @Bean
    public StockDataReader stockDataReader(SQLContext sqlContext, ApplicationConfiguration config) {
        return new StockDataReaderImpl(config, sqlContext);
    }

    @Bean
    public StockDataWriter stockDataWriter(ApplicationConfiguration config) {
        return new StockDataWriterImpl(config, dbConfig);
    }

    @Bean
    public SparkContext sparkContext(ApplicationConfiguration config) {
        SparkConf conf = new SparkConf().setAppName(config.getApplicationName()).setMaster(config.getMaster());
        return new SparkContext(conf);
    }

    @Bean
    public SparkSession sparkSession(SparkContext sparkContext) {
        return SparkSession.builder().sparkContext(sparkContext).getOrCreate();
    }

    @Bean
    public SQLContext sqlContext(SparkSession sparkSession) {
        SQLContext sqlContext = new SQLContext(sparkSession);
        sqlContext.udf().register("spread", (UDF2<Double, Double, Double>) (value1, value2) -> value1 - value2, DataTypes.DoubleType);
        sqlContext.udf().register("filename", (UDF1<String, String>) (input) -> {
            File file = new File(input);
            String fileName = file.getName();
            return fileName.substring(0, fileName.lastIndexOf("."));
        }, DataTypes.StringType);
        return sqlContext;
    }
}
