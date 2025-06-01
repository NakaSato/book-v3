package com.bookstore.app;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import com.bookstore.books.AudioBook;
import com.bookstore.books.Book;
import com.bookstore.books.EBook;
import com.bookstore.books.PhysicalBook;
import com.bookstore.customer.Customer;
import com.bookstore.order.Order;
import com.bookstore.order.OrderItem;
import com.bookstore.order.ShoppingCart;

public class BookstoreApp {
    private static List<Book> inventory = new ArrayList<>(); // รายการหนังสือในคลัง
    private static List<Customer> customers = new ArrayList<>(); // รายชื่อลูกค้า
    private static Customer currentCustomer = null; // ลูกค้าที่กำลังใช้งานระบบ
    private static ShoppingCart shoppingCart = new ShoppingCart(); // ตะกร้าสินค้าปัจจุบัน
    private static Scanner scanner = new Scanner(System.in); // สำหรับรับอินพุต

    public static void main(String[] args) {
        initializeInventory();
        initializeCustomers();

        while (true) {
            printMainMenu();
            int choice = getUserChoice();
            switch (choice) {
                case 1:
                    viewBooks();
                    break;
                case 2:
                    selectCustomer();
                    break;
                case 3:
                    if (currentCustomer == null) {
                        System.out.println("Please select a customer first (Option 2).");
                        break;
                    }
                    addBookToCart();
                    break;
                case 4:
                    shoppingCart.displayCart();
                    break;
                case 5:
                    if (currentCustomer == null) {
                        System.out.println("Please select a customer first (Option 2).");
                        break;
                    }
                    if (shoppingCart.getItems().isEmpty()) {
                        System.out.println("Shopping cart is empty. Add items before checkout.");
                        break;
                    }
                    checkout();
                    break;
                case 6:
                    viewRecommendedBooks();
                    break;
                case 0:
                    System.out.println("Exiting application. Thank you!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void initializeInventory() {
        // Physical Books
        inventory.add(new PhysicalBook(
                "978-0684832722",
                "The Sovereign Individual",
                "James Dale Davidson",
                new BigDecimal("59.99"),
                "320",
                "Hardcover",
                "1997"));

        inventory.add(new PhysicalBook(
                "978-1491954386",
                "Mastering Bitcoin",
                "Andreas M. Antonopoulos",
                new BigDecimal("47.50"),
                "300",
                "Paperback",
                "2017"));

        inventory.add(new PhysicalBook(
                "978-1492054856",
                "Mastering the Lightning Network",
                "Andreas M. Antonopoulos",
                new BigDecimal("52.75"),
                "250",
                "Paperback",
                "2018"));

        inventory.add(new PhysicalBook(
                "978-1544526474",
                "The Bitcoin Standard",
                "Saifedean Ammous",
                new BigDecimal("49.99"),
                "400",
                "Hardcover",
                "2018"));

        inventory.add(new PhysicalBook(
                "978-1544526478",
                "The Fiat Standard",
                "Saifedean Ammous",
                new BigDecimal("54.99"),
                "350",
                "Hardcover",
                "2021"));

        inventory.add(new PhysicalBook(
                "978-1337563079",
                "Principles of Economics (2022)",
                "N. Gregory Mankiw",
                new BigDecimal("89.95"),
                "500",
                "Hardcover",
                "2022"));

        // E-Books
        inventory.add(new EBook(
                "978-1098150097",
                "Bitcoin for Everyone",
                "Andreas M. Antonopoulos",
                new BigDecimal("32.99"),
                "2020"));

        inventory.add(new EBook(
                "978-1492054863",
                "Mastering the Lightning Network",
                "Andreas M. Antonopoulos",
                new BigDecimal("39.99"),
                "2018"));

        inventory.add(new EBook(
                "978-1544526481",
                "The Bitcoin Standard",
                "Saifedean Ammous",
                new BigDecimal("34.99"),
                "2018"));

        // Audio Books
        inventory.add(new AudioBook(
                "978-1098150110",
                "Bitcoin: The Future of Money",
                "Andreas M. Antonopoulos",
                new BigDecimal("29.95"),
                "2020"));

        inventory.add(new AudioBook(
                "978-1544526488",
                "The Fiat Standard",
                "Saifedean Ammous",
                new BigDecimal("39.95"),
                "2021"));

        inventory.add(new AudioBook(
                "978-1099876550",
                "The Bitcoin Enlightenment (2025, Co-Authored)",
                "Various Authors",
                new BigDecimal("37.95"),
                "2025"));
    }

    private static void initializeCustomers() {
        customers.add(new Customer("C001", "demo1", Customer.CustomerType.VIP));
        customers.add(new Customer("C002", "demo2", Customer.CustomerType.GENERAL));
    }

    private static void printMainMenu() {
        System.out.println("\n--- Online Bookstore Menu ---");
        if (currentCustomer != null) {
            System.out.println("Customer Account : " + currentCustomer.getUsername() + " ("
                    + currentCustomer.getCustomerType() + ")");
        } else {
            System.out.println("No customer selected.");
        }
        System.out.println("1. View All Books");
        System.out.println("2. Select Customer");
        System.out.println("3. Add Book to Cart");
        System.out.println("4. View Cart");
        System.out.println("5. Checkout");
        System.out.println("6. View Recommended Books (Highest Price per Type)");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getUserChoice() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next(); // เคลียร์อินพุตที่ไม่ใช่ตัวเลข
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // เคลียร์ newline ที่เหลือ
        return choice;
    }

    private static void viewBooks() {
        System.out.println("\n--- Available Books ---");
        if (inventory.isEmpty()) {
            System.out.println("No books available in inventory.");
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            System.out.println((i + 1) + ". " + inventory.get(i).displayDetails());
        }
        System.out.println("---------------------");
    }

    private static void selectCustomer() {
        if (customers.isEmpty()) {
            System.out.println("No customers available. The system has predefined customers.");
            return;
        }
        System.out.println("\n--- Select Customer ---");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println((i + 1) + ". " + customers.get(i).toString());
        }
        System.out.print("Enter customer number to select: ");
        int choice = getUserChoice();
        if (choice > 0 && choice <= customers.size()) {
            currentCustomer = customers.get(choice - 1);
            System.out.println("Customer selected: " + currentCustomer.getUsername());
            shoppingCart.clearCart(); // ล้างตะกร้าสำหรับลูกค้าที่เลือก
        } else {
            System.out.println("Invalid customer selection.");
        }
    }

    private static void addBookToCart() {
        viewBooks();
        if (inventory.isEmpty())
            return;

        System.out.print("Enter book number to add to cart: ");
        int bookChoice = getUserChoice();
        if (bookChoice <= 0 || bookChoice > inventory.size()) { // Fixed the syntax error
            System.out.println("Invalid book selection.");
            return;
        }
        Book selectedBook = inventory.get(bookChoice - 1);

        System.out.print("Enter quantity: ");
        int quantity = getUserChoice();
        if (quantity <= 0) {
            System.out.println("Quantity must be positive.");
            return;
        }

        shoppingCart.addItem(new OrderItem(selectedBook, quantity));
    }

    private static void checkout() {
        Order order = new Order(currentCustomer, new ArrayList<>(shoppingCart.getItems()));
        order.displayOrderSummary(); // แสดงสรุปคำสั่งซื้อพร้อมราคาสุดท้าย

        // การให้แต้มสะสม
        // ลูกค้าทั่วไป 1 แต้ม ต่อทุกๆ 10 หน่วยสกุลเงินที่ใช้จ่าย
        // ลูกค้า VIP 2 แต้ม ต่อทุกๆ 10 หน่วยสกุลเงินที่ใช้จ่าย
        BigDecimal amountSpent = order.getGrandTotal(); // ยอดที่จ่ายจริง
        int pointsEarnedBase = amountSpent.divide(new BigDecimal("10"), 0, RoundingMode.FLOOR).intValue();
        int pointsMultiplier = currentCustomer.isVIP() ? 2 : 1;
        int totalPointsEarned = pointsEarnedBase * pointsMultiplier;

        if (totalPointsEarned > 0) {
            currentCustomer.addLoyaltyPoints(totalPointsEarned);
        }

        System.out.println("Thank you for your order, " + currentCustomer.getUsername() + "!");
        shoppingCart.clearCart();
    }

    private static void viewRecommendedBooks() {
        System.out.println("\n--- Recommended Books (Highest Price per Type) ---");
        if (inventory.isEmpty()) {
            System.out.println("No books available to recommend.");
            return;
        }

        Map<String, Book> highestPriceBooks = new HashMap<>();

        for (Book book : inventory) {
            String type = book.getBookType();
            BigDecimal currentBookPrice = book.calculatePrice();

            if (!highestPriceBooks.containsKey(type) ||
                    currentBookPrice.compareTo(highestPriceBooks.get(type).calculatePrice()) > 0) {
                highestPriceBooks.put(type, book);
            }
        }

        if (highestPriceBooks.isEmpty()) {
            System.out.println("Could not determine recommendations.");
        } else {
            highestPriceBooks
                    .forEach((type, book) -> System.out.println("Highest priced " + type + ": " + book.getTitle() +
                            " at $" + book.calculatePrice().setScale(2, RoundingMode.HALF_EVEN)));
        }
        System.out.println("--------------------------------------------------");
    }
}
