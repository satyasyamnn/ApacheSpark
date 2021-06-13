package com.stock.processor.stockProcessor;

import com.stock.processor.stockProcessor.configuration.ApplicationConfiguration;
import com.stock.processor.stockProcessor.readers.StockDataReader;
import com.stock.processor.stockProcessor.readers.StockDataReaderImpl;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.api.java.UDF1;
import org.apache.spark.sql.api.java.UDF2;
import org.apache.spark.sql.types.DataTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;

@ComponentScan({"com.stock.processor.*"})
@SpringBootApplication
public class StockProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockProcessorApplication.class, args);
	}

	@Bean
	public StockDataReader stockDataReader() {
		return new StockDataReaderImpl(config, sqlContext());
	}

	@Autowired
	public ApplicationConfiguration config;

	@Bean
	public SparkContext sparkContext() {
		SparkConf conf = new SparkConf().setAppName(config.getApplicationName()).setMaster(config.getMaster());
		return new SparkContext(conf);
	}

	@Bean
	public SparkSession sparkSession() {
		SparkContext context = sparkContext();
		return  SparkSession.builder().sparkContext(context).getOrCreate();
	}

	@Bean
	public SQLContext sqlContext() {
		SQLContext sqlContext = new SQLContext(sparkSession());
		sqlContext.udf().register("spread", (UDF2<Double, Double, Double>) (value1, value2) -> value1 - value2 , DataTypes.DoubleType);
		sqlContext.udf().register("filename", (UDF1<String, String>) (input) -> {
		    File file = new File(input);
			String fileName =  file.getName();
			return fileName.substring(0, fileName.lastIndexOf("."));
		}, DataTypes.StringType);
		return sqlContext;
	}
}
