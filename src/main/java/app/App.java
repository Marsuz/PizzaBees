package app;

import model.Courier;
import model.Order;
import model.Restaurant;
import solver.Solver;
import solver.setting.SettingFactory;
import solver.setting.random.RandomSettingFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2016-04-19.
 */
public class App {

    public static void main(String[] args) {

        ClassLoader classLoader = App.class.getClassLoader();


//        AppInput appInput = new AppInput(classLoader.getResource("inputSample.json").getPath());
//        SavedState savedState = appInput.getSavedState();
        Restaurant.P = 10;
        Courier.velocity = 10; //TODO
        int reastaurantsNumber = 2;
        int ordersNumber = 10;
        int couriersNumber = 5;
        List<Restaurant> restaurants = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for(int i = 0; i < reastaurantsNumber; i++)
            restaurants.add(i, new Restaurant(i, i, couriersNumber));

        for(int i = 0; i < ordersNumber; i++)
            orders.add(i, new Order(i, i , i));


        System.out.println(restaurants);
        System.out.println(orders);
//        System.exit(0);

        Solver solver = new Solver();
        SettingFactory settingFactory = new RandomSettingFactory(restaurants, orders);


        double distanceWage = 1;
        double timeWage = 1;
        int scouts = 100;
        int selectedSites = 10;
        int bestSites = 5;
        int eliteQuantity = 8;
        int normalQuantity = 3;
        int iterations = 1000;
        int moves = 5;

        System.out.println(solver.solve(settingFactory,
                distanceWage,
                timeWage,
                scouts,
                selectedSites,
                bestSites,
                eliteQuantity,
                normalQuantity,
                iterations,
                moves
        ));
    }


}
