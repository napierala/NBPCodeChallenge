package pl.napierala.nbpcodechallenge.service;

import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;
import pl.napierala.nbpcodechallenge.model.CurrencyType;

import java.math.BigDecimal;

public class TheSameCurrencyRateProvider implements CurrencyToCurrencyRateProvider {

    @Override
    public CurrencyToCurrencyRate getRate(CurrencyType fromCurrency, CurrencyType toCurrency) {
        return CurrencyToCurrencyRate.builder()
                .fromCurrency(fromCurrency)
                .toCurrency(toCurrency)
                .exchangeRate(BigDecimal.ONE)
                .build();
    }
}
