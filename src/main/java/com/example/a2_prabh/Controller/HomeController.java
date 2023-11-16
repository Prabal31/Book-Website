package com.example.a2_prabh.Controller;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class HomeController {

    @Autowired
    DataBaseAccess da;
    List<Book> bookList = new CopyOnWriteArrayList<Book>();

    @GetMapping("/")
    public String Books(Model model) {
        List<Book> bookList =da.getbook();
        model.addAttribute("bookList", da.getbook());
        return "index";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        System.out.println("LOL");

        Book book = da.getBookByID(id);

        da.insertBookInCart(book);

        return "itemadded";
    }
}
