package model;

import java.util.List;

/**
 * Created by Jakub Kudzia on 4/19/16.
 */
public class Delivery {

    private List<Order> orders;

    public Delivery(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
