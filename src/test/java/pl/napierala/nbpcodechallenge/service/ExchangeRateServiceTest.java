package pl.napierala.nbpcodechallenge.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import pl.napierala.nbpcodechallenge.entity.BankAccountEntity;
import pl.napierala.nbpcodechallenge.exception.BadRequestException;
import pl.napierala.nbpcodechallenge.exception.BankAccountNumberOrUserCodeNotFoundException;
import pl.napierala.nbpcodechallenge.exception.CurrencyToCurrencyRateNotFoundException;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceRequest;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceResponse;
import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;
import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.repository.BankAccountRepository;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @Mock
    private CurrencyToCurrencyRateProviderFactory currencyToCurrencyRateProviderFactory;

    @InjectMocks
    private ExchangeRateService exchangeRateService;

    @Test
    public void shouldWorkCorrectlyWithOnlyTheAccountNumberFor100PLNConvertedTo50EURWith2_0Rate() throws Exception {

        // Given
        String bankAccountNumber = "123456789";
        String userCode = "262626";

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .build();

        Long balanceInCents = 100_00L;

        BankAccountEntity bankAccountEntity = BankAccountEntity.builder()
                .userCode(userCode)
                .accountNumber(bankAccountNumber)
                .balanceInCents(balanceInCents)
                .currencyCode("PLN")
                .build();

        BigDecimal rate = new BigDecimal("2.0");

        when(bankAccountRepository.findByAccountNumber(any(String.class))).thenReturn(Optional.of(bankAccountEntity));
        when(currencyToCurrencyRateProviderFactory.build(any(CurrencyType.class), any(CurrencyType.class)))
                .thenReturn(Optional.of((fromCurrency, toCurrency) -> CurrencyToCurrencyRate.builder()
                        .fromCurrency(CurrencyType.PLN)
                        .toCurrency(CurrencyType.EUR)
                        .exchangeRate(rate)
                        .build()));

        // When
        CurrencyExchangeRateInfoFromAccountBalanceResponse response = exchangeRateService.accountBalance(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getElements());
        assertEquals(1, response.getElements().size());
        assertEquals(new Long(50_00L), response.getElements().get(0).getAmountInCents());
        assertEquals(new BigDecimal("50.00"), response.getElements().get(0).getAmountWithDecimalPlaces());
        assertEquals("EUR", response.getElements().get(0).getCurrencyCode());
        assertEquals(bankAccountNumber, response.getElements().get(0).getBankAccountNumber());
    }

    @Test
    public void shouldWorkCorrectlyWithOnlyTheAccountNumberFor200PLNConvertedTo200EURWith1_0Rate() throws Exception {

        // Given
        String bankAccountNumber = "123456789";
        String userCode = "262626";

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .build();

        Long balanceInCents = 200_00L;

        BankAccountEntity bankAccountEntity = BankAccountEntity.builder()
                .userCode(userCode)
                .accountNumber(bankAccountNumber)
                .balanceInCents(balanceInCents)
                .currencyCode("PLN")
                .build();

        BigDecimal rate = new BigDecimal("1.0");

        when(bankAccountRepository.findByAccountNumber(any(String.class))).thenReturn(Optional.of(bankAccountEntity));
        when(currencyToCurrencyRateProviderFactory.build(any(CurrencyType.class), any(CurrencyType.class)))
                .thenReturn(Optional.of((fromCurrency, toCurrency) -> CurrencyToCurrencyRate.builder()
                        .fromCurrency(CurrencyType.PLN)
                        .toCurrency(CurrencyType.EUR)
                        .exchangeRate(rate)
                        .build()));

        // When
        CurrencyExchangeRateInfoFromAccountBalanceResponse response = exchangeRateService.accountBalance(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getElements());
        assertEquals(1, response.getElements().size());
        assertEquals(new Long(200_00L), response.getElements().get(0).getAmountInCents());
        assertEquals(new BigDecimal("200.00"), response.getElements().get(0).getAmountWithDecimalPlaces());
        assertEquals("EUR", response.getElements().get(0).getCurrencyCode());
        assertEquals(bankAccountNumber, response.getElements().get(0).getBankAccountNumber());
    }

    @Test
    public void shouldWorkCorrectlyWithTheUserCodeAndAccountNumberFor10PLNConvertedTo2EURWith5_0Rate() throws Exception {

        // Given
        String bankAccountNumber = "123456789";
        String userCode = "262626";

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .userCode(userCode)
                .build();

        Long balanceInCents = 10_00L;

        BankAccountEntity bankAccountEntity = BankAccountEntity.builder()
                .userCode(userCode)
                .accountNumber(bankAccountNumber)
                .balanceInCents(balanceInCents)
                .currencyCode("PLN")
                .build();

        BigDecimal rate = new BigDecimal("5.0");

        when(bankAccountRepository.findByUserCodeAndAccountNumber(any(String.class), any(String.class))).thenReturn(Optional.of(bankAccountEntity));
        when(currencyToCurrencyRateProviderFactory.build(any(CurrencyType.class), any(CurrencyType.class)))
                .thenReturn(Optional.of((fromCurrency, toCurrency) -> CurrencyToCurrencyRate.builder()
                        .fromCurrency(CurrencyType.PLN)
                        .toCurrency(CurrencyType.EUR)
                        .exchangeRate(rate)
                        .build()));

        // When
        CurrencyExchangeRateInfoFromAccountBalanceResponse response = exchangeRateService.accountBalance(request);

        // Then
        assertNotNull(response);
        assertNotNull(response.getElements());
        assertEquals(1, response.getElements().size());
        assertEquals(new Long(2_00L), response.getElements().get(0).getAmountInCents());
        assertEquals(new BigDecimal("2.00"), response.getElements().get(0).getAmountWithDecimalPlaces());
        assertEquals("EUR", response.getElements().get(0).getCurrencyCode());
        assertEquals(bankAccountNumber, response.getElements().get(0).getBankAccountNumber());
    }

    @Test(expected = BankAccountNumberOrUserCodeNotFoundException.class)
    public void shouldThrowExceptionWithTheUserCodeAndAccountNumberMissingFromRepository() throws Exception {

        // Given
        String bankAccountNumber = "123456789";
        String userCode = "262626";

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .userCode(userCode)
                .build();

        when(bankAccountRepository.findByUserCodeAndAccountNumber(any(String.class), any(String.class))).thenReturn(Optional.empty());

        // When
        exchangeRateService.accountBalance(request);
    }

    @Test(expected = BadRequestException.class)
    public void shouldThrowExceptionWithNullUserNameAndAccountNumber() throws Exception {

        // Given
        String bankAccountNumber = null;
        String userCode = null;

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .userCode(userCode)
                .build();

        // When
        exchangeRateService.accountBalance(request);
    }

    @Test(expected = CurrencyToCurrencyRateNotFoundException.class)
    public void shouldThrowExceptionWhenNoProviderIsFound() throws Exception {

        // Given
        String bankAccountNumber = "123456789";
        String userCode = "262626";

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .userCode(userCode)
                .build();

        Long balanceInCents = 10_00L;

        BankAccountEntity bankAccountEntity = BankAccountEntity.builder()
                .userCode(userCode)
                .accountNumber(bankAccountNumber)
                .balanceInCents(balanceInCents)
                .currencyCode("PLN")
                .build();

        when(bankAccountRepository.findByUserCodeAndAccountNumber(any(String.class), any(String.class))).thenReturn(Optional.of(bankAccountEntity));
        when(currencyToCurrencyRateProviderFactory.build(any(CurrencyType.class), any(CurrencyType.class)))
                .thenReturn(Optional.empty());

        // When
        exchangeRateService.accountBalance(request);

        // Then
    }

    @Test(expected = CurrencyToCurrencyRateNotFoundException.class)
    public void shouldThrowExceptionWhenNoRateIsFound() throws Exception {

        // Given
        String bankAccountNumber = "123456789";
        String userCode = "262626";

        CurrencyExchangeRateInfoFromAccountBalanceRequest request = CurrencyExchangeRateInfoFromAccountBalanceRequest.builder()
                .currencyType(CurrencyType.EUR)
                .bankAccountNumber(bankAccountNumber)
                .userCode(userCode)
                .build();

        Long balanceInCents = 10_00L;

        BankAccountEntity bankAccountEntity = BankAccountEntity.builder()
                .userCode(userCode)
                .accountNumber(bankAccountNumber)
                .balanceInCents(balanceInCents)
                .currencyCode("PLN")
                .build();

        BigDecimal rate = new BigDecimal("5.0");

        when(bankAccountRepository.findByUserCodeAndAccountNumber(any(String.class), any(String.class))).thenReturn(Optional.of(bankAccountEntity));
        when(currencyToCurrencyRateProviderFactory.build(any(CurrencyType.class), any(CurrencyType.class)))
                .thenReturn(Optional.of((fromCurrency, toCurrency) -> null));

        // When
        exchangeRateService.accountBalance(request);

        // Then
    }
}