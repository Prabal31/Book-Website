package com.example.prabh.Controller;

import com.example.prabh.Bean.Book;
import com.example.prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@RequestMapping("/api/books")
public class BookController {

    // A list to hold books (consider using a service or database instead of an in-memory list in production)
    java.util.List<Book> bookList = new CopyOnWriteArrayList<>();

    @Autowired
    private DataBaseAccess da;

    // Endpoint to add a book (accessible to ROLE_ADMIN)
    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        da.insertBook(book);
        return "Book added successfully";
    }

    // Endpoint to delete a book by ID (accessible to ROLE_ADMIN)
    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        da.deleteBookById(id);
        return "Book deleted successfully";
    }

    // Endpoint to get a book by ID
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable Long id) {
        return da.getBookByID(id.intValue());
    }

    // Endpoint to get all books
    @GetMapping
    public List<Book> getAllBooks() {
        return da.getbook();
    }
}
