package com.example.a2_prabh.Controller;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private DataBaseAccess da;

    @Secured("ROLE_ADMIN")
    @PostMapping("/add")
    public String addBook(@RequestBody Book book) {
        da.insertBook(book);
        return "Book added successfully";
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        da.deleteBookById(id);
        return "Book deleted successfully";
    }
}
