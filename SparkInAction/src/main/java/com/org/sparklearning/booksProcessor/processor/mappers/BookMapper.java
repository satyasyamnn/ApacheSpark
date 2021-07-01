package com.org.sparklearning.booksProcessor.processor.mappers;

import com.org.sparklearning.booksProcessor.models.Book;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.Row;

import java.text.SimpleDateFormat;

public class BookMapper implements MapFunction<Row, Book> {

    @Override
    public Book call(Row row) throws Exception {
        Book book = new Book();
        book.setId(row.getAs("id"));
        book.setAuthorId(row.getAs("authorId"));
        book.setLink(row.getAs("link"));
        book.setTitle(row.getAs("title"));

        String dateAsString = row.getAs("releaseDate");
        if (dateAsString != null) {
            SimpleDateFormat parser = new SimpleDateFormat("M/d/yy");
            book.setReleaseDate(parser.parse(dateAsString));
        }

        return book;
    }
}
