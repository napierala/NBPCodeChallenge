package pl.napierala.nbpcodechallenge.builder;

import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;
import pl.napierala.nbpcodechallenge.nbp.NBPRate;

public class CurrencyToCurrencyRateBuilder {

    public static CurrencyToCurrencyRate buildWith(NBPRate nbpRate) {

        if (nbpRate == null || nbpRate.getRates() == null || nbpRate.getRates().isEmpty()) {
            return null;
        }

        return CurrencyToCurrencyRate.builder()
                .fromCurrency(CurrencyType.PLN)
                .toCurrency(CurrencyType.findByCodeOrThrowException(nbpRate.getCode()))
                .exchangeRate(nbpRate.getRates().get(0).getMid())
                .build();
    }
}
