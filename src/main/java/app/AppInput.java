package app;

import com.google.gson.Gson;
import model.Order;
import model.Restaurant;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AppInput {
    private static final Logger logger = Logger.getLogger(AppInput.class);
    private List<Restaurant> restaurants = null;
    private int P;
    private List<Order> orders = null;

    public AppInput() {
        restaurants = new ArrayList<Restaurant>();
        restaurants.add(new Restaurant(10, 10, 5));
        restaurants.add(new Restaurant(20, 15, 10));
        P = 10;
        orders = new ArrayList<Order>();
        orders.add(new Order(20, 20, 5));
    }

    public static void main(String[] args) {
        logger.info("Starting app");
        AppInput input = new AppInput();
        System.out.println(convertToJson(input));
    }

    private static String convertToJson(AppInput input){
        Gson gson = new Gson();
        return gson.toJson(input);
    }



}
