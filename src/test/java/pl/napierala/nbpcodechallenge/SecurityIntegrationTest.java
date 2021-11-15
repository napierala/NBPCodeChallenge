package pl.napierala.nbpcodechallenge;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpStatusCodeException;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceRequest;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceResponse;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterRequest;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterResponse;
import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.util.IntegrationTest;

import java.net.HttpRetryException;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class SecurityIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final Set<Integer> NOT_AUTHORIZED_HTTP_CODES = Sets.newHashSet(401, 403);

    @Test
    public void shouldTestCorrectlyForTheAppInfoController() throws Exception {

        shouldNotPassSecurity("/appInfo/version", HttpMethod.GET, null, null, String.class);
        shouldNotPassSecurity("/appInfo/version", HttpMethod.GET, "invalid_user", "test", String.class);
        shouldNotPassSecurity("/appInfo/version", HttpMethod.GET, "user", "user_password", String.class);
        shouldPassSecurity("/appInfo/version", HttpMethod.GET, "admin", "admin_password", String.class);
    }

    @Test
    public void shouldTestCorrectlyForTheUsersController() throws Exception {

        UserRegisterRequest request = UserRegisterRequest.builder()
                .userName("user")
                .password("user_password")
                .build();

        shouldNotPassSecurity("/user/register", HttpMethod.POST, null, null, UserRegisterResponse.class, request);
        shouldNotPassSecurity("/user/register", HttpMethod.POST, "invalid_user", "test", UserRegisterResponse.class, request);
        shouldNotPassSecurity("/user/register", HttpMethod.POST, "api#1", "user_password", UserRegisterResponse.class, request);
        shouldPassSecurity("/user/register", HttpMethod.POST, "admin", "admin_password", UserRegisterResponse.class, request);
    }

    @Test
    public void shouldTestCorrectlyForTheExchangeRateController() throws Exception {

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber("PL27114020040000300201355387")
                .build();

        shouldNotPassSecurity("/exchangeRate/accountBalance", HttpMethod.POST, null, null, CurrencyExchangeRateInfoFromAccountBalanceResponse.class, request);
        shouldNotPassSecurity("/exchangeRate/accountBalance", HttpMethod.POST, "admin", "admin_password", CurrencyExchangeRateInfoFromAccountBalanceResponse.class, request);
        shouldPassSecurity("/exchangeRate/accountBalance", HttpMethod.POST, "api#1", "user_password", CurrencyExchangeRateInfoFromAccountBalanceResponse.class, request);
    }

    private <T> void shouldPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz, Object request) throws Exception {
        testSecurity(url, httpMethod, true, user, password, responseClazz, request);
    }

    private <T> void shouldPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz) throws Exception {
        shouldPassSecurity(url, httpMethod, user, password, responseClazz, null);
    }

    private <T> void shouldNotPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz, Object request) throws Exception {
        testSecurity(url, httpMethod, false, user, password, responseClazz, request);
    }


    private <T> void shouldNotPassSecurity(String url, HttpMethod httpMethod, String user, String password, Class<T> responseClazz) throws Exception {
        shouldNotPassSecurity(url, httpMethod, user, password, responseClazz, null);
    }

    private <T> void testSecurity(String url, HttpMethod httpMethod, boolean pass, String user, String password, Class<T> responseClazz, Object request) throws Exception {

        // Given
        TestRestTemplate finalRestTemplate = this.restTemplate;

        if (user != null && password != null) {
            finalRestTemplate = finalRestTemplate.withBasicAuth(user, password);
        }

        // When
        Integer responseCode = null;

        try {
            responseCode = finalRestTemplate.exchange(url, httpMethod, new HttpEntity<>(request), responseClazz).getStatusCodeValue();
        } catch (Exception e) {
            if (e.getCause() instanceof HttpRetryException) {
                HttpRetryException httpRetryException = (HttpRetryException) e.getCause();
                responseCode = httpRetryException.responseCode();
            } else if (e.getCause() instanceof HttpStatusCodeException) {
                HttpStatusCodeException httpStatusCodeException = (HttpStatusCodeException) e.getCause();
                responseCode = httpStatusCodeException.getStatusCode().value();
            } else {
                throw e;
            }
        }

        // Then
        assertNotNull(responseCode);

        boolean notAuthorizedHttpCode = NOT_AUTHORIZED_HTTP_CODES.contains(responseCode);

        if (pass) {
            assertFalse(notAuthorizedHttpCode);
        } else {
            assertTrue(notAuthorizedHttpCode);
        }
    }
}