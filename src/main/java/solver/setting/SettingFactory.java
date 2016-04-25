package solver.setting;

import model.Order;
import model.Restaurant;

import java.util.List;

public abstract class SettingFactory {

    List<Restaurant> restaurants;

    public SettingFactory(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
    }

    public abstract List<Restaurant> getInitialSetting(List<Order> orders);
    public abstract List<Restaurant> getNeighbourSetting(List<Restaurant> restaurants);
}
