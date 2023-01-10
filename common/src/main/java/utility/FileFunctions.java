package utility;

import constatnt.Constants;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.String.format;

@UtilityClass
public class FileFunctions {

    private static final Logger logger = Logger.getInstance();

    public static String convertFileContentsToString(File file) {
        return convertFileContentsToString(file.getAbsolutePath());
    }

    public static String convertFileContentsToString(String filePath) {
        String line = Constants.EMPTY;
        try {
            line = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (FileNotFoundException e) {
            logger.error(format("File was not found: %s", e.getMessage()));
        } catch (IOException e) {
            logger.error(format("%nIO exception occurred during the conversion of the file '%s': %s", filePath, e.getMessage()));
        }
        return line;
    }
}
