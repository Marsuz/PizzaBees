package model;

/**
 * Created by Marcin on 2016-04-19.
 */
public class Order {

    private Location destination;
    private int quantity;

    public Order(int x, int y, int quantity) {
        this.destination = new Location(x, y);
        this.quantity = quantity;
    }

    public Location getDestination() {
        return destination;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "Order{" +
                "destination=" + destination +
                ", quantity=" + quantity +
                '}';
    }
}
