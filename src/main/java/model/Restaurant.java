package model;

import java.util.*;

/**
 * Created by Marcin on 2016-04-19.
 */
public class Restaurant extends Location {

    private Set<Courier> couriers = null;
    private List<Delivery> deliveries = null;
    public static int P;

    public Restaurant(int x, int y) {
        super(x, y);
        couriers = new HashSet<Courier>();
        deliveries =  new ArrayList<Delivery>();
    }

    public Set<Courier> getCouriers() {
        return couriers;
    }

    public List<Delivery> getDeliveries() {
        return deliveries;
    }

}
