package pl.napierala.nbpcodechallenge.builder;

import org.junit.Test;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoResponse;
import pl.napierala.nbpcodechallenge.model.CurrencyType;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CurrencyExchangeRateInfoResponseBuilderTest {

    @Test
    public void shouldBuildCorrectlyWith1050AmountInCents() {

        // Given
        Long amountInCents = 1050L;
        CurrencyType currencyType = CurrencyType.PLN;
        String accountNumber = "ACCOUNTNUMBER";

        // When
        CurrencyExchangeRateInfoResponse result = CurrencyExchangeRateInfoResponseBuilder.buildWith(amountInCents, currencyType, accountNumber);

        // Then
        assertNotNull(result);
        assertEquals(amountInCents, result.getAmountInCents());
        assertNotNull(result.getAmountWithDecimalPlaces());
        assertEquals(0, new BigDecimal("10.50").compareTo(result.getAmountWithDecimalPlaces()));
        assertEquals("PLN", result.getCurrencyCode());
        assertEquals(accountNumber, result.getBankAccountNumber());
    }

    @Test
    public void shouldBuildCorrectlyWith99AmountInCents() {

        // Given
        Long amountInCents = 99L;
        CurrencyType currencyType = CurrencyType.PLN;
        String accountNumber = "ACCOUNTNUMBER";

        // When
        CurrencyExchangeRateInfoResponse result = CurrencyExchangeRateInfoResponseBuilder.buildWith(amountInCents, currencyType, accountNumber);

        // Then
        assertNotNull(result);
        assertEquals(amountInCents, result.getAmountInCents());
        assertNotNull(result.getAmountWithDecimalPlaces());
        assertEquals(0, new BigDecimal("0.99").compareTo(result.getAmountWithDecimalPlaces()));
        assertEquals("PLN", result.getCurrencyCode());
        assertEquals(accountNumber, result.getBankAccountNumber());
    }
}