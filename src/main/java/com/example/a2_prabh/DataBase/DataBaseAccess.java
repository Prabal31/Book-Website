package com.example.a2_prabh.DataBase;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.Bean.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataBaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public List<Book> getbook() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books";
        System.out.println("Executing query: " + query);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
    }

    public Book getBookByID(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books WHERE id = :id";
        namedParameters.addValue("id", id);
        List<Book> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Book.class));
        return result.isEmpty() ? null : result.get(0);
    }

    public void insertBookInCart(Book book) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO cart(title,author,isbn,price,description) VALUES (:title,:author,:isbn,:price,:description)";

        namedParameters.addValue("title", book.getTitle());
        namedParameters.addValue("author", book.getAuthor());
        namedParameters.addValue("isbn", book.getISBN());
        namedParameters.addValue("price", book.getPrice());
        namedParameters.addValue("description", book.getDescription());

        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0) {
            System.out.println("book inserted into database cart");
        }
    }

    public List<Cart> getCartList() {
        System.out.println("LOL");

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM cart";
        System.out.println("Executing query: " + query);

        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Cart>(Cart.class));
    }
    public Cart getCartByID(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM cart WHERE id = :id";
        namedParameters.addValue("id", id);
        List<Cart> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Cart.class));
        return result.isEmpty() ? null : result.get(0);
    }

    public void deleteCart(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM cart where id = :id";
        namedParameters.addValue("id", id);
        jdbc.update(query, namedParameters);
    }
}
