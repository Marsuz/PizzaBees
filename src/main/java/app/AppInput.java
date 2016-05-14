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

	public AppInput(String pathToInputFile) {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.setPrettyPrinting().registerTypeAdapter(SavedState.class,new SavedStateDeserializer());
		Gson gson = gsonBuilder.create();

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
			savedState = gson.fromJson(jsonContentOfFile, SavedState.class);
			logger.debug(String.format("Resolved app state:\n %s", gson.toJson(savedState)));
		} catch (IOException e) {
			logger.error(String.format("Error while reading from file: %s", inputFile.getAbsolutePath()), e);
		}
	}

	public SavedState getSavedState() {
		return savedState;
	}


	public static void main(String[] args) {
		AppInput appInput = new AppInput("C:\\Users\\Admin\\Documents\\GitHub\\PizzaBess\\src\\main\\resources\\inputSample.json");

		System.out.println();
	}

}
