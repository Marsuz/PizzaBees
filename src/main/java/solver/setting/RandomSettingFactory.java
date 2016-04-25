package solver.setting;

import model.Delivery;
import model.Order;
import model.Restaurant;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

public class RandomSettingFactory extends SettingFactory {

    public RandomSettingFactory(List<Restaurant> restaurants) {
        super(restaurants);
    }

    @Override
    public List<Restaurant> getInitialSetting(List<Order> orders) {

        Iterator<Restaurant> restaurantIterator = restaurants.iterator();
        List<Delivery> deliveries = generateDeliveries(orders);

        for (Delivery delivery : deliveries) {
            restaurantIterator.next().addDelivery(delivery);
            if (!restaurantIterator.hasNext())
                restaurantIterator = restaurants.iterator();
        }

        return restaurants;

    }

    @Override
    public List<Restaurant> getNeighbourSetting(List<Restaurant> restaurants) {
        return null;
    }


    private List<Delivery> generateDeliveries(List<Order> orders){

        int ordersNumber = orders.size();
        int deliveriesNumber = new Random().nextInt(ordersNumber - 1) + 1;
        int orderPerDelivery = ordersNumber/deliveriesNumber;
        List<Delivery> deliveries = new ArrayList<>(deliveriesNumber + 1);
        Iterator<Order> orderIterator =  orders.iterator();

        for(int i = 0; i< deliveriesNumber; i++){
            List<Order> ordersForDelivery = new ArrayList<>(orderPerDelivery);
            for(int j = 0; j < orderPerDelivery; j++)
                ordersForDelivery.add(orderIterator.next());
            deliveries.add(new Delivery(ordersForDelivery));
        }

        List<Order> ordersForLastDelivery = new ArrayList<>(orderPerDelivery);
        while(orderIterator.hasNext())
            ordersForLastDelivery.add(orderIterator.next());

        deliveries.add(new Delivery(ordersForLastDelivery));

        return deliveries;

    }

}
