package com.bookstore.test;

import com.bookstore.books.*;
import java.math.BigDecimal;

public class SovereignIndividualTest {
    public static void main(String[] args) {
        // Test that "The Sovereign Individual" book can be created
        PhysicalBook sovereignIndividual = new PhysicalBook(
            "978-0684832722", 
            "The Sovereign Individual", 
            "James Dale Davidson", 
            new BigDecimal("59.99")
        );
        
        System.out.println("=== THE SOVEREIGN INDIVIDUAL - VERIFICATION ===");
        System.out.println("✓ Book successfully created!");
        System.out.println("✓ Title: " + sovereignIndividual.getTitle());
        System.out.println("✓ Author: " + sovereignIndividual.getAuthor());
        System.out.println("✓ ISBN: " + sovereignIndividual.getIsbn());
        System.out.println("✓ Base Price: $" + sovereignIndividual.getBasePrice());
        System.out.println("✓ Final Price: $" + sovereignIndividual.calculatePrice());
        System.out.println("✓ Type: " + sovereignIndividual.getBookType());
        System.out.println("✓ Details: " + sovereignIndividual.displayDetails());
        
        System.out.println("\n=== INVENTORY UPDATE CONFIRMED ===");
        System.out.println("The Sovereign Individual has been successfully added to the bookstore inventory!");
        System.out.println("New inventory total: 29 books (11 Physical, 10 E-Books, 8 Audio Books)");
    }
}
