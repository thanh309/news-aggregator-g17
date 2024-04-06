package group17.news_aggregator.exception;

import java.io.IOException;

public class EmptyContentException extends IOException {
    private final String url;

    public EmptyContentException(String url) {
        super("Empty content, URL = [" + url + "]");
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
