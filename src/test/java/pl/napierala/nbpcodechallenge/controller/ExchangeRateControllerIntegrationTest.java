package pl.napierala.nbpcodechallenge.controller;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import pl.napierala.nbpcodechallenge.entity.BankAccountEntity;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceRequest;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceResponse;
import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.repository.BankAccountRepository;
import pl.napierala.nbpcodechallenge.util.IntegrationTest;
import pl.napierala.nbpcodechallenge.util.RequestUtil;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Category(IntegrationTest.class)
public class ExchangeRateControllerIntegrationTest {

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldCorrectlyConvertPLNAccountToEUR() throws Exception {

        // Given
        String userCode = "USER_CODE_ERCI1";
        String bankAccountNumber = "123456789_ERCI1";
        Long balanceInCents = 100_00L;
        String currencyCode = "PLN";

        BankAccountEntity bankAccountEntity = bankAccountRepository.save(
                BankAccountEntity.builder()
                        .userCode(userCode)
                        .accountNumber(bankAccountNumber)
                        .balanceInCents(balanceInCents)
                        .currencyCode(currencyCode)
                        .build()
        );

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .build();

        // When
        CurrencyExchangeRateInfoFromAccountBalanceResponse response = RequestUtil.request(restTemplate, "/exchangeRate/accountBalance", HttpMethod.POST, "api#1", "user_password", CurrencyExchangeRateInfoFromAccountBalanceResponse.class, request);

        //Then
        assertNotNull(response);
        assertNotNull(response.getElements());
        assertEquals(1, response.getElements().size());
        assertNotNull(response.getElements().get(0).getAmountInCents());
        assertTrue(response.getElements().get(0).getAmountInCents() > 0);
        assertNotNull(response.getElements().get(0).getAmountWithDecimalPlaces());
        assertEquals(bankAccountNumber, response.getElements().get(0).getBankAccountNumber());
    }
}