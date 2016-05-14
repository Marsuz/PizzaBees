package app.savedSate;

import model.Order;
import model.Restaurant;

import java.util.List;

public class SavedState {
    private List<Restaurant> restaurants;
    private int P;
    private int V;
    private List<Order> orders ;

    public SavedState(List<Restaurant> restaurants, int p, List<Order> orders) {
        this.restaurants = restaurants;
        P = p;
        this.orders = orders;
    }

    public SavedState(List<Restaurant> restaurants, int p, int v, List<Order> orders) {
        this.restaurants = restaurants;
        P = p;
        V = v;
        this.orders = orders;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }

    public int getP() {
        return P;
    }

    public int getV() {
        return V;
    }

    public List<Order> getOrders() {
        return orders;
    }
}
