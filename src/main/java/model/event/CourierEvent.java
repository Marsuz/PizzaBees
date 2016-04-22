package model.event;

import model.Courier;
import model.Delivery;

import java.util.Queue;

/**
 * Created by wgrabis on 22.04.2016.
 */
public class CourierEvent implements RestaurantEvent {
    private Courier courier;

    public CourierEvent(Courier courier){
        this.courier = courier;
    }

    @Override
    public void sortMyself(Queue<Courier> freeCouriers, Queue<Delivery> readyDeliveries, Queue<RestaurantEvent> events) {
        if(readyDeliveries.isEmpty())freeCouriers.add(courier);
        else{
            Delivery delivery = readyDeliveries.remove();

            events.add(this);
        }
    }

    @Override
    public long getTime() {
        return courier.getTime();
    }
}
