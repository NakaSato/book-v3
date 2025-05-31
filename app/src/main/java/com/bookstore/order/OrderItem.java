package com.bookstore.order;

import com.bookstore.books.Book;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class OrderItem {
    private Book book;
    private int quantity;

    public OrderItem(Book book, int quantity) {
        this.book = book;
        this.quantity = quantity;
    }

    public Book getBook() {
        return book;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getItemPriceBeforeVipDiscount() {
        return book.calculatePrice();
    }

    public BigDecimal getLineItemTotalBeforeVipDiscount() {
        return getItemPriceBeforeVipDiscount().multiply(new BigDecimal(quantity));
    }

    @Override
    public String toString() {
        return quantity + " x '" + book.getTitle() + "' @ $" + getItemPriceBeforeVipDiscount().setScale(2, RoundingMode.HALF_EVEN) + " each";
    }
}
