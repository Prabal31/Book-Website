package com.example.a2_prabh.Controller;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.Bean.Cart;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class HomeController {

    @Autowired
    DataBaseAccess da;
    List<Book> bookList = new CopyOnWriteArrayList<Book>();
    List<Cart> cartList = new CopyOnWriteArrayList<Cart>();


    @GetMapping("/")
    public String Books(Model model) {
        List<Book> bookList =da.getbook();
        model.addAttribute("bookList", da.getbook());
        return "index";
    }

    @PostMapping("/addToCart/{id}")
    public String addToCart(@PathVariable int id) {

        Book book = da.getBookByID(id);

        da.insertBookInCart(book);

        return "itemadded";
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
        // Logic to prepare data if needed
        return "Checkout"; // Assuming "checkout" is the name of your Thymeleaf template
    }



    private double calculateTotalPrice(List<Cart> cartList) {
        double totalPrice = 0.0;
        for (Cart cart : cartList) {
            totalPrice += cart.getPrice();
        }
        return totalPrice;
    }

}
