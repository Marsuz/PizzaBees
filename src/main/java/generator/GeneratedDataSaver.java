package generator;

import app.savedSate.SavedState;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import generator.generator.Generator;
import generator.generator.impl.ClosePointsGenerator;
import generator.generator.impl.RectangleFilledPointsGenerator;
import generator.generator.impl.SquarePlacedGenerator;
import org.apache.log4j.Logger;

import java.io.*;

/**
 * Created by Marcin on 2016-05-04.
 */
public class GeneratedDataSaver {

    private static final Logger LOGGER = Logger.getLogger(GeneratedDataSaver.class);
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DEFAULT_FILEPATH_CLOSE_POINTS = "src\\test\\resources\\generated\\closePointsInput.json";
    private static final String DEFAULT_FILEPATH_SQUARE_PLACED = "src\\test\\resources\\generated\\squarePlacedInput.json";
    private static final String DEFAULT_FILEPATH_RECTANGE_FILLED = "src\\test\\resources\\generated\\rectangleFilledInput.json";
    private static String DEFAULT_FILEPATH = DEFAULT_FILEPATH_CLOSE_POINTS;
    private Generator generator;

    public void generateAndSaveData(String filePath, int[] params) {


        generator = null;
        switch (params.length) {
            case 5:
                generator = new SquarePlacedGenerator(params[0], params[1], params[2], params[3], params[4]);
                DEFAULT_FILEPATH = DEFAULT_FILEPATH_SQUARE_PLACED;
                break;
            case 7:
                generator = new RectangleFilledPointsGenerator(params[0], params[1], params[2], params[3], params[4], params[5], params[6]);
                DEFAULT_FILEPATH = DEFAULT_FILEPATH_RECTANGE_FILLED;
                break;
            case 8:
                generator = new ClosePointsGenerator(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
                DEFAULT_FILEPATH = DEFAULT_FILEPATH_CLOSE_POINTS;
                break;
            default:
                LOGGER.info("Wrong numbers of parameters for generator");
                System.exit(1);
        }
        String realFilepath = filePath == null ? DEFAULT_FILEPATH : filePath;
        File file = new File(realFilepath);
        file.delete();
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
        saver.generateAndSaveData(null, new int[]{10, 20, 5, 5, 5, 5, 1, 1});
        saver.generateAndSaveData(null, new int[]{10, 3, 5, 5, 10, 5, 5});
        saver.generateAndSaveData(null, new int[]{5, 5, 5, 5, 20});
    }

}
