package group17.news_aggregator.exception;

public class RequestException extends Exception {
    public RequestException(int code) {
        super("Error: API request failed with status code " + code);
    }
}
