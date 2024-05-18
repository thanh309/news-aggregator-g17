import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;


// could have been easily done with jsoup tho

public class ApiCaller {
    public static Map<String, List<Double>> parseJson(String jsonString) throws JSONException {
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

    public static void main(String[] args) throws IOException, InterruptedException, JSONException {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest
                .newBuilder()
                .GET()
                .uri(URI.create("http://localhost:5000/price_prediction"))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            var x = parseJson(response.body());
            System.out.println();
        } else {
            System.out.println("Error: API request failed with status code: " + response.statusCode());
        }
    }
}