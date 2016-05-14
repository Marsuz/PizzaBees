package app.savedState;

import app.savedSate.SavedState;
import app.savedSate.SavedStateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Courier;
import model.Order;
import model.Restaurant;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import static org.assertj.core.api.Assertions.assertThat;

public class SavedStateDeserializerTest {
	private File jsonFile;
	private int previousValueOfCourierVelocity;

	@Before
	public void setUp() throws Exception {
		previousValueOfCourierVelocity = Courier.velocity;
		final String jsonTestName = "testSample.json";
		final String resourcePath = this.getClass().getClassLoader().getResource(jsonTestName).toURI().getPath();
		jsonFile = new File(String.valueOf(resourcePath));
	}

	@After
	public void tearDown() throws Exception {
		Courier.velocity = previousValueOfCourierVelocity;
	}

	@Test
	public void shouldCorrectlyDeserializeJsonFile() throws Exception {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(SavedState.class, new SavedStateDeserializer());
		Gson gson = gsonBuilder.create();

		SavedState savedState = gson.fromJson(FileUtils.readFileToString(jsonFile, "UTF-8"), SavedState.class);

		Order order = new Order(20, 20, 5);
		Restaurant restaurant0 = new Restaurant(10, 10, 5);
		Restaurant restaurant1 = new Restaurant(20, 15, 10);
		assertThat(savedState.getP()).isEqualTo(10);
		assertThat(savedState.getOrders())
				.hasSize(1)
				.usingFieldByFieldElementComparator()
				.contains(order);
		assertThat(savedState.getRestaurants()).hasSize(2)
				.usingElementComparatorIgnoringFields("couriers")
				.containsExactly(restaurant0,restaurant1);
		assertThat(savedState.getRestaurants().get(0).getCouriers()).hasSize(5);
		assertThat(savedState.getRestaurants().get(1).getCouriers()).hasSize(10);
	}
}