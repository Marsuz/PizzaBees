package app;

import app.savedSate.SavedState;
import app.savedSate.SavedStateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class AppInput {
	private static final Logger logger = Logger.getLogger(AppInput.class);
	private SavedState savedState;

	private static Gson stateGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting().registerTypeAdapter(SavedState.class,new SavedStateDeserializer());
		return gsonBuilder.create();
	}

	public static SavedState stringToState(String json) throws IOException {
		return stateGson().fromJson(json, SavedState.class);
	}

	public static String stateToString(SavedState state) {
		return stateGson().toJson(state);
	}

	public AppInput(String pathToInputFile) {
		File inputFile = new File(pathToInputFile);
		if (!inputFile.exists()) {
			logger.error(String.format("File with path: %s not exist", pathToInputFile));
			System.exit(1);
		}
		if (!inputFile.isFile()) {
			logger.error(String.format("File with path: %s is not a file", pathToInputFile));
			System.exit(1);
		}
		try {
			String jsonContentOfFile = FileUtils.readFileToString(inputFile, Charset.forName("UTF-8"));
			savedState = stringToState(jsonContentOfFile);
			logger.debug(String.format("Resolved app state:\n %s", stateToString(savedState)));
		} catch (IOException e) {
			logger.error(String.format("Error while reading from file: %s", inputFile.getAbsolutePath()), e);
		}
	}

	public SavedState getSavedState() {
		return savedState;
	}


	public static void main(String[] args) {
		AppInput appInput = new AppInput("/home/marcin/Development/Studies/PizzaBees/src/main/resources/inputSample.json");
		System.out.println();
	}

}
