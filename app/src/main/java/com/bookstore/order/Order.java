package com.bookstore.order;

import com.bookstore.customer.Customer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

public class Order {
    private String orderId;
    private Customer customer;
    private List<OrderItem> orderItems;
    private BigDecimal grandTotal;
    private BigDecimal totalVipDiscountApplied;     
    private static final BigDecimal VIP_DISCOUNT_RATE = new BigDecimal("0.15"); // ส่วนลด VIP 15%

    // Constructor
    public Order(Customer customer, List<OrderItem> orderItems) {
        this.orderId = UUID.randomUUID().toString().substring(0, 8);
        this.customer = customer;
        this.orderItems = orderItems;
        this.totalVipDiscountApplied = BigDecimal.ZERO;
        this.grandTotal = calculateGrandTotal(); // คำนวณยอดรวมสุดท้าย
    }

    private BigDecimal calculateGrandTotal() {
        BigDecimal currentTotal = BigDecimal.ZERO;
        totalVipDiscountApplied = BigDecimal.ZERO;

        for (OrderItem item : orderItems) {
            //  EBook ลด 10%, AudioBook บวก 5%
            BigDecimal itemPriceAfterTypeAdjustment = item.getItemPriceBeforeVipDiscount();
            // ยังไม่รวมส่วนลด VIP
            BigDecimal lineItemPriceBeforeVip = itemPriceAfterTypeAdjustment.multiply(new BigDecimal(item.getQuantity()));

            if (customer.isVIP()) {
                // คำนวณส่วนลด VIP สำหรับรายการสินค้านี้
                BigDecimal vipDiscountAmountPerUnit = itemPriceAfterTypeAdjustment.multiply(VIP_DISCOUNT_RATE);
                BigDecimal priceAfterVipDiscountPerUnit = itemPriceAfterTypeAdjustment.subtract(vipDiscountAmountPerUnit);
                BigDecimal lineItemPriceAfterVip = priceAfterVipDiscountPerUnit.multiply(new BigDecimal(item.getQuantity()));
                
                // สะสมยอดส่วนลด VIP ที่ใช้ไป
                totalVipDiscountApplied = totalVipDiscountApplied.add(lineItemPriceBeforeVip.subtract(lineItemPriceAfterVip));
                currentTotal = currentTotal.add(lineItemPriceAfterVip);
            } else {
                currentTotal = currentTotal.add(lineItemPriceBeforeVip);
            }
        }
        return currentTotal;
    }

    public void displayOrderSummary() {
        System.out.println("\n--- Order Summary ---");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getUsername() + " (" + customer.getCustomerType() + ")");
        System.out.println("Items:");
        for (OrderItem item : orderItems) {
            BigDecimal itemPriceAfterTypeAdj = item.getItemPriceBeforeVipDiscount();
            BigDecimal finalPricePerUnit = itemPriceAfterTypeAdj;
            String vipNote = "";

            if (customer.isVIP()) {
                BigDecimal vipDiscountAmount = itemPriceAfterTypeAdj.multiply(VIP_DISCOUNT_RATE);
                finalPricePerUnit = itemPriceAfterTypeAdj.subtract(vipDiscountAmount);
                vipNote = " (VIP Price: $" + finalPricePerUnit.setScale(2, RoundingMode.HALF_EVEN) +
                          ", Original Item Price (after type adj.): $" + itemPriceAfterTypeAdj.setScale(2, RoundingMode.HALF_EVEN) + ")";
            }

            System.out.println("  - " + item.getQuantity() + " x " + item.getBook().getTitle() +
                               " @ $" + finalPricePerUnit.setScale(2, RoundingMode.HALF_EVEN) + " each" + vipNote);
        }
        if (customer.isVIP() && totalVipDiscountApplied.compareTo(BigDecimal.ZERO) > 0) {
            System.out.println("Total VIP Discount Applied: $" + totalVipDiscountApplied.setScale(2, RoundingMode.HALF_EVEN));
        }
        System.out.println("Grand Total: $" + grandTotal.setScale(2, RoundingMode.HALF_EVEN));
        System.out.println("--------------------");
    }

    public BigDecimal getGrandTotal() {
        return grandTotal;
    }
}
