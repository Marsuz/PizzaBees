package model;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public abstract class Setting {
    protected List<Restaurant> restaurants;
    protected long fullDistance;
    protected long maxTime;

    public Setting(List<Restaurant> restaurants){
        this.restaurants = restaurants;

        for(Restaurant r: restaurants){
            r.simulate();
        }

        long distance = 0;
        long time = 0;

        for(Restaurant r: restaurants){
            distance += r.getDistance();
            time = Long.max(time, r.maxTime());
        }

        fullDistance = distance;
        maxTime = time;
    }

    public double getFitness(double distanceWage, double timeWage){
        return fullDistance * distanceWage + maxTime * timeWage;
    }

    public abstract Setting getNeighbour(int moves);

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
