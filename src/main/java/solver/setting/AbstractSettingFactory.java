package solver.setting;

import model.Order;
import model.Restaurant;

import java.util.List;

public abstract class AbstractSettingFactory implements SettingFactory{

    protected List<Restaurant> restaurants;
    protected List<Order> orders;

    public AbstractSettingFactory(List<Restaurant> restaurants, List<Order> orders) {
        this.restaurants = restaurants;
        this.orders = orders;
    }

}
