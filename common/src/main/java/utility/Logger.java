package utility;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import java.io.File;

import static constatnt.Constants.NEW_LINE;
import static java.util.Objects.isNull;

public final class Logger {

    static {
        LOG4J = org.apache.log4j.Logger.getLogger(Logger.class);
    }

    private static final org.apache.log4j.Logger LOG4J;
    private static final ThreadLocal<Logger> LOGGER_HOLDER = new ThreadLocal<>();

    private File logFile;

    private Logger() {
    }

    public void initLogFile(String logName) {
        FileAppender fa = new FileAppender();
        logFile = new File(FileHelper.getLogFilePath(logName));
        fa.setFile(logFile.getAbsolutePath());
        fa.setLayout(new PatternLayout("%d %-5p - %m%n"));
        fa.setThreshold(Level.INFO);
        fa.activateOptions();
        LOG4J.addAppender(fa);
    }

    public static Logger getInstance() {
        if (isNull(LOGGER_HOLDER.get())) {
            LOGGER_HOLDER.set(new Logger());
        }
        return LOGGER_HOLDER.get();
    }

    public void info(Object message) {
        LOG4J.info(message);
    }

    public void infoLn(Object message) {
        info(NEW_LINE + message);
    }

    public void error(String message) {
        LOG4J.error(message);
    }

    public void fatal(String message, Throwable ex) {
        LOG4J.fatal(message + ": " + ex.toString());
    }

    public File getLogFile() {
        return logFile;
    }

    public void unload() {
        LOGGER_HOLDER.remove();
    }
}
