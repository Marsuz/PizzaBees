package model;

import model.event.CourierEvent;
import model.event.DeliveryEvent;
import model.event.RestaurantEvent;

import java.util.*;

/**
 * Created by Marcin on 2016-04-19.
 */
public class Restaurant extends Location {

    private int courierNumber;
    private Set<Courier> couriers = null;
    private List<Delivery> deliveries = null;
    public static int P; // how many units of time creating single object takes

    public Restaurant(int x, int y, int courierNumber) {
        super(x, y);
        this.courierNumber = courierNumber;
        couriers = new HashSet<Courier>();
        initCouriers(courierNumber);
        deliveries =  new ArrayList<Delivery>();
    }

    public void initCouriers(int courierNumber) {
        for(int i = 0; i < courierNumber; i++) couriers.add(new Courier());
    }

    public Set<Courier> getCouriers() {
        return couriers;
    }

    public void setCouriers(Set<Courier> couriers) {
        this.couriers = couriers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

    public void setDeliveries(List<Delivery> deliveries){
        this.deliveries = deliveries;
    }

    public void addDelivery(Delivery delivery){
            this.deliveries.add(delivery);
    }

    public void simulate(){
        long currTime = 0;

        Queue<Courier> freeCouriers = new LinkedList<>();
        Queue<Delivery> readyDeliveries = new LinkedList<>();

        Queue<RestaurantEvent> events = new PriorityQueue<>(RestaurantEvent.BY_TIME);
        for(Delivery d: deliveries){
            currTime += d.getQuantity() * P;

            events.add(new DeliveryEvent(currTime, this, d));
        }

        for(Courier c: couriers){
            events.add(new CourierEvent(c, this));
        }

        while(!events.isEmpty()){
            RestaurantEvent tmp = events.remove();
            System.out.println(tmp.getClass() + " time:" + tmp.getTime());
            tmp.sortMyself(freeCouriers, readyDeliveries, events);
        }

    }

    public int getDistance(){
        int distance = 0;

        for(Delivery d: deliveries) {
            distance += d.getDistance(this);
        }

        return distance;
    }

    public long maxTime(){
        long max = 0;

        for(Courier c: couriers){
            max = Long.max(max, c.getTime());
        }

        return max;
    }

}
