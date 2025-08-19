package main.exceptions.checked;

public class GameDataSourceException extends RuntimeException {
    public GameDataSourceException(String message) {
        super(message);
    }

    public GameDataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
