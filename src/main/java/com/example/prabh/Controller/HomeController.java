package com.example.prabh.Controller;

import com.example.prabh.Bean.Book;
import com.example.prabh.Bean.User;
import com.example.prabh.Bean.Cart;
import com.example.prabh.DataBase.DataBaseAccess;
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

    double totalPrice=0.0;
    double newtotal=0.0;
    int count=0;
    int bookid = 0;
    String UserId;
    String username;

    List<Book> bookList = new CopyOnWriteArrayList<Book>();
    List<Integer> userbookList = new CopyOnWriteArrayList<>();
    List<User> userList = new CopyOnWriteArrayList<User>();
    List<Cart> cartList = new CopyOnWriteArrayList<Cart>();

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/details")
    public String details(Model model) {

        return "details";
    }

    @GetMapping("/User/payment")
    public String payments(Model model) {

        return "/User/payment";
    }

    @GetMapping("/User/orderplaced")
    public String orderplaced(Model model) {
        da.insertBooksForUser(UserId, userbookList);
        userbookList.clear();
        da.deleteCart();
        return "/User/orderplaced";
    }

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

    @PostMapping("/User/details/{id}")
    public String Userdetails(Model model, @PathVariable int id) {

        model.addAttribute("bookList", da.getBookByID(id));

        return "/User/details";
    }

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

    @GetMapping("/secure/addbook")
    public String addBook() {

        return "/secure/addbook";
    }
    @GetMapping("/secure/report")
    public String report(Model model) {
        model.addAttribute("totalPrice", newtotal);
        model.addAttribute("totalcount", count);

        return "/secure/report";
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

    @GetMapping("/User/books")
    public String Books(Model model) {

        model.addAttribute("bookList", da.getbook());

        return "User/books";
    }
    @PostMapping("/User/addToCart/{id}")
    public String addToCart(@PathVariable int id, RedirectAttributes redirectAttributes) {
        System.out.println("Adding book to cart. Book ID: " + id);
        String username = getCurrentUsername();
        UserId = da.getUserID(username);
        List<Integer> bookIds = da.getUserbookId(username);

        if (bookIds.contains(id)) {
            redirectAttributes.addFlashAttribute("notification", "Book is already Bought.");
            return "redirect:/User/books";
        }
        if (userbookList.contains(id)) {
            redirectAttributes.addFlashAttribute("notification", "Book is already in the cart.");
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

    @PostMapping("/User/yourprofile/{id}")
    public String yourprofile(@PathVariable int id, int bookid) {


        System.out.println("Book added to cart.");
        return "/User/itemadded";
    }

    @PostMapping("/secure/deleteBookById/{id}")
    public String deleteStudentById(Model model, @PathVariable Long id) {
        Book book = da.getbook(id).get(0);
        userbookList.remove(id);
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


    @GetMapping("User/cart")
    public String showCart(Model model) {

        List<Cart> cartList = da.getCartList();
        double totalPrice = calculateTotalPrice(cartList);

        model.addAttribute("cartList", da.getCartList());
        model.addAttribute("totalPrice", totalPrice);

        return "User/cart";
    }

    @PostMapping("User/deleteCart/{title}")
    public String deleteCartByTitle(Model model, @PathVariable String title) {

        da.deleteCart(title);
        count--;
        List<Cart> cartList = da.getCartList(); // Retrieve updated cart items
        double totalPrice = calculateTotalPrice(cartList); // Recalculate total price

        model.addAttribute("cartList", cartList);
        model.addAttribute("totalPrice", totalPrice);

        return "/User/cart";
    }

    @GetMapping("User/Checkout")
    public String showCheckoutPage() {
        da.deleteCart();
        return "User/Checkout";
    }



    private double calculateTotalPrice(List<Cart> cartList) {
        totalPrice = 0.0;
        for (Cart cart : cartList) {
            totalPrice += cart.getPrice();


        }
        newtotal= newtotal+totalPrice;
        return totalPrice;
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam String first_name, @RequestParam String last_name, @RequestParam String username, @RequestParam String password) {
        da.addUser(first_name, last_name, username, password);
        Long userId = da.findUserAccount(username).getUserId();
        da.addRole(userId, Long.valueOf(1));
        return "useradded";
    }

}
