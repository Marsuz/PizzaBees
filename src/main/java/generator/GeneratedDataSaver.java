package generator;

import app.savedSate.SavedState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import generator.generator.Generator;
import generator.generator.ClosePointsGenerator;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Marcin on 2016-05-04.
 */
public class GeneratedDataSaver {

    private static final Logger LOGGER = Logger.getLogger(GeneratedDataSaver.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DEFAULT_FILEPATH = "src\\test\\resources\\generated\\input.json";
    private Generator generator;

    public void generateAndSaveData(String filePath, int[] params) {

        if (params.length != 8) {
            LOGGER.info("Please insert: <restaurants number> <orders number>" +
                    "<p> <courier velocity> <max couriers number> <max order quantity> <restaurant closeness factor> <order closeness factor>");
            System.exit(1);
        }
        String realFilepath = filePath == null ? DEFAULT_FILEPATH : filePath;
        File file = new File(realFilepath);
        file.delete();
        Generator generator = new ClosePointsGenerator(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
        SavedState generatedData = new SavedState(generator.getRestaurants(), generator.getP(), generator.getV(), generator.getOrders());
        String dataInJson = gson.toJson(generatedData);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(realFilepath))) {
            bw.write(dataInJson);
        } catch (IOException ex) {
            LOGGER.error("Problem occurred processing generated data file", ex);
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        GeneratedDataSaver saver = new GeneratedDataSaver();
        saver.generateAndSaveData(null, new int[]{10, 20, 5, 5, 5, 7, 1, 1});
    }

}
