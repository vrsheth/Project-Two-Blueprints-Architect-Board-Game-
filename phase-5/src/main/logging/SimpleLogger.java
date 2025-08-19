package main.logging;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class providing a crude way to log warnings to the log file
 * logs/app.log,
 * using Java's java.util.logging package.
 * 
 */
public class SimpleLogger implements Loggable {

    private static final String LOG_PATH = "logs/app.log";
    private final Logger logger;
    private ArrayList<String> linesLogged;

    public SimpleLogger() {
        logger = Logger.getLogger("simple.logger");
        linesLogged = new ArrayList<>();

        FileHandler fileLogHandler = null;
        try {
            fileLogHandler = new FileHandler(LOG_PATH, true); // true means append to file
            fileLogHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileLogHandler);
            logger.setUseParentHandlers(false); // Stop logging to console if file log working.
        } catch (IOException ex) {
            // If we can't log to a file, log this issue to console.
            logger.severe("Unable to log to the application log file! Falling back to console-only logging.");
        }
    }

    /**
     * Writes a given message as a warning to logs/app.log.
     * 
     * If something goes wrong writing to that log, a severe-level log
     * message wil be written to the console instead.
     * 
     * The log file is always appended to; this log has the potential
     * to get very large, so keep an eye on it and clear it out
     * when necessary.
     * 
     * @param msg the message to log
     */
    public void log(String msg) {
        logger.log(Level.WARNING, msg);
        linesLogged.add(msg);
    }

    /**
     * Returns a list of messages logged by this Logger.
     * 
     * @return a list of messages that have been logged
     */
    public ArrayList<String> getMessages() {
        return new ArrayList<String>(linesLogged);
    }
}
