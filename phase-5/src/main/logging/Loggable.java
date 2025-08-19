package main.logging;

/**
 * Things that can "log" - the assumption is that when the log(msg)
 * method is called, a message will be logged...somewhere.
 */
public interface Loggable {
    public void log(String msg);
}
