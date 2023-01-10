package utility;

import org.apache.log4j.Appender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.LF;

public final class Logger {

    static {
        LOG4J = org.apache.log4j.Logger.getLogger(Logger.class);
        LOG_STEPS = true;
    }

    private static final org.apache.log4j.Logger LOG4J;
    private static final ThreadLocal<Logger> LOGGER_HOLDER = new ThreadLocal<>();
    private static final boolean LOG_STEPS;

    private File logFile;
    private FileAppender fa;

    private Logger() {
    }

    public void initLogFile(String logName) {
        fa = new FileAppender();
        logFile = new File(FileHelper.getLogFilePath(logName));
        fa.setFile(logFile.getAbsolutePath());
        fa.setLayout(new PatternLayout("%d %-5p - %m%n"));
        fa.setThreshold(Level.INFO);
        fa.activateOptions();
        LOG4J.addAppender(fa);
    }

    public void removeAppender(Appender appender) {
        LOG4J.removeAppender(appender.getName());
    }

    public FileAppender getFileAppender() {
        return fa;
    }

    public static Logger getInstance() {
        if (isNull(LOGGER_HOLDER.get())) {
            LOGGER_HOLDER.set(new Logger());
        }
        return LOGGER_HOLDER.get();
    }

    public void debug(String message) {
        LOG4J.debug(message);
    }

    public void debug(Object message) {
        LOG4J.debug(message);
    }

    public void debug(Object message, Throwable throwable) {
        LOG4J.debug(message, throwable);
    }

    public void info(Object message, Throwable throwable) {
        LOG4J.info(message, throwable);
    }

    public void info(Object message) {
        LOG4J.info(message);
    }

    public void warn(String message) {
        LOG4J.warn(message);
    }

    public void error(String message) {
        LOG4J.error(message);
    }

    public void error(Throwable ex) {
        LOG4J.error(getStackTraceMessage(ex));
    }

    public static String getStackTraceMessage(Throwable ex) {
        if (nonNull(ex)) {
            return LF + ex.getMessage() + "\n\nStacktrace:\n" + Arrays
                    .stream(ex.getStackTrace())
                    .map(StackTraceElement::toString)
                    .collect(Collectors.joining(LF));
        }
        return EMPTY;
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
