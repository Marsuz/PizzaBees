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
    private int P = 5;
    private int V = 5;
    private int stepDivideFactor = 3;

    public RectangleFilledPointsGenerator(int restaurantsNumber, int numberOfLines, int maxCourierNumber, int maxOrderQuantity, int step, int P, int V, int stepDivideFactor) {
        this.restaurantsNumber = restaurantsNumber;
        this.numberOfLines = numberOfLines;
        this.maxCourierNumber = maxCourierNumber;
        this.maxOrderQuantity = maxOrderQuantity;
        this.step = step;
        this.P = P;
        this.V = V;
        this.stepDivideFactor = stepDivideFactor;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i < restaurantsNumber; i++) {
            for (int j = 1; j <= numberOfLines; j++) {
                Order order1 = new Order(step * (i + 1), step * j + step / stepDivideFactor, maxOrderQuantity);
                Order order2 = new Order(step * (i + 1), step * j - step / stepDivideFactor, maxOrderQuantity);
                Order order3 = new Order(step * (i + 1) + step / stepDivideFactor, step * j, maxOrderQuantity);
                Order order4 = new Order(step * (i + 1) - step / stepDivideFactor, step * j, maxOrderQuantity);
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
        for (int j = 1; j <= numberOfLines; j++) {
            for (int i = 0; i < restaurantsNumber; i++) {
                Restaurant restaurant = new Restaurant(step * (i + 1), step * j);
                restaurant.setCourierNumber(maxCourierNumber);
                restaurants.add(restaurant);
            }
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
