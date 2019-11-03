package nl.limakajo.numbers.RABO.API.security;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class APISecurityConfigTest {

    @ParameterizedTest
    @CsvSource({
            "admin, admin, 200",
            "user, user, 403"
    })
    void testDeleteWithUserRoles(String user, String password, int resultCode) {
        String levelNumbers = "001002003004005006007";
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(
                new AuthScope("localhost", 8080),
                new UsernamePasswordCredentials(user, password));
        try {
            CloseableHttpResponse response = HttpClientBuilder
                    .create()
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .build()
                    .execute(new HttpDelete("http://localhost:8080/api/levels/" + levelNumbers));
            assertEquals(resultCode, response.getStatusLine().getStatusCode());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}