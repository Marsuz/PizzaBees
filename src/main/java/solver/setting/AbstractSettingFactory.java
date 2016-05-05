package solver.setting;

import model.Restaurant;

import java.util.List;

public abstract class AbstractSettingFactory implements SettingFactory{

    List<Restaurant> restaurants;

    public AbstractSettingFactory(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

}
