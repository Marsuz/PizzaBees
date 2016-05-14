package generator.generator.impl;

import generator.generator.Generator;
import model.Order;
import model.Restaurant;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Marcin on 2016-05-07.
 */
public class RectangleFilledPointsGenerator implements Generator {

    private int maxOrderQuantity = 10;
    private int step = 10;
    private int maxCourierNumber = 2;
    private int numberOfLines;
    private int restaurantsNumber;
    private int ordersNumber;
    private int P = 5;
    private int V = 5;

    public RectangleFilledPointsGenerator(int restaurantsNumber, int numberOfLines, int maxCourierNumber, int maxOrderQuantity, int step, int P, int V) {
        this.restaurantsNumber = restaurantsNumber;
        this.ordersNumber = 4 * restaurantsNumber;
        this.numberOfLines = numberOfLines;
        this.maxCourierNumber = maxCourierNumber;
        this.step = step;
        this.P = P;
        this.V = V;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < restaurantsNumber; i += step) {
            for (int j = 1; j <= numberOfLines; j++) {
                Order order1 = new Order(i, 10 * j + step / 3, maxOrderQuantity);
                Order order2 = new Order(i, 10 * j - step / 3, maxOrderQuantity);
                Order order3 = new Order(i + step / 3, 10 * j, maxOrderQuantity);
                Order order4 = new Order(i - step / 3, 10 * j, maxOrderQuantity);
                orders.add(order1);
                orders.add(order2);
                orders.add(order3);
                orders.add(order4);
            }
        }
        return orders;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < 10 * restaurantsNumber; i += step) {
            Restaurant restaurant = new Restaurant(i, step);
            restaurant.setCourierNumber(maxCourierNumber);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    @Override
    public int getP() {
        return P;
    }

    @Override
    public int getV() { return V; }

}
