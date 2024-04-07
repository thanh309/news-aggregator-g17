package group17.news_aggregator.news;

import java.util.List;

public interface News_old {
    String getLink();

    String getWebsiteSource();

    String getTitle();

    List<String> getContent();

    String getCreationDate();

}