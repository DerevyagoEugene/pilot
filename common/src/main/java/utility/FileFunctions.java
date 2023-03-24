package utility;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import static java.lang.String.format;
import static utility.FileHelper.getAbsoluteTargetPath;
import static utility.FileHelper.getTestResourcePathToFileByName;

@UtilityClass
public class FileFunctions {

    private static final Logger logger = Logger.getInstance();

    public static String convertFileContentsToString(File file) {
        return convertFileContentsToString(file.getAbsolutePath());
    }

    public static String convertFileContentsToString(String filePath) {
        String line;
        try {
            line = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (FileNotFoundException e) {
            logger.error(format("File was not found: %s", e.getMessage()));
            throw new IllegalStateException(e);
        } catch (IOException e) {
            logger.error(format("%nIO exception occurred during the conversion of the file '%s': %s", filePath, e.getMessage()));
            throw new IllegalStateException(e);
        }
        return line;
    }

    public static void copyFileTestResourceToTarget(String source, String target) throws IOException {
        Path sourcePath = getTestResourcePathToFileByName(source);
        Path targetPath = Paths.get(getAbsoluteTargetPath(), target);

        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
