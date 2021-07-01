package com.org;

import com.org.sparklearning.booksProcessor.models.Book;
import com.org.sparklearning.booksProcessor.processor.BooksProcessor;
import com.org.sparklearning.restaurantsProcessor.RestaurantsProcessor;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class Application {

    public static void main(String[] args) {
        //processBooks();
        processRestaurants();
    }

    private static void processBooks() {
        BooksProcessor processor = new BooksProcessor(getSparkSession());
        Dataset<Book> books = processor.getBooksData();
        Dataset<Row> booksDf = processor.getBooksDataWithNewColumns(books);
        booksDf.show();
        booksDf.printSchema();
    }

    private static void processRestaurants() {
        RestaurantsProcessor processor = new RestaurantsProcessor(getSparkSession());
        Dataset<Row> data = processor.processRestaurantsData();
        data.show(5);
        data.printSchema();
    }

    private static SparkSession getSparkSession() {
        SparkConf conf = new SparkConf().setAppName("Spark In Action").setMaster("local[*]");
        conf.set("spark.sql.legacy.timeParserPolicy", "LEGACY");
        SparkContext context = new SparkContext(conf);
        SparkSession session = SparkSession.builder().sparkContext(context).getOrCreate();
        return session;
    }
}
