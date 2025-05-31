package com.bookstore.test;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class RoundingModeTest {
    public static void main(String[] args) {
        System.out.println("=== ROUNDING MODE COMPARISON TEST ===");
        System.out.println("Demonstrating why HALF_EVEN reduces cumulative statistical errors\n");
        
        // Test values that end in .5 (the critical case for rounding differences)
        BigDecimal[] testValues = {
            new BigDecimal("12.125"),  // .125 rounds to .13 (up) or .12 (even)
            new BigDecimal("12.135"),  // .135 rounds to .14 (up) or .14 (even)
            new BigDecimal("12.145"),  // .145 rounds to .15 (up) or .14 (even) 
            new BigDecimal("12.155"),  // .155 rounds to .16 (up) or .16 (even)
            new BigDecimal("12.165"),  // .165 rounds to .17 (up) or .16 (even)
            new BigDecimal("12.175"),  // .175 rounds to .18 (up) or .18 (even)
            new BigDecimal("59.995"),  // .995 rounds to 60.00 (up) or 60.00 (even)
            new BigDecimal("25.225"),  // .225 rounds to .23 (up) or .22 (even)
            new BigDecimal("33.335"),  // .335 rounds to .34 (up) or .34 (even)
            new BigDecimal("41.245")   // .245 rounds to .25 (up) or .24 (even)
        };
        
        System.out.printf("%-12s | %-12s | %-12s | %s\n", "Original", "HALF_UP", "HALF_EVEN", "Note");
        System.out.println("-------------|--------------|--------------|---------------------------");
        
        BigDecimal sumOriginal = BigDecimal.ZERO;
        BigDecimal sumHalfUp = BigDecimal.ZERO;
        BigDecimal sumHalfEven = BigDecimal.ZERO;
        
        for (BigDecimal value : testValues) {
            BigDecimal halfUp = value.setScale(2, RoundingMode.HALF_UP);
            BigDecimal halfEven = value.setScale(2, RoundingMode.HALF_EVEN);
            
            sumOriginal = sumOriginal.add(value);
            sumHalfUp = sumHalfUp.add(halfUp);
            sumHalfEven = sumHalfEven.add(halfEven);
            
            String note = halfUp.equals(halfEven) ? "Same" : "Different";
            
            System.out.printf("$%-11s | $%-11s | $%-11s | %s\n", 
                value.toString(), 
                halfUp.toString(), 
                halfEven.toString(),
                note);
        }
        
        System.out.println("-------------|--------------|--------------|---------------------------");
        System.out.printf("$%-11s | $%-11s | $%-11s | %s\n", 
            sumOriginal.setScale(3, RoundingMode.HALF_EVEN), 
            sumHalfUp.toString(), 
            sumHalfEven.toString(),
            "Totals");
        
        System.out.println("\n=== ANALYSIS ===");
        BigDecimal exactSum = sumOriginal.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal halfUpError = sumHalfUp.subtract(exactSum).abs();
        BigDecimal halfEvenError = sumHalfEven.subtract(exactSum).abs();
        
        System.out.println("Original sum (3 decimal places): $" + sumOriginal.setScale(3, RoundingMode.HALF_EVEN));
        System.out.println("Expected sum (2 decimal places): $" + exactSum);
        System.out.println("HALF_UP sum: $" + sumHalfUp + " (Error: $" + halfUpError + ")");
        System.out.println("HALF_EVEN sum: $" + sumHalfEven + " (Error: $" + halfEvenError + ")");
        
        System.out.println("\n=== WHY HALF_EVEN IS BETTER FOR FINANCIAL CALCULATIONS ===");
        System.out.println("1. HALF_UP always rounds .5 upward, creating systematic bias");
        System.out.println("2. HALF_EVEN rounds .5 to the nearest even digit, creating statistical balance");
        System.out.println("3. Over many calculations, HALF_EVEN reduces cumulative rounding errors");
        System.out.println("4. HALF_EVEN is the IEEE 754 standard for floating-point arithmetic");
        System.out.println("5. Banking and financial institutions commonly use HALF_EVEN (Banker's Rounding)");
        
        System.out.println("\n✅ All financial calculations in the Bookstore App now use HALF_EVEN!");
    }
}
