package book;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import com.bookstore.books.*;
import com.bookstore.customer.Customer;
import com.bookstore.order.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

class BookstorePackageStructureTest {
    
    private PhysicalBook physicalBook;
    private EBook ebook;
    private AudioBook audiobook;
    private Customer generalCustomer;
    private Customer vipCustomer;
    private ShoppingCart cart;
    
    @BeforeEach
    void setUp() {
        physicalBook = new PhysicalBook("123", "Test Physical Book", "Test Author", new BigDecimal("20.00"));
        ebook = new EBook("456", "Test EBook", "Test Author", new BigDecimal("20.00"));
        audiobook = new AudioBook("789", "Test AudioBook", "Test Author", new BigDecimal("20.00"));
        generalCustomer = new Customer("C001", "General User", Customer.CustomerType.GENERAL);
        vipCustomer = new Customer("C002", "VIP User", Customer.CustomerType.VIP);
        cart = new ShoppingCart();
    }

    @Test
    void testBookPackageHierarchy() {
        // Test that all book types implement proper inheritance
        assertTrue(physicalBook instanceof Book, "PhysicalBook should extend Book");
        assertTrue(ebook instanceof Book, "EBook should extend Book");
        assertTrue(audiobook instanceof Book, "AudioBook should extend Book");
        
        // Test book type identification
        assertEquals("PhysicalBook", physicalBook.getBookType());
        assertEquals("EBook", ebook.getBookType());
        assertEquals("AudioBook", audiobook.getBookType());
    }

    @Test
    void testBookPricingStrategies() {
        // Physical book should have base price
        assertEquals(0, physicalBook.calculatePrice().compareTo(new BigDecimal("20.00")));
        
        // EBook should have 10% discount
        assertEquals(0, ebook.calculatePrice().compareTo(new BigDecimal("18.00")));
        
        // AudioBook should have 5% fee added
        assertEquals(0, audiobook.calculatePrice().compareTo(new BigDecimal("21.00")));
    }

    @Test
    void testCustomerPackage() {
        // Test customer types
        assertFalse(generalCustomer.isVIP());
        assertTrue(vipCustomer.isVIP());
        
        // Test loyalty points
        assertEquals(0, generalCustomer.getLoyaltyPoints());
        generalCustomer.addLoyaltyPoints(10);
        assertEquals(10, generalCustomer.getLoyaltyPoints());
    }

    @Test
    void testOrderPackage() {
        // Test shopping cart functionality
        OrderItem item1 = new OrderItem(physicalBook, 2);
        OrderItem item2 = new OrderItem(ebook, 1);
        
        cart.addItem(item1);
        cart.addItem(item2);
        
        assertEquals(2, cart.getItems().size());
        
        // Test subtotal calculation
        BigDecimal expectedSubtotal = new BigDecimal("58.00"); // (20*2) + (18*1)
        assertEquals(0, cart.calculateSubTotalBeforeVipDiscount().compareTo(expectedSubtotal));
    }

    @Test
    void testOrderCreationAndVipDiscount() {
        // Create order items
        List<OrderItem> items = new ArrayList<>();
        items.add(new OrderItem(ebook, 1)); // $18.00 after ebook discount
        
        // Test general customer order
        Order generalOrder = new Order(generalCustomer, items);
        assertEquals(0, generalOrder.getGrandTotal().compareTo(new BigDecimal("18.00")));
        
        // Test VIP customer order (additional 15% discount)
        Order vipOrder = new Order(vipCustomer, items);
        BigDecimal expectedVipTotal = new BigDecimal("18.00").multiply(new BigDecimal("0.85")); // 15% discount
        assertEquals(0, vipOrder.getGrandTotal().compareTo(expectedVipTotal));
    }

    @Test
    void testPackageOrganization() {
        // Verify that classes are in their correct packages
        assertEquals("com.bookstore.books.Book", Book.class.getName());
        assertEquals("com.bookstore.books.PhysicalBook", PhysicalBook.class.getName());
        assertEquals("com.bookstore.books.EBook", EBook.class.getName());
        assertEquals("com.bookstore.books.AudioBook", AudioBook.class.getName());
        assertEquals("com.bookstore.customer.Customer", Customer.class.getName());
        assertEquals("com.bookstore.order.Order", Order.class.getName());
        assertEquals("com.bookstore.order.OrderItem", OrderItem.class.getName());
        assertEquals("com.bookstore.order.ShoppingCart", ShoppingCart.class.getName());
    }

    @Test
    void testCrossPackageIntegration() {
        // Test that classes from different packages work together
        OrderItem item = new OrderItem(physicalBook, 3);
        cart.addItem(item);
        
        List<OrderItem> orderItems = new ArrayList<>(cart.getItems());
        Order order = new Order(vipCustomer, orderItems);
        
        // Verify the order was created successfully with cross-package dependencies
        assertNotNull(order);
        assertEquals(vipCustomer.getUsername(), "VIP User");
        assertEquals(1, orderItems.size());
        assertEquals("Test Physical Book", orderItems.get(0).getBook().getTitle());
    }
}
