package model;

import java.util.*;

/**
 * Created by Marcin on 2016-04-19.
 */
public class Restaurant extends Location {

    private int courierNumber;
    private Set<Courier> couriers = null;
    private List<Delivery> deliveries = null;
    public static int P;

    public Restaurant(int x, int y, int courierNumber) {
        super(x, y);
        this.courierNumber = courierNumber;
        couriers = new HashSet<Courier>();
        //for(int i = 0; i < courierNumber; i++) couriers.add(new Courier());
        deliveries =  new ArrayList<Delivery>();
    }

    public Set<Courier> getCouriers() {
        return couriers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

}
