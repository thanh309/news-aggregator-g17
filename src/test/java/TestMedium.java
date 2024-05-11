import group17.news_aggregator.exception.EmptyContentException;
import group17.news_aggregator.news.News;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

public class TestMedium {
    public static void main(String[] args) throws IOException {
        String url = "https://medium.com/enrique-dans/surprise-bitcoin-is-back-above-50-000-f943a2fa9de1";
        News news = new News();


        String[] temp = url.split("/");
        String readUrl = "https://readmedium.com/" + temp[temp.length - 1];

        news.setLink(readUrl);
        news.setWebsiteSource("Medium");

        Document readDoc = Jsoup
                .connect(readUrl)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        readDoc.select("div[class=\"link-block\"]").remove();

        Document doc = Jsoup
                .connect(url)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:122.0) Gecko/20100101 Firefox/122.0")
                .get();

        // get tags
        try {
            Elements tags = Objects.requireNonNull(
                    readDoc
                            .body()
                            .select("div[class=\" text-sm font-bold mt-12 space-y-[4px]\"]")
                            .first()
            ).children();
            List<String> tagsList = tags.eachText();
            news.setTags(tagsList);
        } catch (NoSuchElementException | NullPointerException ignored) {
        }

        //get author
        String author = doc
                .head()
                .select("meta[name=\"author\"]")
                .attr("content");
        if (author.isEmpty()) {
            author = "Guest";
        }
        news.setAuthor(author);

        //get summary (description)
        String summary = readDoc
                .select("div[class=\"prose break-words dark:prose-invert prose-p:leading-relaxed " +
                        "prose-pre:p-0 text-[var(--theme-text)]\"]")
                .getFirst()
                .children()
                .get(1)
                .text();
        news.setSummary(summary);

        // get title
        String title = doc
                .head()
                .select("meta[property=\"og:title\"]")
                .attr("content");
        news.setTitle(title);

        // get content
        try {
            List<String> content = readDoc
                    .select("article")
                    .getFirst()
                    .children()
                    .eachText();
            news.setContent(content);
        } catch (NoSuchElementException e) {
            throw new EmptyContentException(news.getLink());
        }

        // get creation date
        String datetime = doc
                .head()
                .select("meta[property=\"article:published_time\"]")
                .attr("content");

        if (datetime.isBlank()) {
            news.setCreationDate(0);
        } else {
            news.setCreationDate(Instant.parse(datetime).toEpochMilli());
        }


        System.out.println("done");
    }
}
