import com.google.gson.Gson;
import model.Restaurant;

import java.util.List;

public class JsonSample {

    private static List<Restaurant> restaurants = null;

    public static void main(String[] args) {
        System.out.println(convertToJson());
    }

    private static String convertToJson(){
        Gson gson = new Gson();
        return gson.toJson(restaurants);
    }



}
