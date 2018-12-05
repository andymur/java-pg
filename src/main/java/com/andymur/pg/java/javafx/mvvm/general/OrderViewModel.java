package com.andymur.pg.java.javafx.mvvm.general;

import com.andymur.pg.java.javafx.mvvm.base.BaseViewModel;
import com.andymur.pg.java.javafx.mvvm.domain.Order;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * Created by andymur on 12/10/17.
 */
public class OrderViewModel extends BaseViewModel {

    final private ObservableList<Order> orders;
    private ListProperty<Order> orderListProperty;

    private OrderModel model;

    public OrderViewModel(List<Order> orders) {
        this.orders = FXCollections.observableArrayList(orders);
        orderListProperty = new SimpleListProperty<>(getOrders());

        this.orders.addListener(
                new ListChangeListener<Order>() {
                    @Override
                    public void onChanged(Change<? extends Order> c) {
                        System.out.println(c);
                        System.out.println(c.getFrom());
                    }
                }
        );
    }

    public ObservableList<Order> getOrderListProperty() {
        return orderListProperty.get();
    }

    public ListProperty<Order> orderListPropertyProperty() {
        return orderListProperty;
    }

    public ObservableList<Order> getOrders() {
        return orders;
    }
}
