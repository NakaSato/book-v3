package com.bookstore.books;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AudioBook extends Book {
    private static final BigDecimal FEE_RATE = new BigDecimal("0.05"); // ค่าธรรมเนียม 5%

    public AudioBook(String isbn, String title, String author, BigDecimal basePrice) {
        super(isbn, title, author, basePrice);
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal feeAmount = this.basePrice.multiply(FEE_RATE);
        return this.basePrice.add(feeAmount);
    }

    @Override
    public String displayDetails() {
        return " " + super.displayDetails() +
               ", Fee: 5%" +
               ", Final Price (after type adjustment): $" + calculatePrice().setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public String getBookType() {
        return "AudioBook";
    }
}
