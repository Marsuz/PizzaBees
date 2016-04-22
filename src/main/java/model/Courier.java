package model;

public class Courier {
    private long time;
    private int velocity;
    private int capacity;

    public Courier(int velocity, int capacity){
        this.velocity = velocity;
        this.capacity = capacity;
        this.time = 0;
    }

    public void executeDelivery(long distance, long startTime){
        time = startTime + distance / velocity;
    }

    public long getTime(){
        return time;
    }


    // TODO: 2016-04-19

}
