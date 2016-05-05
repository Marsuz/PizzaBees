package solver.setting;

import model.Restaurant;
import model.Setting;

import java.util.List;

/**
 * Created by Jakub Kudzia on 5/5/16.
 */
public class SettingImpl extends Setting {


    public SettingImpl(List<Restaurant> restaurants) {
        super(restaurants);
    }

    @Override
    public Setting getNeighbour(int moves) {
        //TODO
        return null;
    }
}
