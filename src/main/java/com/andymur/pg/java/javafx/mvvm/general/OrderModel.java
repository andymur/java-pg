package com.andymur.pg.java.javafx.mvvm.general;

import com.andymur.pg.java.javafx.mvvm.domain.Order;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by andymur on 12/10/17.
 */
public class OrderModel {
    private List<Order> orders = new ArrayList<>();

    public OrderModel() {
        this.orders.add(new Order(LocalDate.now(), 100, Order.OrderType.ENTERTAINMENT));
    }

    public List<Order> orders() {
        return orders;
    }
}
