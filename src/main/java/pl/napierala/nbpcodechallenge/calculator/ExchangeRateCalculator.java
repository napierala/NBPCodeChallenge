package pl.napierala.nbpcodechallenge.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ExchangeRateCalculator {

    public static Long calculate(Long amount, BigDecimal exchangeRate) {

        return new BigDecimal(amount)
                .divide(exchangeRate, 0, RoundingMode.HALF_UP)
                .longValueExact();
    }
}
