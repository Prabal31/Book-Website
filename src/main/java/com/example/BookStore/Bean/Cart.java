package com.example.BookStore.Bean;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor


public class Cart {

    private String title;
    private String author;
    private String ISBN;
    private double price;
    private String description;


}
