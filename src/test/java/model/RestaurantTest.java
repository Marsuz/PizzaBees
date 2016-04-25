package model;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class RestaurantTest {

    private Restaurant restaurant;
    private Restaurant restaurant2;

    @Before
    public void setup(){
        restaurant = new Restaurant(0, 0, 3);
        restaurant2 = new Restaurant(0, 0, 1);
        Restaurant.P = 10;
        Courier.velocity = 1;

        List<Delivery> deliveries = new ArrayList<>();

        for(int i = 0; i < 9; i++){
            Delivery delivery = EasyMock.createMock(Delivery.class);
            EasyMock.expect(delivery.getDistance(restaurant)).andReturn((long)10);
            EasyMock.expect(delivery.getQuantity()).andReturn(1);
            EasyMock.replay(delivery);
            deliveries.add(delivery);
        }

        List<Delivery> deliveries2 = new ArrayList<>();

        Delivery delivery = EasyMock.createMock(Delivery.class);
        EasyMock.expect(delivery.getDistance(restaurant)).andReturn((long)10);
        EasyMock.expect(delivery.getQuantity()).andReturn(1);
        EasyMock.replay(delivery);
        deliveries2.add(delivery);

        restaurant.setDeliveries(deliveries);
        restaurant2.setDeliveries(deliveries2);
    }

    @Test
    public void testSimulate(){
        restaurant.simulate();
        restaurant2.simulate();
        assertEquals(20, restaurant2.maxTime());
        assertEquals(100, restaurant.maxTime());
    }
}
