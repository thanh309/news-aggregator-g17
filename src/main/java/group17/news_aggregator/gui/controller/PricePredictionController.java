package group17.news_aggregator.gui.controller;

import group17.news_aggregator.exception.RequestException;
import group17.news_aggregator.gui.utils.ApiCaller;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PricePredictionController {

    public List<List<String>> getFormattedResponse() throws IOException, InterruptedException, RequestException, JSONException {
        ApiCaller apiCaller = new ApiCaller();
        Map<String, List<Double>> response = apiCaller.getResponse();
        List<List<String>> formattedResponse = new ArrayList<>();

        for (Map.Entry<String, List<Double>> entry : response.entrySet()) {
            String key = entry.getKey();
            List<Double> prices = entry.getValue();
            List<String> formattedPrices = formatPrices(key, prices);
            formattedResponse.add(formattedPrices);
        }

        return formattedResponse;
    }

    private List<String> formatPrices(String key, List<Double> prices) {
        if (prices.size() < 4) {
            throw new IllegalArgumentException("Not enough price data to calculate changes.");
        }

        List<String> formattedPrices = new ArrayList<>();
        formattedPrices.add(key);
        double firstElement = prices.get(0);
        formattedPrices.add(String.format("%.1f", firstElement));
        formattedPrices.add("0%");

        for (int i = 1; i <= 3; i++) {
            double percentageChange = ((prices.get(i) - firstElement) / firstElement) * 100;
            formattedPrices.add(String.format("%.1f",prices.get(i)));
            formattedPrices.add(String.format("%+.2f%%", percentageChange));
        }


        return formattedPrices;
    }
    public static void main(String[] args) {
        PricePredictionController controller = new PricePredictionController();
        try {
            List<List<String>> response = controller.getFormattedResponse();
            for (List<String> formattedList : response) {
                System.out.println("Key: " + formattedList.get(0));
                formattedList.subList(1, formattedList.size()).forEach(System.out::println);
            }
        } catch (Exception e) {
            System.err.println("Failed to fetch or format response: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
