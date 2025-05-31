package com.bookstore.books;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class Book {
    protected String isbn;
    protected String title;
    protected String author;
    protected BigDecimal basePrice; // ราคาพื้นฐาน

    public Book(String isbn, String title, String author, BigDecimal basePrice) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.basePrice = basePrice;
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

    // คำนวณราคาหลังจากการปรับปรุงตามประเภทหนังสือ (เช่น ส่วนลด E-book, ค่าธรรมเนียม Audiobook)
    public abstract BigDecimal calculatePrice();

    public String displayDetails() {
        return "ISBN: " + isbn + ", Title: '" + title + "', Author: '" + author +
               "', Base Price: $" + basePrice.setScale(2, RoundingMode.HALF_UP);
    }

    // เมธอดเสริมสำหรับช่วยในการแสดงประเภทหนังสือ
    public abstract String getBookType();
}
