package com.andymur.pg.java.javafx.mvvm.general;

import com.andymur.pg.java.javafx.mvvm.base.BaseView;
import com.andymur.pg.java.javafx.mvvm.domain.Order;
import com.andymur.pg.java.javafx.mvvm.domain.Order.OrderType;
import javafx.scene.control.ListView;

import java.time.LocalDate;

/**
 * Created by andymur on 12/10/17.
 */
public class OrderView extends BaseView<OrderViewModel> {

    ListView<Order> listView;

    public OrderView(OrderViewModel viewModel) {
        super(viewModel);
        listView = new ListView<>();
        listView.itemsProperty().bind(viewModel.orderListPropertyProperty());
    }

    public void addOrder(Order order) {
        listView.getItems().add(order);
    }

    public void addOrder(OrderType type, double price, LocalDate purchaseDate) {
        addOrder(new Order(purchaseDate, price, type));
    }

    public void addOrder(OrderType type, double price) {
        addOrder(type, price, LocalDate.now());
    }
}
