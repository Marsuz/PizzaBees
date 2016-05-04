package iomodel.generator;

import model.Order;
import model.Restaurant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * Created by Marcin on 2016-05-04.
 */
public class ClosePointsGenerator {

    private static final Random random = new Random();
    private int restaurantsNumber;
    private int ordersNumber;
    private int P;
    private int maxCourierNumber;
    private int maxOrderQuantity;
    private int howCloseRestaurants;
    private int howCloseOrders;

    public ClosePointsGenerator(int restaurantsNumber, int ordersNumber, int p, int maxCourierNumber, int maxOrderQuantity, int howCloseRestaurants, int howCloseOrders) {
        this.restaurantsNumber = restaurantsNumber;
        this.ordersNumber = ordersNumber;
        P = p;
        this.maxCourierNumber = maxCourierNumber;
        this.maxOrderQuantity = maxOrderQuantity;
        this.howCloseRestaurants = howCloseRestaurants;
        this.howCloseOrders = howCloseOrders;
    }

    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        int size = restaurantsNumber/howCloseRestaurants;
        for (int i = 0; i < restaurantsNumber; i++) {
            Restaurant restaurant = new Restaurant(random.nextInt(size), random.nextInt(size), random.nextInt(maxCourierNumber));
            restaurant.setCouriers(new HashSet<>());
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        int size = ordersNumber/howCloseOrders;
        for (int i = 0; i < ordersNumber; i++) {
            orders.add(new Order(random.nextInt(size), random.nextInt(size), random.nextInt(maxOrderQuantity)));
        }
        return orders;
    }

    public int getP() {
        return P;
    }
}
