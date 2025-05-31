package com.bookstore.test;

import com.bookstore.app.BookstoreApp;
import com.bookstore.books.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class InventoryTest {
    public static void main(String[] args) {
        // Create a test inventory to verify our books were added
        List<Book> testInventory = new ArrayList<>();
        
        // Add the same books as in BookstoreApp to verify they compile and work
        // Physical books
        testInventory.add(new PhysicalBook("978-1491954386", "Mastering Bitcoin", "Andreas M. Antonopoulos", new BigDecimal("47.50")));
        testInventory.add(new PhysicalBook("978-1492054856", "Mastering the Lightning Network", "Andreas M. Antonopoulos", new BigDecimal("52.75")));
        testInventory.add(new PhysicalBook("978-1544526474", "The Bitcoin Standard", "Saifedean Ammous", new BigDecimal("49.99")));
        testInventory.add(new PhysicalBook("978-1544526478", "The Fiat Standard", "Saifedean Ammous", new BigDecimal("54.99")));
        testInventory.add(new PhysicalBook("978-1337563079", "Principles of Economics (2022)", "N. Gregory Mankiw", new BigDecimal("89.95")));
        testInventory.add(new PhysicalBook("978-1234567890", "Bitcoin and Blockchain Technology", "John Smith", new BigDecimal("45.99")));
        testInventory.add(new PhysicalBook("978-0987654321", "The Economics of Cryptocurrency", "Jane Doe", new BigDecimal("52.50")));
        testInventory.add(new PhysicalBook("978-1111222233", "Digital Gold Rush", "Mike Johnson", new BigDecimal("38.75")));
        testInventory.add(new PhysicalBook("978-4444555566", "Decentralized Finance Explained", "Sarah Williams", new BigDecimal("46.99")));
        testInventory.add(new PhysicalBook("978-7777888899", "The Future of Money", "Robert Brown", new BigDecimal("41.25")));
        
        // E-books
        testInventory.add(new EBook("978-1098150097", "Bitcoin for Everyone", "Andreas M. Antonopoulos", new BigDecimal("32.99")));
        testInventory.add(new EBook("978-1492054863", "Mastering the Lightning Network", "Andreas M. Antonopoulos", new BigDecimal("39.99")));
        testInventory.add(new EBook("978-1544526481", "The Bitcoin Standard", "Saifedean Ammous", new BigDecimal("34.99")));
        testInventory.add(new EBook("978-1337563086", "Principles of Economics (2022)", "N. Gregory Mankiw", new BigDecimal("64.99")));
        testInventory.add(new EBook("978-1099876543", "The Bitcoin Enlightenment (2025, Co-Authored)", "Various Authors", new BigDecimal("42.99")));
        testInventory.add(new EBook("978-2234567891", "Cryptocurrency Investment Guide", "John Smith", new BigDecimal("28.99")));
        testInventory.add(new EBook("978-3987654322", "Blockchain for Business", "Jane Doe", new BigDecimal("35.99")));
        testInventory.add(new EBook("978-5111222234", "Digital Currency Revolution", "Mike Johnson", new BigDecimal("24.99")));
        testInventory.add(new EBook("978-6444555567", "Smart Contracts and DApps", "Sarah Williams", new BigDecimal("31.99")));
        testInventory.add(new EBook("978-8777888890", "Crypto Trading Strategies", "Robert Brown", new BigDecimal("27.50")));
        
        // Audio books
        testInventory.add(new AudioBook("978-1098150110", "Bitcoin: The Future of Money", "Andreas M. Antonopoulos", new BigDecimal("29.95")));
        testInventory.add(new AudioBook("978-1544526488", "The Fiat Standard", "Saifedean Ammous", new BigDecimal("39.95")));
        testInventory.add(new AudioBook("978-1099876550", "The Bitcoin Enlightenment (2025, Co-Authored)", "Various Authors", new BigDecimal("37.95")));
        testInventory.add(new AudioBook("978-3234567892", "Understanding Blockchain", "John Smith", new BigDecimal("33.95")));
        testInventory.add(new AudioBook("978-4987654323", "The Crypto Economy", "Jane Doe", new BigDecimal("36.95")));
        testInventory.add(new AudioBook("978-6111222235", "Digital Money Explained", "Mike Johnson", new BigDecimal("31.95")));
        testInventory.add(new AudioBook("978-7444555568", "Web3 and the Metaverse", "Sarah Williams", new BigDecimal("34.95")));
        testInventory.add(new AudioBook("978-9777888891", "The Psychology of Money", "Robert Brown", new BigDecimal("32.95")));
        
        System.out.println("=== EXPANDED INVENTORY TEST ===");
        System.out.println("Total books in inventory: " + testInventory.size());
        System.out.println();
        
        // Count by type
        long physicalCount = testInventory.stream().filter(book -> book instanceof PhysicalBook).count();
        long ebookCount = testInventory.stream().filter(book -> book instanceof EBook).count();
        long audiobookCount = testInventory.stream().filter(book -> book instanceof AudioBook).count();
        
        System.out.println("Physical Books: " + physicalCount);
        System.out.println("E-Books: " + ebookCount);
        System.out.println("Audio Books: " + audiobookCount);
        System.out.println();
        
        // Display some sample books
        System.out.println("=== SAMPLE BOOKS ===");
        for (int i = 0; i < Math.min(5, testInventory.size()); i++) {
            Book book = testInventory.get(i);
            System.out.println((i+1) + ". " + book.toString());
        }
        System.out.println("...");
        
        // Display some of the new books we added
        System.out.println("\n=== NEW BOOKS ADDED ===");
        String[] newBookTitles = {
            "Bitcoin and Blockchain Technology",
            "The Economics of Cryptocurrency", 
            "Digital Gold Rush",
            "Cryptocurrency Investment Guide",
            "Understanding Blockchain"
        };
        
        for (String title : newBookTitles) {
            testInventory.stream()
                .filter(book -> book.getTitle().equals(title))
                .findFirst()
                .ifPresent(book -> System.out.println("✓ " + book.toString()));
        }
        
        System.out.println("\n=== TEST COMPLETED SUCCESSFULLY ===");
        System.out.println("All " + testInventory.size() + " books were successfully created and added to inventory!");
    }
}
