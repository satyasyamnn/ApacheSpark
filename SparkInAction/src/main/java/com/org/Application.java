package com.org;

import com.org.sparklearning.booksProcessor.models.Book;
import com.org.sparklearning.booksProcessor.processor.BooksProcessor;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.*;
import static org.apache.spark.sql.functions.expr;

public class Application {

    public static void main(String[] args) {
        BooksProcessor processor = new BooksProcessor(getSparkSession());
        Dataset<Book> books =  processor.getBooksData();
        Dataset<Row> booksDf = books.toDF();
        booksDf = booksDf.withColumn("releaseDataAsString", concat(
                            expr("releaseDate.year + 1950"), lit("-"),
                            expr("releaseDate.month + 1"), lit("-"),
                            expr("releaseDate.date")));

        booksDf = booksDf.withColumn("releaseDateAsDate",
                to_date(booksDf.col("releaseDataAsString"), "yyyy-MM-dd"))
                .drop("releaseDataAsString");
        booksDf.show();
        booksDf.printSchema();
    }

    private static SparkSession getSparkSession() {
        SparkConf conf = new SparkConf().setAppName("Spark In Action").setMaster("local[*]");
        conf.set("spark.sql.legacy.timeParserPolicy", "LEGACY");
        SparkContext context = new SparkContext(conf);
        SparkSession session = SparkSession.builder().sparkContext(context).getOrCreate();
        return session;
    }
}
