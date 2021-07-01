package com.org.sparklearning.booksProcessor.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Book {
    private Integer id;
    private Integer authorId;
    private String title;
    private Date releaseDate;
    private String link;
}
