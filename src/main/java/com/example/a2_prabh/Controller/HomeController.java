package com.example.a2_prabh.Controller;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class HomeController {

    @Autowired
    DataBaseAccess da;
    List<Book> bookList = new CopyOnWriteArrayList<Book>();
    List<Book> cartList = new CopyOnWriteArrayList<Book>();



    @GetMapping("/")
    public String Books(Model model) {
        List<Book> bookList =da.getbook();
        model.addAttribute("bookList", da.getbook());
        System.out.println("LOL");
        return "index";
    }

}
