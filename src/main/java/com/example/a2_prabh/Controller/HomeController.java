package com.example.a2_prabh.Controller;

import com.example.a2_prabh.Bean.Book;
import com.example.a2_prabh.Bean.User;
import com.example.a2_prabh.Bean.Cart;
import com.example.a2_prabh.DataBase.DataBaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Controller
public class HomeController {

    @Autowired
    DataBaseAccess da;

    // Variables to track total price, new total, and book count
    double totalPrice = 0.0;
    double newtotal = 0.0;
    int count = 0;

    // Lists to store books, user book list, users, and cart items
    List<Book> bookList = new CopyOnWriteArrayList<Book>();
    List<Integer> userbookList = new CopyOnWriteArrayList<>();
    List<User> userList = new CopyOnWriteArrayList<User>();
    List<Cart> cartList = new CopyOnWriteArrayList<Cart>();

    // Variables to store user details and selected book ID
    String UserId;
    String username;
    int bookid = 0;

    // Helper method to get the current username from the authentication context
    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    // Mapping for login page
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Mapping for details page
    @GetMapping("/details")
    public String details(Model model) {
        return "details";
    }

    // Mapping for payment page
    @GetMapping("/User/payment")
    public String payments(Model model) {
        return "/User/payment";
    }

    // Mapping for order placed page
    @GetMapping("/User/orderplaced")
    public String orderplaced(Model model) {
        da.insertBooksForUser(UserId, userbookList);
        da.deleteCart();
        return "/User/orderplaced";
    }

    // Mapping for user profile page
    @GetMapping("/User/yourprofile")
    public String yourprofile(Model model) {
        String username = getCurrentUsername();
        System.out.println(username);

        model.addAttribute("userList", da.getuser(username));

        List<Integer> bookIds = da.getUserbookId(username);
        System.out.println(bookIds);
        List<Book> userBookList = da.getBooksByIdList(bookIds);
        System.out.println(userBookList);

        model.addAttribute("userBookList", userBookList);

        return "/User/yourprofile";
    }

    // Mapping to display book details for a user
    @PostMapping("/User/details/{id}")
    public String Userdetails(Model model, @PathVariable int id) {
        model.addAttribute("bookList", da.getBookByID(id));
        return "/User/details";
    }

    // Mapping for the home page
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("bookList", da.getbook());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_USER"))) {
            return "redirect:/User/books";
        } else if (authentication.getAuthorities().stream().anyMatch(e -> e.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/secure/books";
        }
        return "index";
    }

    // Mapping for adding a book (accessible to ROLE_ADMIN)
    @GetMapping("/secure/addbook")
    public String addBook() {
        return "/secure/addbook";
    }

    // Mapping for the report page
    @GetMapping("/secure/report")
    public String report(Model model) {
        newtotal = newtotal + totalPrice;
        model.addAttribute("totalPrice", newtotal);
        model.addAttribute("totalcount", count);
        return "/secure/report";
    }

    // Mapping for user registration
    @GetMapping("/register")
    public String register(Model model) {
        return "register";
    }

    // Mapping for adding a book (accessible to ROLE_ADMIN)
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

    // Mapping for displaying the book list (accessible to ROLE_ADMIN)
    @GetMapping("/secure/books")
    public String secureIndex(Model model) {
        model.addAttribute("bookList", da.getbook());
        return "secure/books";
    }

    // Mapping for displaying the book list for users
    @GetMapping("/User/books")
    public String Books(Model model) {
        model.addAttribute("bookList", da.getbook());
        return "User/books";
    }

    // Mapping for adding a book to the cart
    @PostMapping("/User/addToCart/{id}")
    public String addToCart(@PathVariable int id, RedirectAttributes redirectAttributes) {
        System.out.println("Adding book to cart. Book ID: " + id);
        String username = getCurrentUsername();
        UserId = da.getUserID(username);
        System.out.println(UserId);
        List<Integer> bookIds = da.getUserbookId(username);

        if (bookIds.contains(id)) {
            redirectAttributes.addFlashAttribute("notification", "Book is already Bought.");
            return "redirect:/User/books";
        }


        // Check if the book is already in the user's cart
        if (da.isBookInUserCart(Integer.parseInt(UserId), id)) {
            redirectAttributes.addFlashAttribute("notification", "Book is already in the cart.");
            return "redirect:/User/books";
        }

        // If not in cart and not bought, proceed to add to the cart
        Book book = da.getBookByID(id);
        System.out.println("Retrieved book: " + book);
        userbookList.add(id);
        da.insertBookInCart(book);
        count++;
        bookid = id;

        System.out.println("Book added to cart.");
        return "/User/itemadded";
    }

    // Mapping for displaying the user's cart
    @GetMapping("User/cart")
    public String showCart(Model model) {
        List<Cart> cartList = da.getCartList();
        double totalPrice = calculateTotalPrice(cartList);

        model.addAttribute("cartList", da.getCartList());
        model.addAttribute("totalPrice", totalPrice);

        return "User/cart";
    }

    // Mapping for deleting an item from the user's cart (continued)
    @PostMapping("User/deleteCart/{title}")
    public String deleteCartByTitle(Model model, @PathVariable String title) {

        // Delete the item from the cart and update counts
        da.deleteCart(title);
        count--;

        // Retrieve updated cart items and recalculate total price
        List<Cart> cartList = da.getCartList();
        double totalPrice = calculateTotalPrice(cartList);

        // Update the model with the latest cart information
        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);

        // Redirect to the user's cart page
        return "/User/cart";
    }

    // Mapping for displaying the checkout page
    @GetMapping("User/Checkout")
    public String showCheckoutPage() {
        // Delete the entire cart when proceeding to checkout
        da.deleteCart();
        return "User/Checkout";
    }

    // Helper method to calculate the total price of items in the cart
    private double calculateTotalPrice(List<Cart> cartList) {
        for (Cart cart : cartList) {
            totalPrice += cart.getPrice();
        }
        return totalPrice;
    }

    // Mapping for processing user registration
    @PostMapping("/register")
    public String postRegister(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String username, @RequestParam String password) {
        // Add user to the database
        da.addUser(first_name, last_name, username, password);

        // Retrieve the user's ID and assign the ROLE_USER role
        Long userId = da.findUserAccount(username).getUserId();
        da.addRole(userId, Long.valueOf(1));

        // Redirect to a page indicating successful user registration
        return "useradded";
    }
}
