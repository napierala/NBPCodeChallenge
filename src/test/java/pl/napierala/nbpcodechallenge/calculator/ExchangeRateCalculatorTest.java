package pl.napierala.nbpcodechallenge.calculator;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class ExchangeRateCalculatorTest {

    @Test
    public void shouldCalculateCorrectlyFor1000And1_0Rate() {

        // Given
        Long amount = 1000L;
        BigDecimal rate = BigDecimal.ONE;

        // When
        Long result = ExchangeRateCalculator.calculate(amount, rate);

        // Then
        assertEquals(new Long(1000L), result);
    }

    @Test
    public void shouldCalculateCorrectlyFor1000And2_0Rate() {

        // Given
        Long amount = 1000L;
        BigDecimal rate = new BigDecimal("2.0");

        // When
        Long result = ExchangeRateCalculator.calculate(amount, rate);

        // Then
        assertEquals(new Long(500L), result);
    }

    @Test
    public void shouldCalculateCorrectlyFor50000And4_6365Rate() {

        // Given
        Long amount = 50000L;
        BigDecimal rate = new BigDecimal("4.6365");

        // When
        Long result = ExchangeRateCalculator.calculate(amount, rate);

        // Then
        assertEquals(new Long(10784L), result);
    }

    @Test
    public void shouldCalculateCorrectlyFor50000And4_6007Rate() {

        // Given
        Long amount = 50000L;
        BigDecimal rate = new BigDecimal("4.6007");

        // When
        Long result = ExchangeRateCalculator.calculate(amount, rate);

        // Then
        assertEquals(new Long(10868L), result);
    }
}