package generator.generator.impl;

import generator.generator.Generator;
import model.Order;
import model.Restaurant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2016-05-14.
 */
public class SquarePlacedGenerator implements Generator {

    private int maxOrderQuantity = 10;
    private int maxCourierNumber = 2;
    private int P = 5;
    private int V = 5;
    private int distanceBetweenRestaurants;
    private int baseX;
    private int baseY;
    private int stepDivideFactor = 3;

    public SquarePlacedGenerator(int maxOrderQuantity, int maxCourierNumber, int p, int v, int distanceBetweenRestaurants, int stepDivideFactor) {
        this.maxOrderQuantity = maxOrderQuantity;
        this.maxCourierNumber = maxCourierNumber;
        P = p;
        V = v;
        this.distanceBetweenRestaurants = distanceBetweenRestaurants;
        this.stepDivideFactor = stepDivideFactor;
        baseX = distanceBetweenRestaurants;
        baseY = distanceBetweenRestaurants;
    }

    @Override
    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        for (int i = 0; i <= 1; i++) {
            for (int j = 0; j <= 1; j++) {
                for (int k = -1; k <= 1; k++) {
                    for (int m = -1; m <= 1; m++) {
                        if (Math.abs(k) != Math.abs(m)) {
                            Order order = new Order(baseX + j * baseX + k * (distanceBetweenRestaurants / stepDivideFactor), baseY + i * baseY + m * (distanceBetweenRestaurants /stepDivideFactor), maxOrderQuantity);
                            orders.add(order);
                        }
                    }
                }
            }
        }
        return orders;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        Restaurant restaurant1 = new Restaurant(baseX, baseY);
        Restaurant restaurant2 = new Restaurant(baseX, baseY + distanceBetweenRestaurants);
        Restaurant restaurant3 = new Restaurant(baseX + distanceBetweenRestaurants, baseY);
        Restaurant restaurant4 = new Restaurant(baseX + distanceBetweenRestaurants, baseY + distanceBetweenRestaurants);
        restaurant1.setCourierNumber(maxCourierNumber);
        restaurant2.setCourierNumber(maxCourierNumber);
        restaurant3.setCourierNumber(maxCourierNumber);
        restaurant4.setCourierNumber(maxCourierNumber);
        restaurants.add(restaurant1);
        restaurants.add(restaurant2);
        restaurants.add(restaurant3);
        restaurants.add(restaurant4);
        return restaurants;
    }

    @Override
    public int getP() {
        return P;
    }

    @Override
    public int getV() {
        return V;
    }
}
