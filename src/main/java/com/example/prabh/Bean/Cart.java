package com.example.prabh.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The Cart class represents an item that can be added to a shopping cart.
 * It contains information about a book, such as title, author, ISBN, price, and description.
 */
@Data
@NoArgsConstructor
public class Cart {

    /**
     * The title of the book in the cart.
     */
    private String title;

    /**
     * The author of the book in the cart.
     */
    private String author;

    /**
     * The International Standard Book Number (ISBN) of the book in the cart.
     */
    private String ISBN;

    /**
     * The price of the book in the cart.
     */
    private double price;

    /**
     * A brief description of the book in the cart.
     */
    private String description;
}
