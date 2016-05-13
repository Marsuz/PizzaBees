package app;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.Setting;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

class ResultsDumper {
	private final static Logger logger = Logger.getLogger(ResultsDumper.class);
	
	static void dumpResultsToFile(String outputPath, Setting result) throws IOException {
		Preconditions.checkNotNull(outputPath,"Output path must nor be null!");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String outputResultJson = gson.toJson(result);
		logger.debug(String.format("Result: \n %s", outputResultJson));
		File file = new File(outputPath);
		boolean ifCreated = file.createNewFile();
		if(!ifCreated){
			logger.warn(String.format("File was not created for path :%s",outputPath));
		}
		logger.info(String.format("Writing output data to file: %s",file.getAbsolutePath()));
		FileUtils.writeStringToFile(file,outputResultJson,"UTF-8");
	}
}
