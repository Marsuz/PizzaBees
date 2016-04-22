package model;

import java.util.List;

public class Delivery {

    private List<Order> orders;

    public Delivery(List<Order> orders) {
        this.orders = orders;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public long getDistance(Location restaurant){
        long distance = 0;

        Location temp = restaurant;
        for(Order order: orders){
            distance += order.getDestination().getDistance(temp);
            temp = order.getDestination();
        }

        distance += temp.getDistance(restaurant);

        return distance;
    }
}
