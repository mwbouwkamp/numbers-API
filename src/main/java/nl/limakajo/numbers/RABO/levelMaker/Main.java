package nl.limakajo.numbers.RABO.levelMaker;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import nl.limakajo.numbers.RABO.API.entity.Level;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static LevelCollection levelCollection;
    private static CredentialsProvider credsProvider;

    public static void main(String[] args) {
        credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("localhost", 8080),
                new UsernamePasswordCredentials("admin", "admin"));
        deleteExistingLevels();
        LevelCollection levelCollection = generateLevels();
        postLevels(levelCollection.getLevels());
    }

    /**
     * Deletes the existing Levels from the database
     */
    private static void deleteExistingLevels() {
        List<Level> levels = getLevels();
        for (Level level: levels) {
            deleteLevel(level);
        }
    }

    /**
     * Deletes a single Level through the API
     *
     * @param level     Level to delete
     */
    private static void deleteLevel(Level level) {
        try {
            CloseableHttpResponse response = HttpClientBuilder
                    .create()
                    .setDefaultCredentialsProvider(credsProvider)
                    .build()
                    .execute(new HttpDelete("http://localhost:8080/api/levels/" + level.getNumbers()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get levels from API
     * @return      List of Levels
     */
    private static List<Level> getLevels() {
        try {
            CloseableHttpResponse response = HttpClientBuilder
                    .create()
                    .setDefaultCredentialsProvider(credsProvider)
                    .build()
                    .execute(new HttpGet("http://localhost:8080/api/levels"));
            HttpEntity entity = response.getEntity();
            InputStream inputStream = entity.getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String jsonString = bufferedReader.readLine();
            return new ObjectMapper().readValue(jsonString, new TypeReference<List<Level>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * Post levels through the API
     */
    private static void postLevels(List<Level> levels) {
        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultCredentialsProvider(credsProvider)
                .build();
        HttpPost request = new HttpPost("http://localhost:8080/api/levels");
        try {
            for (Level level: levels) {
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
    private static LevelCollection generateLevels() {
        levelCollection = new LevelCollection();
        for (int i = 0; i < 10; i++) {
            levelCollection.addValidLevel();
        }
        return levelCollection;
    }
}
