package solver.setting;

import model.Restaurant;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public interface SettingFactory {

    public List<Restaurant> getInitialSetting();

    public List<Restaurant> getNeighbourSetting(List<Restaurant> restaurants);
}
