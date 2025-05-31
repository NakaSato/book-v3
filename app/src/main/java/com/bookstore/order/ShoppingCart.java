package com.bookstore.order;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<OrderItem> items;

    public ShoppingCart() {
        this.items = new ArrayList<>();
    }

    public void addItem(OrderItem item) {
        this.items.add(item);
        System.out.println("Added to cart: " + item.getQuantity() + " x " + item.getBook().getTitle());
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void clearCart() {
        this.items.clear();
    }

    // คำนวณยอดรวมย่อย (ก่อนส่วนลด VIP)
    public BigDecimal calculateSubTotalBeforeVipDiscount() {
        BigDecimal subTotal = BigDecimal.ZERO;
        for (OrderItem item : items) {
            subTotal = subTotal.add(item.getLineItemTotalBeforeVipDiscount());
        }
        return subTotal;
    }

    public void displayCart() {
        if (items.isEmpty()) {
            System.out.println("Shopping cart is empty.");
            return;
        }
        System.out.println("\n--- Shopping Cart ---");
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            System.out.println((i + 1) + ". " + item.getBook().getTitle() +
                               " (Type: " + item.getBook().getBookType() + ")" +
                               " - Qty: " + item.getQuantity() +
                               " - Price per unit (after type adj.): $" + item.getItemPriceBeforeVipDiscount().setScale(2, RoundingMode.HALF_EVEN) +
                               " - Line Total (before VIP): $" + item.getLineItemTotalBeforeVipDiscount().setScale(2, RoundingMode.HALF_EVEN));
        }
        System.out.println("---------------------");
        System.out.println("Subtotal (before VIP discount): $" + calculateSubTotalBeforeVipDiscount().setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("---------------------");
    }
}
