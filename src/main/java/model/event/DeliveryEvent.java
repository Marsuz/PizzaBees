package model.event;

import model.Courier;
import model.Delivery;
import model.Restaurant;

import java.util.Queue;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class DeliveryEvent implements RestaurantEvent {
    private long time;
    private Delivery delivery;
    private Restaurant restaurant;

    public DeliveryEvent(long time, Restaurant restaurant, Delivery delivery){
        this.time = time;
        this.delivery = delivery;
        this.restaurant = restaurant;
    }

    @Override
    public void sortMyself(Queue<Courier> freeCouriers, Queue<Delivery> readyDeliveries, Queue<RestaurantEvent> events) {
        if(freeCouriers.isEmpty())readyDeliveries.add(delivery);
        else{
            Courier courier = freeCouriers.remove();

            courier.executeDelivery(delivery.getDistance(restaurant), time);

            events.add(new CourierEvent(courier, restaurant));
        }
    }

    @Override
    public long getTime() {
        return time;
    }
}
