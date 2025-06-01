package com.bookstore.books;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Book {
    protected String isbn;
    protected String title;
    protected String author;
    protected String publishYear; // ปีที่ตีพิมพ์ (อาจจะใช้ในอนาคต)
    protected BigDecimal basePrice; // ราคาตั้งต้น

    public Book(String isbn, String title, String author, BigDecimal basePrice, String publishYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.basePrice = basePrice;
        this.publishYear = publishYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public String getPublishYear() {
        return publishYear;
    }

    // คำนวณราคาหลังจากการปรับปรุงตามประเภทหนังสือ (เช่น ส่วนลด E-book, ค่าธรรมเนียม Audiobook)
    public abstract BigDecimal calculatePrice();

    public String displayDetails() {
        return "ISBN: " + isbn + ", Title: '" + title + "', Author: '" + author +
               "', Publish Year: " + publishYear + ", Base Price: $" + basePrice.setScale(2, RoundingMode.HALF_EVEN);
    }

    // เมธอดเสริมสำหรับช่วยในการแสดงประเภทหนังสือ
    public abstract String getBookType();
}
