package group17.news_aggregator.gui.utils;

import group17.news_aggregator.exception.RequestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class ApiCaller {
    private Map<String, List<Double>> parseJson(String jsonString) throws JSONException {
        Map<String, List<Double>> result = new HashMap<>();

        JSONObject jsonObject = new JSONObject(jsonString);

        for (Iterator it = jsonObject.keys(); it.hasNext(); ) {
            String key = (String) it.next();
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            List<Double> list = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                list.add(jsonArray.getDouble(i));
            }

            result.put(key, list);
        }

        return result;
    }

    public Map<String, List<Double>> getResponse() throws IOException, InterruptedException, RequestException, JSONException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create("http://localhost:5000/price_prediction"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return parseJson(response.body());
        } else {
            throw new RequestException(response.statusCode());
        }
    }

    public static void main(String[] args) {
        ApiCaller apiCaller = new ApiCaller();
        try {
            Map<String, List<Double>> response = apiCaller.getResponse();
            for (Map.Entry<String, List<Double>> keyPricePair: response.entrySet()) {
                System.out.println(keyPricePair.getKey() + ": " + keyPricePair.getValue());
            }
        } catch (RequestException e) {
            // show nothing here; or maybe a warning in GUI
            //...
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }
}
