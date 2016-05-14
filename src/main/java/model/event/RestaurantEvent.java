package model.event;

import model.Courier;
import model.Delivery;

import java.util.Comparator;
import java.util.Queue;

/**
 * Created by Admin on 2016-04-22.
 */
public interface RestaurantEvent {
    Comparator<RestaurantEvent> BY_TIME = new EventComparator();
    void sortMyself(Queue<Courier> freeCouriers, Queue<Delivery> readyDeliveries, Queue<RestaurantEvent> events);
    long getTime();

    class EventComparator implements Comparator<RestaurantEvent> {
        @Override
        public int compare(RestaurantEvent o1, RestaurantEvent o2) {
            if(o1.getTime() > o2.getTime()) return 1;
            if(o1.getTime() == o2.getTime()) return 0;
            return -1;
        }
    }
}
