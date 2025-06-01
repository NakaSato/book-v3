package com.bookstore.books;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class EBook extends Book {
    private static final BigDecimal DISCOUNT_RATE = new BigDecimal("0.10"); // ส่วนลด 10%

    public EBook(String isbn, String title, String author, BigDecimal basePrice, String publishYear) {
        super(isbn, title, author, basePrice, publishYear);
    }

    @Override
    public BigDecimal calculatePrice() {
        BigDecimal discountAmount = this.basePrice.multiply(DISCOUNT_RATE);
        return this.basePrice.subtract(discountAmount);
    }

    @Override
    public String displayDetails() {
        return " " + super.displayDetails() +
               ", Discount: 10%" +
               ", Final Price (after type adjustment): $" + calculatePrice().setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String getBookType() {
        return "EBook";
    }
}
