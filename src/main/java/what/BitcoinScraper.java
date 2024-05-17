package what;

import com.opencsv.CSVWriter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class BitcoinScraper {

    public static List<CoinPrice> scrapeAll(String baseUrl) throws IOException {
        // initializing the list of Coin data objects
        List<CoinPrice> coins = new ArrayList<>();

        // downloading the target website with an HTTP GET request
        Document doc = Jsoup.connect(baseUrl).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/107.0.0.0 Safari/537.36").get();

        // selecting the table containing Bitcoin's historical data
        Element table = doc.selectFirst("table.svelte-ewueuo");
        if (table != null) {
            // iterating over rows in the table
            for (Element row : table.select("tbody tr")) {
                Elements cols = row.select("td");
                // checking if the row contains data
                if (cols.size() == 7) {
                    // extracting data from each column
                    String date = cols.get(0).text();
                    String open = cols.get(1).text();
                    String high = cols.get(2).text();
                    String low = cols.get(3).text();
                    String close = cols.get(4).text();
                    String adjClose = cols.get(5).text();
                    String volume = cols.get(6).text();

                    // creating a Coin object and adding it to the list
                    CoinPrice coin = new CoinPrice(date, open, high, low, close, adjClose, volume);
                    coins.add(coin);
                }
            }
        }

        return coins;
    }

    public static void main(String[] args) throws IOException {
        String[] coinCodes = {"BTC-USD", "ETH-USD", "QS", "WES", "EPD"};
        for (String coinCode: coinCodes){
            List<CoinPrice> coinPrices = scrapeAll(String.format("https://finance.yahoo.com/quote/%s/history/?period1=1557641369&period2=1715472000", coinCode));

            File csvFile = new File(String.format("src/main/java/coinPriceHistory/%s.csv", coinCode));

            try {
                FileWriter outputFile = new FileWriter(csvFile);

                CSVWriter writer = new CSVWriter(outputFile);

                String[] header = {"Date", "Open", "High", "Low", "Close", "Adj Close", "Volume"};
                writer.writeNext(header);

                for (CoinPrice coin : coinPrices) {
                    String[] data = {DateConverter.dateConverter(coin.getDate()), coin.getOpen().replace(",", ""), coin.getHigh().replace(",", ""), coin.getLow().replace(",", ""), coin.getClose().replace(",", ""), coin.getAdjClose().replace(",", ""), coin.getVolume().replace(",", "")};
                    writer.writeNext(data);

                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}



