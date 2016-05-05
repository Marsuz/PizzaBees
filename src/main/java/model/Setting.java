package model;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public abstract class Setting {
    private List<Restaurant> restaurants;

    public Setting(List<Restaurant> restaurants){
        this.restaurants = restaurants;

        for(Restaurant r: restaurants){
            r.simulate();
        }
    }

    public double getFitness(double distanceWage, double timeWage){
        long distance = 0;
        long time = 0;

        for(Restaurant r: restaurants){
            distance += r.getDistance();
            time = Long.max(time, r.maxTime());
        }

        return distance * distanceWage + time * timeWage;
    }

    public abstract Setting getNeighbour(int moves);
}
