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
}
