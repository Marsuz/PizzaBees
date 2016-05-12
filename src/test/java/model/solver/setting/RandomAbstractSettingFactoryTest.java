package model.solver.setting;

import model.Delivery;
import model.Order;
import model.Restaurant;
import org.junit.Before;
import org.junit.Test;
import solver.setting.random.RandomSettingFactory;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Jakub Kudzia on 4/23/16.
 */
public class RandomAbstractSettingFactoryTest {

    private RandomSettingFactory randomSettingFactory;
    private List<Restaurant> restaurants;
    private List<Order> orders;
    private static final int RESTAURANTS_NUM = 10;
    private static final int ORDERS_NUM = 173;

    @Before
    public void setup() {

        restaurants = new ArrayList<>(RESTAURANTS_NUM);
        for (int i = 0; i < RESTAURANTS_NUM; i++) {
            restaurants.add(new Restaurant(i, i, i));
        }

        orders = new ArrayList<>(ORDERS_NUM);
        for (int i = 0; i < ORDERS_NUM; i++) {
            orders.add(new Order(i, i, i));
        }

        randomSettingFactory = new RandomSettingFactory(restaurants);
    }

    @Test
    public void testGetInitialSetting(){
        List<Restaurant> restaurantsWithSettings = randomSettingFactory.getInitialSetting().getRestaurants();
        int setOrdersNumber = 0;

        for(Restaurant r: restaurantsWithSettings)
            setOrdersNumber += getOrdersNumber(r.getDeliveries());
        assertEquals(ORDERS_NUM, setOrdersNumber);
    }


    private int getOrdersNumber(List<Delivery> deliveries){
        int sum = 0;
        for(Delivery d: deliveries)
            sum += d.getOrders().size();

        return sum;
    }

}
