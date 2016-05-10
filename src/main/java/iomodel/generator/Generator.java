package iomodel.generator;

import model.Order;
import model.Restaurant;

import java.util.List;

/**
 * Created by Marcin on 2016-05-07.
 */
public interface Generator {

    List<Order> getOrders();
    List<Restaurant> getRestaurants();
    int getP();

}
