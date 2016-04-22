package model.event;

import model.Courier;
import model.Delivery;

import java.util.Queue;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class DeliveryEvent implements RestaurantEvent {
    long time;
    Delivery delivery;

    public DeliveryEvent(long time, Delivery delivery){
        this.time = time;
        this.delivery = delivery;
    }

    @Override
    public void sortMyself(Queue<Courier> freeCouriers, Queue<Delivery> readyDeliveries, Queue<RestaurantEvent> events) {
        if(freeCouriers.isEmpty())readyDeliveries.add(delivery);
        else{
            Courier courier = freeCouriers.remove();

            events.add(new CourierEvent(courier));
        }
    }

    @Override
    public long getTime() {
        return time;
    }
}
