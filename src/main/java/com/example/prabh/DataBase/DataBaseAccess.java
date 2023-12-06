package com.example.prabh.DataBase;

import com.example.prabh.Bean.Book;
import com.example.prabh.Bean.Cart;
import com.example.prabh.Bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

@Repository
public class DataBaseAccess {

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private User user;

    // BCryptPasswordEncoder for password encoding
    public BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Retrieve all books from the database
    public List<Book> getbook() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books";
        System.out.println("Executing query: " + query);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Book>(Book.class));
    }

    // Retrieve user by email
    public User getuser(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user WHERE email = :email";
        System.out.println("Executing query: " + query);
        namedParameters.addValue("email", email);
        List<User> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
        return result.isEmpty() ? null : result.get(0);
    }

    // Get the user ID by email
    public String getUserID(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT userId FROM sec_user WHERE email = :email";
        System.out.println("Executing query: " + query);
        namedParameters.addValue("email", email);
        try {
            return jdbc.queryForObject(query, namedParameters, String.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Retrieve a book by its ID
    public Book getBookByID(int id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books WHERE id = :id";
        namedParameters.addValue("id", id);
        List<Book> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Book.class));
        return result.isEmpty() ? null : result.get(0);
    }

    // Retrieve a list of books by a list of book IDs
    public List<Book> getBooksByIdList(List<Integer> bookIds) {
        if (bookIds.isEmpty()) {
            return Collections.emptyList();
        }

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("bookIds", bookIds);

        String query = "SELECT * FROM books WHERE id IN (:bookIds)";
        System.out.println("Executing query: " + query);

        return jdbc.query(query, parameters, new BeanPropertyRowMapper<>(Book.class));
    }

    // Insert a book into the cart
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
            System.out.println("Book inserted into the database cart");
        }
    }

    // Retrieve a book by its title
    public Book getBookByTitle(String title) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM books WHERE title = :title";
        namedParameters.addValue("title", title);
        List<Book> result = jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Book.class));
        return result.isEmpty() ? null : result.get(0);
    }

    // Update a book by its title
    public void updateBookByTitle(String title, Book books) {
        String query = "UPDATE books SET title = :title, author = :author, " +
                "isbn = :isbn, price = :price, description = :description " +
                "WHERE title = :title";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("title", books.getTitle());
        parameters.addValue("author", books.getAuthor());
        parameters.addValue("isbn", books.getISBN());
        parameters.addValue("price", books.getPrice());
        parameters.addValue("description", books.getDescription());

        int rowsAffected = jdbc.update(query, parameters);
        if (rowsAffected > 0) {
            System.out.println("Book updated in the database");
        }
    }

    // Insert a new book into the database
    public void insertBook(Book book) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("title", book.getTitle());
        namedParameters.addValue("author", book.getAuthor());
        namedParameters.addValue("isbn", book.getISBN());
        namedParameters.addValue("price", book.getPrice());
        namedParameters.addValue("description", book.getDescription());
        String query = "INSERT INTO books(title,author,ISBN,price,description) VALUES (:title,:author,COALESCE(:isbn, 'N/A'),:price,:description)";

        int rowsAffected = jdbc.update(query, namedParameters);
        if (rowsAffected > 0) {
            System.out.println("Book inserted into the database");
        }
    }

    // Retrieve the list of items in the user's cart
    public List<Cart> getCartList() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM cart";
        System.out.println("Executing query: " + query);
        return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<Cart>(Cart.class));
    }

    // Check if a book is already in the user's cart
    public boolean isBookInUserCart(int userId, int bookId) {
        String query = "SELECT COUNT(*) FROM user_book WHERE userId = :userId AND bookId = :bookId";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("userId", userId);
        parameters.addValue("bookId", bookId);

        int count = jdbc.queryForObject(query, parameters, Integer.class);

        return count > 0;
    }

    // Delete a specific item from the cart by its title
    public void deleteCart(String title) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "DELETE FROM cart WHERE title = :title";
        namedParameters.addValue("title", title);
        jdbc.update(query, namedParameters);
    }

    // Delete all items from the cart
    public void deleteCart() {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        String query = "DELETE FROM cart";
        jdbc.update(query, namedParameters);
    }

    // Method to find a user account by email
    public User findUserAccount(String email) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT * FROM sec_user WHERE email = :email";
        namedParameters.addValue("email", email);
        try {
            return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    // Insert selected books into the user's collection
    public void insertBooksForUser(String userId, List<Integer> bookIds) {
        String query = "INSERT INTO user_book(userId, bookId, enabled) VALUES (:userId, :bookId, 1)";

        for (Integer bookId : bookIds) {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("userId", userId);
            namedParameters.addValue("bookId", bookId);

            int rowsAffected = jdbc.update(query, namedParameters);

            if (rowsAffected > 0) {
                System.out.println("Book with ID " + bookId + " inserted into the user's collection.");
            } else {
                System.out.println("Failed to insert book with ID " + bookId + " into the user's collection.");
            }
        }
    }

    // Retrieve book IDs owned by a specific user
    public List<Integer> getUserbookId(String username) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT bookId FROM user_book WHERE userId = (SELECT userId FROM sec_user WHERE email = :username)";

        namedParameters.addValue("username", username);
        return jdbc.queryForList(query, namedParameters, Integer.class);
    }

    // Delete a book from the database by its ID
    public void deleteBookById(Long id) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "DELETE FROM books WHERE id = :id";
        System.out.println("Executing query: " + query);
        namedParameters.addValue("id", id);
        if (jdbc.update(query, namedParameters) > 0) {
            System.out.println("Deleted book " + id + " from the database.");
        }
    }

    // Retrieve roles for a specific user ID
    public List<String> getRolesById(Long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId = sec_role.roleId "
                + "AND userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters, String.class);
    }

    // Add a new user to the database
    public void addUser(String first_name, String last_name, String email, String password) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO sec_user "
                + "(first_name, last_name, email, encryptedPassword, enabled) "
                + "VALUES (:first_name, :last_name, :email, :encryptedPassword, 1)";
        namedParameters.addValue("first_name", first_name);
        namedParameters.addValue("last_name", last_name);
        namedParameters.addValue("email", email);
        namedParameters.addValue("encryptedPassword", passwordEncoder.encode(password));
        jdbc.update(query, namedParameters);
    }

    // Add a role to a specific user
    public void addRole(Long userId, Long roleId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "INSERT INTO user_role (userId, roleId) "
                + "VALUES (:userId, :roleId)";
        namedParameters.addValue("userId", userId);
        namedParameters.addValue("roleId", roleId);
        jdbc.update(query, namedParameters);
    }

}
