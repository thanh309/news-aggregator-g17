package group17.news_aggregator.news;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class News {
    @CsvBindByName
    protected String type;
    @CsvBindByName
    protected String link;
    @CsvBindByName
    protected String websiteSource;
    @CsvBindByName
    protected String summary;
    @CsvBindByName
    protected String title;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = "\\|", writeDelimiter = "|")
    protected List<String> content;
    @CsvBindByName
    protected String creationDate;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = "\\|", writeDelimiter = "|")
    protected List<String> tags;
    @CsvBindByName
    protected String author;
    @CsvBindByName
    protected String category;

    public static int MaxOrder = 0;

    // this is not the order news is add to total all news,
    // it is just the Order of news in difference display turn
    private int Order;


    public News(){
        MaxOrder += 1;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getWebsiteSource() {
        return websiteSource;
    }

    public void setWebsiteSource(String websiteSource) {
        this.websiteSource = websiteSource;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public int getOrder() {return Order;}

    public void setOrder(int order) {Order = order;}
}
