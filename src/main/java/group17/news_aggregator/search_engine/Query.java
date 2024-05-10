package group17.news_aggregator.search_engine;

public class Query {

    private String searchQuery;
    private String author;
    private String category;
    private String tag;
    private long startDateTime = 0;
    private long endDateTime = Long.MAX_VALUE;

    public String getSearchQuery() {
        return searchQuery;
    }

    public String getAuthor() {
        return author;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public long getStartDateTime() {
        return startDateTime;
    }

    public long getEndDateTime() {
        return endDateTime;
    }

    public Query(String searchQuery, String author, String category, String tag, long startDateTime, long endDateTime) {
        this.searchQuery = searchQuery.toLowerCase().trim();
        this.author = author.toLowerCase().trim();
        this.category = category.toLowerCase().trim();
        this.tag = tag.trim();
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public Query(String searchQuery, String author, String category, String tag) {
        this.searchQuery = searchQuery.toLowerCase().trim();
        this.author = author.toLowerCase().trim();
        this.category = category.toLowerCase().trim();
        this.tag = tag.trim();
    }
}
