package com.org.sparklearning.booksProcessor.processor;

import com.org.Util;
import com.org.sparklearning.booksProcessor.models.Book;
import com.org.sparklearning.booksProcessor.processor.mappers.BookMapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

import static org.apache.spark.sql.functions.concat;
import static org.apache.spark.sql.functions.expr;
import static org.apache.spark.sql.functions.lit;

public class BooksProcessor {

    private static final String INPUT_PATH = "data/inputs/books/books.csv";
    private SparkSession session;

    public BooksProcessor(SparkSession session) {
        this.session = session;
    }

    public Dataset<Book> getBooksData() {
        Dataset<Row> data = session.read().format("csv").options(Util.getOptions()).load(INPUT_PATH);
        Dataset<Book> books = data.map(new BookMapper(), Encoders.bean(Book.class));
        return books;
    }
}