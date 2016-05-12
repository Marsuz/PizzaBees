package solver;

import model.Order;
import model.Restaurant;
import model.Setting;
import solver.setting.AbstractSettingFactory;
import solver.setting.SettingFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Marcin on 2016-04-19.
 */
public class Solver {

    List<Restaurant> beginRestaurants;
    List<Order> beginOrders;

    public Solver(List<Restaurant> restaurants, List<Order> orders){
        beginOrders = orders;
        beginRestaurants = restaurants;
    }

    public void solve(
            SettingFactory factory,
            double distanceWage,
            double timeWage,
            int scoutsQuantity,
            int selectedSites,
            int bestSites,
            int eliteQuantity,
            int normalQuantity,
            int iterations
    ){
        List<Setting> scoutsSettings = new ArrayList<>();
        IntStream.range(0, scoutsQuantity).forEach(
                n -> scoutsSettings.add(n, factory.getInitialSetting(beginOrders))
        );

        while(iterations-- != 0) { // TODO: second stop condition
            Collections.sort(scoutsSettings,
                    (s1, s2) -> Double.compare(
                            s1.getFitness(distanceWage, timeWage),
                            s2.getFitness(distanceWage, timeWage)
                    )
            );



        }

    }
}
