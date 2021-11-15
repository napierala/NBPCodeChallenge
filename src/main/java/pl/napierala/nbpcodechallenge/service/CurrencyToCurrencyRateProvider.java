package pl.napierala.nbpcodechallenge.service;

import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;

public interface CurrencyToCurrencyRateProvider {

    CurrencyToCurrencyRate getRate(CurrencyType fromCurrency, CurrencyType toCurrency);
}