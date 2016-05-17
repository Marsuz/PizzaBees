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

    public List<Setting> initialSettings(SettingFactory factory, SolverParameters solverParameters) {
        List<Setting> iterationSettings = new ArrayList<>();
        int scoutsQuantity = solverParameters.getScouts();

        IntStream.range(0, scoutsQuantity).forEach(
                n -> iterationSettings.add(n, factory.getInitialSetting())
        );

        return iterationSettings;
    }

    public List<Setting> solveIteration(List<Setting> iterationSettings, SettingFactory factory, SolverParameters solverParameters) {
        double distanceWage = solverParameters.getDistanceWage();
        double timeWage = solverParameters.getTimeWage();
        int scoutsQuantity = solverParameters.getScouts();

        List<Setting> newSetting = new ArrayList<>();

        Collections.sort(iterationSettings,
                (s1, s2) -> Double.compare(
                        s1.getFitness(distanceWage, timeWage),
                        s2.getFitness(distanceWage, timeWage)
                )
        );

        System.out.println(iterationSettings.get(iterationSettings.size() - 1).getFitness(distanceWage, timeWage));

        int bestSites = solverParameters.getBestSites();
        int eliteQuantity = solverParameters.getEliteQuantity();
        int movesNum = solverParameters.getMoves();
        IntStream.range(0, bestSites).forEach(
                n -> {
                    List<Setting> tempSettings = new ArrayList<>();
                    IntStream.range(0, eliteQuantity).forEach(
                            m -> tempSettings.add(iterationSettings.get(n).getNeighbour(movesNum))
                    );
                    Collections.sort(tempSettings,
                            (s1, s2) -> Double.compare(
                                    s1.getFitness(distanceWage, timeWage),
                                    s2.getFitness(distanceWage, timeWage)
                            )
                    );
                    newSetting.add(tempSettings.get(0));
                }

        );
        int selectedSites = solverParameters.getSelectedSites();
        int normalQuantity = solverParameters.getNormalQuantity();
        IntStream.range(bestSites, selectedSites).forEach(
                n -> {
                    List<Setting> tempSettings = new ArrayList<>();
                    IntStream.range(0, normalQuantity).forEach(
                            m -> tempSettings.add(iterationSettings.get(n).getNeighbour(movesNum))
                    );
                    Collections.sort(tempSettings,
                            (s1, s2) -> Double.compare(
                                    s1.getFitness(distanceWage, timeWage),
                                    s2.getFitness(distanceWage, timeWage)
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
        return iterationSettings;
    }

    public Setting bestSetting(List<Setting> iterationSettings, SolverParameters solverParameters) {
        double distanceWage = solverParameters.getDistanceWage();
        double timeWage = solverParameters.getTimeWage();

        Collections.sort(iterationSettings,
                (s1, s2) -> Double.compare(
                        s1.getFitness(distanceWage, timeWage),
                        s2.getFitness(distanceWage, timeWage)
                )
        );
        return iterationSettings.get(0);
    }

	public Setting solve(SettingFactory factory, SolverParameters solverParameters) {
        List<Setting> iterationSettings = initialSettings(factory, solverParameters);

        for(int i = 0; i < solverParameters.getIterations(); i++) { // TODO: second stop condition
            iterationSettings = solveIteration(iterationSettings, factory, solverParameters);
        }

        return bestSetting(iterationSettings, solverParameters);
    }
}
