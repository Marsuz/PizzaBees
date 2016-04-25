package app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class AppInput {
    private static final Logger logger = Logger.getLogger(AppInput.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private SavedState savedState;

    private AppInput(String pathToInputFile) {
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

    public static void main(String[] args) {
        logger.info("Starting app");
        if (args.length < 1) {
            logger.error("You must provide path to input file");
            System.exit(1);
        }
        final String pathToInputFile = args[0];
        logger.info(String.format("Will read input from file: %s", pathToInputFile));
        AppInput input = new AppInput(pathToInputFile);
    }

    private static String convertToJson(AppInput input) {
        Gson gson = new Gson();
        return gson.toJson(input);
    }


}
