package com.example.a2_prabh.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Book class represents a book entity with various attributes.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    /**
     * The unique identifier for the book.
     */
    private int id;

    /**
     * The title of the book.
     */
    private String title;

    /**
     * The author of the book.
     */
    private String author;

    /**
     * The International Standard Book Number (ISBN) of the book.
     */
    private String ISBN;

    /**
     * The price of the book.
     */
    private double price;

    /**
     * A brief description of the book.
     */
    private String description;
}
