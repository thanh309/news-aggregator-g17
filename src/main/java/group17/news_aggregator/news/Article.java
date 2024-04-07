package group17.news_aggregator.news;

import com.opencsv.bean.CsvBindAndSplitByName;
import com.opencsv.bean.CsvBindByName;

import java.util.List;

public class Article implements News {
    @CsvBindByName
    private String link;
    @CsvBindByName
    private String websiteSource;
    @CsvBindByName
    private String summary;
    @CsvBindByName
    private String title;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = "\\|", writeDelimiter = "|")
    private List<String> content;
    @CsvBindByName
    private String creationDate;
    @CsvBindAndSplitByName(elementType = String.class, splitOn = "\\|", writeDelimiter = "|")
    private List<String> tags;
    @CsvBindByName
    private String author;
    @CsvBindByName
    private String category;

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

    @Override
    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String getWebsiteSource() {
        return websiteSource;
    }

    public void setWebsiteSource(String websiteSource) {
        this.websiteSource = websiteSource;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    @Override
    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
