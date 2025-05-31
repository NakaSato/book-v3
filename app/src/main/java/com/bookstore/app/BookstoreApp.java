package com.bookstore.app;

import com.bookstore.books.*;
import com.bookstore.customer.Customer;
import com.bookstore.order.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class BookstoreApp {
    private static List<Book> inventory = new ArrayList<>(); // รายการหนังสือในคลัง
    private static List<Customer> customers = new ArrayList<>(); // รายชื่อลูกค้า
    private static Customer currentCustomer = null; // ลูกค้าที่กำลังใช้งานระบบ
    private static ShoppingCart shoppingCart = new ShoppingCart(); // ตะกร้าสินค้าปัจจุบัน
    private static Scanner scanner = new Scanner(System.in); // สำหรับรับอินพุต

    public static void main(String[] args) { // Fixed the method signature
        initializeInventory(); // เพิ่มหนังสือตัวอย่างเข้าคลัง
        initializeCustomers(); // เพิ่มลูกค้าตัวอย่าง (ถ้าต้องการ)

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
      // Physical books
      inventory.add(new PhysicalBook("978-0321765723", "Effective Java", "Joshua Bloch", new BigDecimal("45.00")));
      inventory.add(new PhysicalBook("978-1617292545", "Grokking Algorithms", "Aditya Bhargava", new BigDecimal("40.00")));
      inventory.add(new PhysicalBook("978-1234567890", "New Order", "Author Name", new BigDecimal("35.00")));
      inventory.add(new PhysicalBook("978-0596007126", "Head First Design Patterns", "Eric Freeman", new BigDecimal("50.00")));
      
      // E-books
      inventory.add(new EBook("978-0134685991", "Clean Code", "Robert C. Martin", new BigDecimal("30.00"))); // ราคา EBook จะถูกลด 10%
      inventory.add(new EBook("978-1492032630", "Designing Data-Intensive Applications", "Martin Kleppmann", new BigDecimal("55.00")));
      
      // Audio books
      inventory.add(new AudioBook("978-0132350884", "The Mythical Man-Month", "Frederick P. Brooks Jr.", new BigDecimal("22.00")));
      inventory.add(new AudioBook("978-1984801258", "The Pragmatic Programmer", "David Thomas, Andrew Hunt", new BigDecimal("25.00"))); // ราคา AudioBook จะบวก 5%
    }
    
    private static void initializeCustomers() {
        // เพิ่มลูกค้าตัวอย่าง
        customers.add(new Customer("C001", "Alice Wonderland", Customer.CustomerType.VIP));
        customers.add(new Customer("C002", "Bob The Builder", Customer.CustomerType.GENERAL));
    }


    private static void printMainMenu() {
        System.out.println("\n--- Online Bookstore Menu ---");
        if (currentCustomer != null) {
            System.out.println("Current Customer: " + currentCustomer.getUsername() + " (" + currentCustomer.getCustomerType() + ")");
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
            // displayDetails() ในแต่ละประเภทย่อยจะแสดงราคาที่ปรับปรุงแล้ว
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
        if (inventory.isEmpty()) return;

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
        // สร้าง Order object โดยส่งสำเนาของรายการในตะกร้าไป (ป้องกันการแก้ไขตะกร้าโดยตรง)
        Order order = new Order(currentCustomer, new ArrayList<>(shoppingCart.getItems()));
        order.displayOrderSummary(); // แสดงสรุปคำสั่งซื้อพร้อมราคาสุดท้าย

        // ตรรกะการให้แต้มสะสมแบบง่าย:
        // ลูกค้าทั่วไป: 1 แต้ม ต่อทุกๆ 10 หน่วยสกุลเงินที่ใช้จ่าย
        // ลูกค้า VIP: 2 แต้ม ต่อทุกๆ 10 หน่วยสกุลเงินที่ใช้จ่าย
        BigDecimal amountSpent = order.getGrandTotal(); // ยอดที่จ่ายจริง
        int pointsEarnedBase = amountSpent.divide(new BigDecimal("10"), 0, RoundingMode.FLOOR).intValue();
        int pointsMultiplier = currentCustomer.isVIP() ? 2 : 1;
        int totalPointsEarned = pointsEarnedBase * pointsMultiplier;
        
        if (totalPointsEarned > 0) {
            currentCustomer.addLoyaltyPoints(totalPointsEarned);
        }

        System.out.println("Thank you for your order, " + currentCustomer.getUsername() + "!");
        shoppingCart.clearCart(); // ล้างตะกร้าหลังจากการสั่งซื้อสำเร็จ
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
            // ใช้ calculatePrice() ซึ่งรวมการปรับราคาตามประเภทแล้ว
            // เพื่อให้การเปรียบเทียบราคาสอดคล้องกับราคาที่ลูกค้าเห็น
            BigDecimal currentBookPrice = book.calculatePrice(); 

            if (!highestPriceBooks.containsKey(type) || // Fixed the syntax error
                currentBookPrice.compareTo(highestPriceBooks.get(type).calculatePrice()) > 0) {
                highestPriceBooks.put(type, book);
            }
        }

        if (highestPriceBooks.isEmpty()) {
            System.out.println("Could not determine recommendations.");
        } else {
            highestPriceBooks.forEach((type, book) -> 
                System.out.println("Highest priced " + type + ": " + book.getTitle() + 
                                   " at $" + book.calculatePrice().setScale(2, RoundingMode.HALF_UP))
            );
        }
        System.out.println("--------------------------------------------------");
    }
}
