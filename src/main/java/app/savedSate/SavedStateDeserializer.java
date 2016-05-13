package app.savedSate;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import model.Courier;
import model.Order;
import model.Restaurant;
import org.apache.log4j.Logger;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class SavedStateDeserializer implements JsonDeserializer<SavedState> {

	private static final Logger logger = Logger.getLogger(SavedStateDeserializer.class);

	@Override
	public SavedState deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonContext)
			throws JsonParseException {
		JsonObject mainJsonObject = jsonElement.getAsJsonObject();

		List<Restaurant> restaurantList = new LinkedList<>();
		JsonArray restaurants = mainJsonObject.getAsJsonArray("restaurants");
		Preconditions.checkNotNull(restaurants,"No restaurants present in json!");
		for (JsonElement restaurant : restaurants) {
			restaurantList.add(processOneRestaurant(restaurant));
		}

		final JsonPrimitive pJsonPrimitive = mainJsonObject.getAsJsonPrimitive("P");
		Preconditions.checkNotNull(pJsonPrimitive,"No P parameter present in json!");
		int p = pJsonPrimitive.getAsInt();

		List<Order> orderList = new LinkedList<>();
		JsonArray orders = mainJsonObject.getAsJsonArray("orders");
		Preconditions.checkNotNull(orders,"No orders present in json!");
		for (JsonElement order : orders) {
			orderList.add(processOneOrder(order));
		}

		final JsonPrimitive velocityJson = mainJsonObject.getAsJsonPrimitive("V");
		Preconditions.checkNotNull(velocityJson, "No velocity present in json!");
		int velocity = velocityJson.getAsInt();
		logger.info(String.format("Setting velocity for class %s as %d",Courier.class,velocity));
		Courier.velocity = velocity;

		return new SavedState(restaurantList,p, velocity, orderList);
	}

	private Order processOneOrder(JsonElement jsonOrderElement) {
		JsonObject orderJsonObject = jsonOrderElement.getAsJsonObject();
		int quantity = orderJsonObject.getAsJsonPrimitive("quantity").getAsInt();

		JsonObject destinationObject = orderJsonObject.getAsJsonObject("destination");
		final int x = destinationObject.getAsJsonPrimitive("x").getAsInt();
		final int y = destinationObject.getAsJsonPrimitive("y").getAsInt();

		return new Order(x,y,quantity);
	}

	private Restaurant processOneRestaurant(JsonElement restaurant) {
		JsonObject restaurantJsonObject = restaurant.getAsJsonObject();
		int numberOfCouriers = restaurantJsonObject.getAsJsonPrimitive("courierNumber").getAsInt();
		int x = restaurantJsonObject.getAsJsonPrimitive("x").getAsInt();
		int y = restaurantJsonObject.getAsJsonPrimitive("y").getAsInt();
		return new Restaurant(x, y, numberOfCouriers);
	}
}
