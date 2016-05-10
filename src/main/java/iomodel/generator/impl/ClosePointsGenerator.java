package iomodel.generator.impl;

import iomodel.generator.Generator;
import model.Order;
import model.Restaurant;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by Marcin on 2016-05-04.
 */
public class ClosePointsGenerator implements Generator {

    private static final Logger LOGGER = Logger.getLogger(ClosePointsGenerator.class);
    private static final Random random = new Random();
    private int restaurantsNumber;
    private int ordersNumber;
    private int P;
    private int maxCourierNumber;
    private int maxOrderQuantity;
    private int howCloseRestaurants;
    private int howCloseOrders;
    private int sizeOfSquare;
    private boolean tooLittleSpaceForRestaurants;
    private List<Pair> possibleLocations;

    public ClosePointsGenerator(int restaurantsNumber, int ordersNumber, int p, int maxCourierNumber, int maxOrderQuantity, int howCloseRestaurants, int howCloseOrders) {
        this.restaurantsNumber = restaurantsNumber;
        this.ordersNumber = ordersNumber;
        P = p;
        this.maxCourierNumber = maxCourierNumber;
        this.maxOrderQuantity = maxOrderQuantity;
        this.howCloseRestaurants = howCloseRestaurants;
        this.howCloseOrders = howCloseOrders;
        this.sizeOfSquare = restaurantsNumber/howCloseRestaurants;
        if ( (sizeOfSquare + 1) * (sizeOfSquare + 1) <= restaurantsNumber ) {
            LOGGER.info("This number of restaurants can not be that close to each other. Final restaurants number will be lowered");
            tooLittleSpaceForRestaurants = true;
        } else {
            initPossibleLocations();
        }
    }

    private void initPossibleLocations() {

        possibleLocations = new ArrayList<>();
        for (int i = 0; i <= sizeOfSquare; i++) {
            for (int j = 0; j <= sizeOfSquare; j++){
                possibleLocations.add(new Pair(i, j));
            }
        }

    }

    public List<Restaurant> getRestaurants() {
        if (tooLittleSpaceForRestaurants) return fillWholeSquareWithRestaurants();
        List<Restaurant> restaurants = new ArrayList<>();
        Collections.shuffle(possibleLocations);
        for (Pair pair : possibleLocations) {
            Restaurant restaurant = new Restaurant(pair.x, pair.y);
            restaurant.setCourierNumber(random.nextInt(maxCourierNumber) + 1);
            restaurants.add(restaurant);
        }
        return restaurants;
    }

    private List<Restaurant> fillWholeSquareWithRestaurants() {
        List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i <= sizeOfSquare; i++) {
            for (int j = 0; j <= sizeOfSquare; j++) {
                Restaurant restaurant = new Restaurant(i, j);
                restaurant.setCourierNumber(random.nextInt(maxCourierNumber) + 1);
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        int size = ordersNumber/howCloseOrders;
        for (int i = 0; i < ordersNumber; i++) {
            orders.add(new Order(random.nextInt(size + 1), random.nextInt(size + 1), random.nextInt(maxOrderQuantity) + 1));
        }
        return orders;
    }

    public int getP() {
        return P;
    }

    private class Pair {
        private int x;
        private int y;

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
