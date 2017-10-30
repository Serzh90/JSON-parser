import com.google.common.base.Charsets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.Record;
import util.RecordResult;
import util.Validator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sergejs Gamans on 26.10.17.
 */
public class JsonParserApplication {

    private final static Logger logger = LoggerFactory.getLogger(JsonParserApplication.class);
    private static Validator validator;
    private static String resultFileName;

    public static void main(String[] args) {

        if (args.length < 3) {
            logger.error("Arguments missing, please check README.md, exiting!");
            System.exit(-1);
        }

        String validationConfig = args[0];
        String fileName = args[1];
        resultFileName = args[2];

        List<Record> records = loadRecords(new File(fileName));

        try {
            validator = new Validator(new File(validationConfig)).init();
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("Error creating validator " + e);
            System.exit(-1);
        }

        List<RecordResult> results = new ArrayList<>(records.size());
        for (Record rec : records) {
            try {
                results.add(validator.validate(rec));
            } catch (Exception ex) {
                ex.printStackTrace();
                logger.error("Validation failed for  record with id: " + rec.getId(), ex);
            }
        }
        saveResult(results);
    }

    private static void saveResult(List<RecordResult> results) {
        try (Writer writer = new FileWriter(resultFileName)) {
            Gson gson = new GsonBuilder().create();
            gson.toJson(results, writer);
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error("Fatal error when saving validation results to file ", ex);
        }
        logger.info("Saved results as: " + resultFileName);
    }

    private static List<Record> loadRecords(File file) {
        List<String> linedResult;
        List<Record> records = null;
        try {
            linedResult = Files.readLines(file, Charsets.UTF_8);
            records = new ArrayList<>(linedResult.size());
            for (String line : linedResult) {
                Record result = new Gson().fromJson(line, Record.class);
                records.add(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return records;
    }

}
