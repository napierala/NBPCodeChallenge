package pl.napierala.nbpcodechallenge.builder;

import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoResponse;
import pl.napierala.nbpcodechallenge.model.CurrencyType;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CurrencyExchangeRateInfoResponseBuilder {

    public static CurrencyExchangeRateInfoResponse buildWith(Long amountInCents, CurrencyType currencyType, String accountNumber) {

        if (currencyType == null) {
            throw new NullPointerException("CurrencyType cannot be null");
        }

        BigDecimal amountWithDecimalPlaces = new BigDecimal(amountInCents)
                .divide(new BigDecimal(100), 2, RoundingMode.HALF_UP);

        return CurrencyExchangeRateInfoResponse.builder()
                .amountInCents(amountInCents)
                .amountWithDecimalPlaces(amountWithDecimalPlaces)
                .currencyCode(currencyType.getCode())
                .bankAccountNumber(accountNumber)
                .build();
    }
}
