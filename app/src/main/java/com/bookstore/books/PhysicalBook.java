package com.bookstore.books;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PhysicalBook extends Book {

    private String pageNumber;
    private String coverType;

    public PhysicalBook(String isbn, String title, String author, BigDecimal basePrice, String pageNumber, String coverType, String publishYear) {
        super(isbn, title, author, basePrice, publishYear);
        this.pageNumber = pageNumber;
        this.coverType = coverType;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public String getCoverType() {
        return coverType;
    }
    @Override
    public BigDecimal calculatePrice() {
        // สำหรับหนังสือทั่วไป (PhysicalBook) จะไม่มีการปรับราคาเพิ่ม 
        return this.basePrice;
    }

    @Override
    public String displayDetails() {
        return " " + super.displayDetails() +
                ", Page Number: " + pageNumber +
                ", Cover Type: " + coverType +
               ", Final Price (after type adjustment): $" + calculatePrice().setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public String getBookType() {
        return "PhysicalBook";
    }
}
