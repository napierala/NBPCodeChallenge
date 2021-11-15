package pl.napierala.nbpcodechallenge.util;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

public class RequestUtil {

    public static <T> T request(TestRestTemplate restTemplate, String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz, Object request) throws Exception {

        // Given
        TestRestTemplate finalRestTemplate = restTemplate;

        if (user != null && password != null) {
            finalRestTemplate = finalRestTemplate.withBasicAuth(user, password);
        }

        return finalRestTemplate.exchange(url, httpMethod, new HttpEntity<>(request), responseClazz).getBody();
    }
}
