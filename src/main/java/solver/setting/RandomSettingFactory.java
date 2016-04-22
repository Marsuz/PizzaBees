package solver.setting;

import model.Restaurant;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public class RandomSettingFactory implements SettingFactory{
    @Override
    public List<Restaurant> getInitialSetting() {
        return null;
    }

    @Override
    public List<Restaurant> getNeighbourSetting(List<Restaurant> restaurants) {
        return null;
    }
}
