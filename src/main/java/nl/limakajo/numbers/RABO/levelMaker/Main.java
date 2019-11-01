package nl.limakajo.numbers.RABO.levelMaker;

import nl.limakajo.numbers.RABO.API.entity.Level;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    private static LevelCollection levelCollection;

    public static void main(String[] args) {
        generateLevels();
        postLevels();
    }

    /**
     * Post levels through the API
     */
    private static void postLevels() {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("http://localhost:8080/api/levels");
            for (int i = 0; i < 10; i++) {
                Level level = levelCollection.getLevel(i);
                JSONObject json = new JSONObject()
                        .put("numbers", level.getNumbers())
                        .put("averageTime", level.getAverageTime())
                        .put("timesPlayed", 1);
                StringEntity params = new StringEntity(json.toString());
                request.addHeader("content-type", "application/json");
                request.setEntity(params);
                httpClient.execute(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Generate list of Levels
     */
    private static void generateLevels() {
        levelCollection = new LevelCollection();
        for (int i = 0; i < 10; i++) {
            levelCollection.addValidLevel();
        }
    }
}
