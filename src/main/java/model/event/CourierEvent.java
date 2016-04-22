package model.event;

import model.Courier;
import model.Delivery;
import model.Restaurant;

import java.util.Queue;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class CourierEvent implements RestaurantEvent {
    private Courier courier;
    private Restaurant restaurant;

    public CourierEvent(Courier courier, Restaurant restaurant){
        this.courier = courier;
        this.restaurant = restaurant;
    }

    @Override
    public void sortMyself(Queue<Courier> freeCouriers, Queue<Delivery> readyDeliveries, Queue<RestaurantEvent> events) {
        if(readyDeliveries.isEmpty())freeCouriers.add(courier);
        else{
            Delivery delivery = readyDeliveries.remove();

            courier.executeDelivery(delivery.getDistance(restaurant), courier.getTime());

            events.add(this);
        }
    }

    @Override
    public long getTime() {
        return courier.getTime();
    }
}
