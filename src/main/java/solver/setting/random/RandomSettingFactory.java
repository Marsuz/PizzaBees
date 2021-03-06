package solver.setting.random;

import model.Delivery;
import model.Order;
import model.Restaurant;
import model.Setting;
import solver.setting.AbstractSettingFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomSettingFactory extends AbstractSettingFactory {

    public RandomSettingFactory(List<Restaurant> restaurants, List<Order> orders) {
        super(restaurants, orders);
    }

    @Override
    public Setting getInitialSetting() {

        List<Delivery> deliveries = generateDeliveries();
        List<Restaurant> newRestaurants = new ArrayList<>();

        for(Restaurant r: restaurants) newRestaurants.add(new Restaurant(r));

        Iterator<Restaurant> newRestaurantIterator = newRestaurants.iterator();

        for (Delivery delivery : deliveries) {
            if (!newRestaurantIterator.hasNext())
                //TODO uwaga na pusta liste restauracji
                newRestaurantIterator = newRestaurants.iterator();
            newRestaurantIterator.next().addDelivery(delivery);
        }
        return new RandomSetting(newRestaurants);
    }

    private List<Delivery> generateDeliveries(){

        int ordersNumber = orders.size();
        //todo ordersNumber - 1 moze byc 0, trzeba lapac wyjatek albo wyifowac
//        int deliveriesNumber = new Random().nextInt(ordersNumber - 1) + 1;
        int deliveriesNumber = new Random().nextInt(ordersNumber) + 1;
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
