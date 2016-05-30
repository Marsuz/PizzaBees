package app;

import app.savedSate.SavedState;
import com.google.common.base.Preconditions;
import model.Courier;
import model.Order;
import model.Restaurant;
import model.Setting;
import org.apache.log4j.Logger;
import solver.Solver;
import solver.SolverParameters;
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
        //String outputPath = args[0];
        String outputPath = "./outputRestaurantsInRectangle1.json";


        ClassLoader classLoader = App.class.getClassLoader();

        final String resourceName = "RestaurantsInRectangle1.json";
        final URL resource = classLoader.getResource(resourceName);
        Preconditions.checkNotNull(resource, String.format("Resource with name %s not available", resourceName));

        AppInput appInput = new AppInput(resource.getPath());
        SavedState savedState = appInput.getSavedState();
        Restaurant.P = savedState.getP();
        Courier.velocity = savedState.getV();
        List<Restaurant> restaurants = savedState.getRestaurants();
        List<Order> orders = savedState.getOrders();

        logger.info("Restaurants: " +restaurants);
        logger.info("Orders: " + orders);

        Solver solver = new Solver();
        SettingFactory settingFactory = new RandomSettingFactory(restaurants, orders);

        //FIXME builder pattern maybe?
        SolverParameters solverParameters = new SolverParameters();
        solverParameters.setDistanceWage(1);
        solverParameters.setScouts(21);
        solverParameters.setTimeWage(1d);
        solverParameters.setSelectedSites(6);
        solverParameters.setBestSites(2);
        solverParameters.setEliteQuantity(2);
        solverParameters.setNormalQuantity(4);
        solverParameters.setIterations(1000);
        solverParameters.setMoves(2);

        Setting result = solver.solve(settingFactory, solverParameters);

        ResultsDumper.dumpResultsToFile(outputPath, result);
    }

}
