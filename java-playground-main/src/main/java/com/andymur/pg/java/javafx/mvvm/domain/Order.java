package com.andymur.pg.java.javafx.mvvm.domain;

import java.time.LocalDate;

/**
 * Created by andymur on 12/10/17.
 */
public class Order {
    private final OrderType orderType;
    private final double price;
    private final LocalDate purchaseDate;


    public Order(LocalDate purchaseDate, double price, OrderType orderType) {
        this.purchaseDate = purchaseDate;
        this.price = price;
        this.orderType = orderType;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Order{");
        sb.append("orderType=").append(orderType);
        sb.append(", price=").append(price);
        sb.append(", purchaseDate=").append(purchaseDate);
        sb.append('}');
        return sb.toString();
    }

    public enum OrderType {
        TRANSPORT, FOOD, ENTERTAINMENT
    }
}
