package pl.napierala.nbpcodechallenge.builder;

import org.assertj.core.util.Lists;
import org.junit.Test;
import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;
import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.nbp.NBPRate;
import pl.napierala.nbpcodechallenge.nbp.NBPRateElement;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class CurrencyToCurrencyRateBuilderTest {

    @Test
    public void shouldBuildCorrectlyWithNBPRateWithOneInnerRate() {

        // Given
        BigDecimal rate = new BigDecimal("4.6365");

        NBPRate nbpRate = NBPRate.builder()
                .code("EUR")
                .rates(Lists.newArrayList(
                        NBPRateElement.builder()
                                .effectiveDate(LocalDate.of(2021, 11, 15))
                                .mid(rate)
                                .build()
                ))
                .build();

        // When
        CurrencyToCurrencyRate result = CurrencyToCurrencyRateBuilder.buildWith(nbpRate);

        // Then
        assertNotNull(result);
        assertEquals(CurrencyType.PLN, result.getFromCurrency());
        assertEquals(CurrencyType.EUR, result.getToCurrency());
        assertEquals(rate, result.getExchangeRate());
    }

    @Test
    public void shouldBuildCorrectlyWithNBPRateWithTwoInnerRates() {

        // Given
        BigDecimal rate = new BigDecimal("4.6365");
        BigDecimal rate2 = new BigDecimal("4.7000");

        NBPRate nbpRate = NBPRate.builder()
                .code("EUR")
                .rates(Lists.newArrayList(
                        NBPRateElement.builder()
                                .effectiveDate(LocalDate.of(2021, 11, 15))
                                .mid(rate)
                                .build(),
                        NBPRateElement.builder()
                                .effectiveDate(LocalDate.of(2021, 11, 16))
                                .mid(rate2)
                                .build()
                ))
                .build();

        // When
        CurrencyToCurrencyRate result = CurrencyToCurrencyRateBuilder.buildWith(nbpRate);

        // Then
        assertNotNull(result);
        assertEquals(CurrencyType.PLN, result.getFromCurrency());
        assertEquals(CurrencyType.EUR, result.getToCurrency());
        assertEquals(rate, result.getExchangeRate());
    }

    @Test
    public void shouldReturnNullWithNBPRateWithEmptyRates() {

        // Given
        BigDecimal rate = new BigDecimal("4.6365");

        NBPRate nbpRate = NBPRate.builder()
                .code("EUR")
                .rates(new ArrayList<>())
                .build();

        // When
        CurrencyToCurrencyRate result = CurrencyToCurrencyRateBuilder.buildWith(nbpRate);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldReturnNullWithNBPRateWithNullRates() {

        // Given
        BigDecimal rate = new BigDecimal("4.6365");

        NBPRate nbpRate = NBPRate.builder()
                .code("EUR")
                .rates(null)
                .build();

        // When
        CurrencyToCurrencyRate result = CurrencyToCurrencyRateBuilder.buildWith(nbpRate);

        // Then
        assertNull(result);
    }

    @Test
    public void shouldReturnNullWithNullNBPRate() {

        // Given

        // When
        CurrencyToCurrencyRate result = CurrencyToCurrencyRateBuilder.buildWith(null);

        // Then
        assertNull(result);
    }
}