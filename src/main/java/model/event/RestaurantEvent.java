package model.event;

import model.Courier;
import model.Delivery;

import java.util.Queue;

/**
 * Created by Admin on 2016-04-22.
 */
public interface RestaurantEvent {
    public void sortMyself(Queue<Courier> freeCouriers, Queue<Delivery> readyDeliveries, Queue<RestaurantEvent> events);
    public long getTime();
}
