package model;

public class Courier {
    private long time;
    public static int velocity;

    public Courier(){
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
