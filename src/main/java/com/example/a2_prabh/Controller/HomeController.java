package com.example.a2_prabh.Controller;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.Bean.Cart;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class HomeController {

    @Autowired
    DataBaseAccess da;
    List<Book> bookList = new CopyOnWriteArrayList<Book>();
    List<Cart> cartList = new CopyOnWriteArrayList<Cart>();

    @GetMapping("/login")
    public String login(Model model) {

        return "login";
    }
    @GetMapping("/details")
    public String details(Model model) {

        return "details";
    }
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("bookList", da.getbook());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/books";
        } else if(authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/secure/books";
        }
        return "index";
    }

    @GetMapping("/secure/addbook")
    public String addBook() {

        return "/secure/addbook";
    }

    @GetMapping("/register")
    public String register(Model model) {

        return "register";
    }


    @PostMapping("/secure/addbook")
    public String addBook(Model model, @ModelAttribute Book book) {
        System.out.println("done");

        bookList.add(book);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookList);

        da.insertBook(book);
        model.addAttribute("bookList", da.getbook());

        return "/secure/bookadded";
    }


    @GetMapping("/secure/books")
    public String secureIndex(Model model) {
        model.addAttribute("bookList", da.getbook());
        return "secure/books";
    }

    @GetMapping("/books")
    public String Books(Model model) {
        model.addAttribute("bookList", da.getbook());
        return "books";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {
        System.out.println("Adding book to cart. Book ID: " + id);

        Book book = da.getBookByID(id);
        System.out.println("Retrieved book: " + book);

        da.insertBookInCart(book);

        System.out.println("Book added to cart.");
        return "itemadded";
    }

    @PostMapping("/secure/deleteBookById/{id}")
    public String deleteStudentById(Model model, @PathVariable Long id) {
        Book book = da.getbook(id).get(0);
        da.deleteBookById(id);
        System.out.println("DOne");
        model.addAttribute("bookList", da.getbook());
        return "secure/bookdeleted";
    }

    @PostMapping("/secure/editBook/{id}")
    public String showEditForm(@PathVariable long id, Model model) {
        Book book = da.getbook(id).get(0);
        model.addAttribute("book", book);
        return "secure/editBook";
    }
    @PostMapping("/secure/edit/{title}")
    public String editBook(Model model, @PathVariable String title, @ModelAttribute Book updatedBook) {
        System.out.println("DOne");

        da.updateBookByTitle(title, updatedBook);

        Book book = da.getBookByTitle(title);

        model.addAttribute("bookList", da.getbook());
        model.addAttribute("book", book);

        return "secure/books"; // Redirect to the book list page after editing
    }


    @GetMapping("/cart")
    public String showCart(Model model) {

        List<Cart> cartList = da.getCartList();
        double totalPrice = calculateTotalPrice(cartList);

        model.addAttribute("cartList", da.getCartList());
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    @PostMapping("/deleteCart/{title}")
    public String deleteCartByTitle(Model model, @PathVariable String title) {

        da.deleteCart(title);
        List<Cart> cartList = da.getCartList(); // Retrieve updated cart items
        double totalPrice = calculateTotalPrice(cartList); // Recalculate total price

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "cart";
    }

    @GetMapping("/Checkout")
    public String showCheckoutPage() {
        da.deleteCart();
        return "Checkout";
    }



    private double calculateTotalPrice(List<Cart> cartList) {
        double totalPrice = 0.0;
        for (Cart cart : cartList) {
            totalPrice += cart.getPrice();
        }
        return totalPrice;
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam String username, @RequestParam String password) {
        da.addUser(username, password);
        Long userId = da.findUserAccount(username).getUserId();
        da.addRole(userId, Long.valueOf(1));
        return "useradded";
    }

}
