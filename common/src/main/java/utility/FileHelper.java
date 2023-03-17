package utility;

import lombok.experimental.UtilityClass;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.lang.System.getProperty;
import static java.nio.file.Paths.get;
import static java.util.Objects.nonNull;

@UtilityClass
public class FileHelper {
    private static final String TEST_RESOURCES = "/src/test/resources/";
    private static final String USER_DIR = "user.dir";
    private static final String PROJECT_BASEDIR = "project.basedir";

    public static String getLogFilePath(String fileName) {
        return Paths.get(getLogsDirPath(), fileName).toString();
    }

    public static String getLogsDirPath() {
        return Paths.get(getAbsoluteTargetPath(), "logs").toString();
    }

    public static String getAbsoluteTargetPath() {
        return Paths.get(getProjectBaseDir(), "target").toString();
    }

    public static String getProjectBaseDir() {
        return nonNull(getProperty(USER_DIR)) ? getProperty(USER_DIR) : getProperty(PROJECT_BASEDIR);
    }

    public static File getGraphQlQueryByName(String queryName) {
        try {
            return get(FileHelper.class.getClassLoader().getResource("graphql/" + queryName).toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static File getTestResourceFileByName(String fileName) {
        return get(getProjectBaseDir(), TEST_RESOURCES + fileName).toFile();
    }

    public static Path getTestResourcePathToFileByName(String fileName) {
        return get(getProjectBaseDir(), TEST_RESOURCES + fileName);
    }

    public static File getMainResourceFileByName(String fileName) {
        try {
            return get(FileHelper.class.getClassLoader().getResource(fileName).toURI()).toFile();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
