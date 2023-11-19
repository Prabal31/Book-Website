package com.example.a2_prabh.DataBase;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.Bean.Cart;
import com.example.a2_prabh.Bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DataBaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<Book> getbook() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books";
        System.out.println("Executing query: " + query);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
    }

    public List<Book> getbook(long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books where id=:id";
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

        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM cart";
        System.out.println("Executing query: " + query);

        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Cart>(Cart.class));
    }


    public void deleteCart(String title) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "DELETE FROM cart where title = :title";
        namedParameters.addValue("title", title);
        jdbc.update(query, namedParameters);
    }
    public void deleteCart() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "DELETE FROM cart ";
        jdbc.update(query, namedParameters);
    }

    // Method to find a user account by email
    public User findUserAccount(String email) {
        MapSqlParameterSource namedParameters = new
                MapSqlParameterSource();
        String query = "SELECT * FROM sec_user where email = :email";
        namedParameters.addValue("email", email);
        try {
            return jdbc.queryForObject(query, namedParameters, new
                    BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }


    public void deleteBookById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM books WHERE id = :id";
        namedParameters.addValue("id", id);
        if (jdbc.update(query, namedParameters) > 0) {
            System.out.println("Deleted book " + id + " from the database.");
        }
    }

    // Method to get User Roles for a specific User id
    public List<String> getRolesById(Long userId) {
        MapSqlParameterSource namedParameters = new
                MapSqlParameterSource();
        String query = "SELECT sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId = sec_role.roleId "
                + "AND userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters,
                String.class);
    }
    public void addUser(String email, String password) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO sec_user "
                + "(email, encryptedPassword, enabled) "
                + "VALUES (:email, :encryptedPassword, 1)";
        namedParameters.addValue("email", email);
        namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
        jdbc.update(query, namedParameters);
    }

    public void addRole(Long userId, Long roleId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role (userId, roleId) "
                + "VALUES (:userId, :roleId)";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("roleId", roleId);
        jdbc.update(query, namedParameters);
    }

}
