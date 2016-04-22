package model;

import solver.permutation.SettingFactory;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public class Setting {
    private List<Restaurant> restaurants;

    public Setting(List<Restaurant> restaurants){
        this.restaurants = restaurants;

    }

    public long getFitness(double wage){
        long distance = 0;
        long time = 0;

        for(Restaurant r: restaurants){
            distance += r.getDistance();
            time = Long.max(time, r.maxTime());
        }

        return distance + (long)(time * wage);
    }

    public Setting getNeighbour(SettingFactory factory){
        return new Setting(factory.getNextSetting(restaurants));
    }
}
