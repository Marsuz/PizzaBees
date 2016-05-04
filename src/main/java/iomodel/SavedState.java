package iomodel;

import model.Order;
import model.Restaurant;

import java.util.List;

public class SavedState {
    private List<Restaurant> restaurants;
    private int P;
    private List<Order> orders ;

    public SavedState(List<Restaurant> restaurants, int p, List<Order> orders) {
        this.restaurants = restaurants;
        P = p;
        this.orders = orders;
    }
}
