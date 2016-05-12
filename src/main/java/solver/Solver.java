package solver;

import model.Setting;
import solver.setting.SettingFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Marcin on 2016-04-19.
 */
public class Solver {
//    List<Restaurant> beginRestaurants;
//    List<Order> beginOrders;

//    public Solver(List<Restaurant> restaurants, List<Order> orders){
//        beginOrders = orders;
//        beginRestaurants = restaurants;
//    }

    public Setting solve(
            SettingFactory factory,
            double distanceWage,
            double timeWage,
            int scoutsQuantity,
            int selectedSites,
            int bestSites,
            int eliteQuantity,
            int normalQuantity,
            int iterations,
            int movesNum
    ){
        List<Setting> iterationSettings = new ArrayList<>();
        IntStream.range(0, scoutsQuantity).forEach(
                n -> iterationSettings.add(n, factory.getInitialSetting())
        );

        while(iterations-- != 0) { // TODO: second stop condition
            List<Setting> newSetting = new ArrayList<>();

            Collections.sort(iterationSettings,
                    (s1, s2) -> Double.compare(
                            s2.getFitness(distanceWage, timeWage),
                            s1.getFitness(distanceWage, timeWage)
                    )
            );

            IntStream.range(0, bestSites).forEach(
                    n -> {
                        List<Setting> tempSettings = new ArrayList<>();
                        IntStream.range(0, eliteQuantity).forEach(
                                m -> tempSettings.add(iterationSettings.get(n).getNeighbour(movesNum))
                        );
                        Collections.sort(tempSettings,
                                (s1, s2) -> Double.compare(
                                        s2.getFitness(distanceWage, timeWage),
                                        s1.getFitness(distanceWage, timeWage)
                                )
                        );
                        newSetting.add(tempSettings.get(0));
                    }

            );
            IntStream.range(bestSites, selectedSites).forEach(
                    n -> {
                        List<Setting> tempSettings = new ArrayList<>();
                        IntStream.range(0, normalQuantity).forEach(
                                m -> tempSettings.add(iterationSettings.get(n).getNeighbour(movesNum))
                        );
                        Collections.sort(tempSettings,
                                (s1, s2) -> Double.compare(
                                        s2.getFitness(distanceWage, timeWage),
                                        s1.getFitness(distanceWage, timeWage)
                                )
                        );
                        newSetting.add(tempSettings.get(0));
                    }
            );

            int scoutsLeft = scoutsQuantity - bestSites * eliteQuantity - (selectedSites - bestSites) * normalQuantity;

            IntStream.range(0, scoutsLeft).forEach(
                    n -> newSetting.add(n, factory.getInitialSetting())
            );

            iterationSettings.clear();
            iterationSettings.addAll(newSetting);
        }

        Collections.sort(iterationSettings,
                (s1, s2) -> Double.compare(
                        s2.getFitness(distanceWage, timeWage),
                        s1.getFitness(distanceWage, timeWage)
                )
        );
        return iterationSettings.get(0);
    }
}
