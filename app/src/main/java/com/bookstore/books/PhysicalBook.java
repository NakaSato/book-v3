package com.bookstore.books;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PhysicalBook extends Book {
    public PhysicalBook(String isbn, String title, String author, BigDecimal basePrice) {
        super(isbn, title, author, basePrice);
    }

    @Override
    public BigDecimal calculatePrice() {
        // สำหรับหนังสือทั่วไป (PhysicalBook) จะไม่มีการปรับราคาเพิ่ม 
        return this.basePrice;
    }

    @Override
    public String displayDetails() {
        return " " + super.displayDetails() +
               ", Final Price (after type adjustment): $" + calculatePrice().setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String getBookType() {
        return "PhysicalBook";
    }
}
