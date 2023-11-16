package com.example.a2_prabh.DataBase;

import com.example.a2_prabh.Bean.Book;
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
}
