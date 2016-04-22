package solver.permutation;

import model.Restaurant;

import java.util.List;

/**
 * Created by Admin on 2016-04-22.
 */
public interface SettingFactory {

    public List<Restaurant> getInitialSetting();

    public List<Restaurant> getNextSetting(List<Restaurant> restaurants);
}
