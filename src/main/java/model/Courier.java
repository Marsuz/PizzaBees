package model;

public class Courier {
    private double time;
    private int velocity;
    private int capacity;

    public Courier(int velocity, int capacity){
        this.velocity = velocity;
        this.capacity = capacity;
        this.time = 0;
    }

    public void executeDelivery(Delivery delivery, Location restaurant){
        time += delivery.getDistance(restaurant) / velocity;
    }

    public boolean isFree(int currTime){
        return time >= currTime;
    }


    // TODO: 2016-04-19

}
