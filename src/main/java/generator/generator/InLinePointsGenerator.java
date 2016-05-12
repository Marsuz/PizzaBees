package generator.generator;

import generator.generator.Generator;
import model.Order;
import model.Restaurant;

import java.util.List;
import java.util.Random;

/**
 * Created by Marcin on 2016-05-07.
 */
public class InLinePointsGenerator implements Generator {

    private static final Random random = new Random();
    private int restaurantsNumber;
    private int ordersNumber;
    private int P;
    private int maxCourierNumber;
    private int maxOrderQuantity;


    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public List<Restaurant> getRestaurants() {
        return null;
    }

    @Override
    public int getP() {
        return 0;
    }
}
