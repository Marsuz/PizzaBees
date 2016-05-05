package solver.setting.random;

import model.Restaurant;
import model.Setting;

import java.util.List;

/**
 * Created by Jakub Kudzia on 5/5/16.
 */
public class RandomSetting extends Setting {

    private Shuffler shuffler;

    public RandomSetting(List<Restaurant> restaurants) {
        super(restaurants);
        this.shuffler = new Shuffler();
    }

    @Override
    public Setting getNeighbour(int moves) {

        //TODO
        /**
         * swapowanie pomiedzy delivery
         * swapowanie w delivery
         * swapowanie pomiedzy restauracjami
         * wyrzucanie z jedengo delivery do drugiego wew resetauracji
         * wyrzucanie z jednego delivery do drugiego  w innej restauracji
         * */

        return null;
    }
}
