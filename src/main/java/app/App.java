package app;

import app.savedSate.SavedState;
import com.google.common.base.Preconditions;
import model.Courier;
import model.Order;
import model.Restaurant;
import model.Setting;
import org.apache.log4j.Logger;
import solver.Solver;
import solver.setting.SettingFactory;
import solver.setting.random.RandomSettingFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class App {
    private static final Logger logger = Logger.getLogger(App.class);


    public static void main(String[] args) throws IOException {

        if(args.length<1){
            logger.error("Please provide path for output file as program parameter!");
            System.exit(1);
        }
        String outputPath = args[0];


        ClassLoader classLoader = App.class.getClassLoader();

        final String resourceName = "inputSample.json";
        final URL resource = classLoader.getResource(resourceName);
        Preconditions.checkNotNull(resource, String.format("Resource with name %s not available", resourceName));

        AppInput appInput = new AppInput(resource.getPath());
        SavedState savedState = appInput.getSavedState();
        Restaurant.P = 10;
        Courier.velocity = 10; //TODO
        int restaurantsNumber = 2;
        int ordersNumber = 10;
        int couriersNumber = 5;
        List<Restaurant> restaurants = new ArrayList<>();
        List<Order> orders = new ArrayList<>();

        for(int i = 0; i < restaurantsNumber; i++)
            restaurants.add(i, new Restaurant(i, i, couriersNumber));

        for(int i = 0; i < ordersNumber; i++)
            orders.add(i, new Order(i, i , i));


        logger.info("Restaurants: " +restaurants);
        logger.info("Orders: " + orders);

        Solver solver = new Solver();
        SettingFactory settingFactory = new RandomSettingFactory(restaurants, orders);


        double distanceWage = 1;
        double timeWage = 1;
        int scouts = 100;
        int selectedSites = 10;
        int bestSites = 5;
        int eliteQuantity = 8;
        int normalQuantity = 3;
        int iterations = 25;
        int moves = 5;

       Setting result = solver.solve(settingFactory,
                distanceWage,
                timeWage,
                scouts,
                selectedSites,
                bestSites,
                eliteQuantity,
                normalQuantity,
                iterations,
                moves);

        ResultsDumper.dumpResultsToFile(outputPath, result);
    }

}
