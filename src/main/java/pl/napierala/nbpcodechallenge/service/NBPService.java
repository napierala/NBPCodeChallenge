package pl.napierala.nbpcodechallenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.nbpcodechallenge.builder.CurrencyToCurrencyRateBuilder;
import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;
import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.nbp.NBPRate;
import pl.napierala.nbpcodechallenge.repository.NBPApiRepository;

@Service
public class NBPService implements CurrencyToCurrencyRateProvider {

    private final static Logger logger = LoggerFactory.getLogger(NBPService.class);

    private NBPApiRepository nbpApiRepository;

    @Autowired
    public NBPService(NBPApiRepository nbpApiRepository) {
        this.nbpApiRepository = nbpApiRepository;
    }

    /**
     * Get the rate from the currency to the currency, if something goes wrong return null.
     *
     * @param fromCurrency currency to get the rate from.
     * @param toCurrency   currency to get the rate to.
     * @return The rate from the fromCurrency to the toCurrency or null if something went wrong.
     */
    public CurrencyToCurrencyRate getRate(CurrencyType fromCurrency, CurrencyType toCurrency) {

        if (fromCurrency != CurrencyType.PLN) {
            throw new IllegalArgumentException("Only PLN as the fromCurrency is supported.");
        }

        NBPRate nbpRate = nbpApiRepository.getRate(toCurrency.getCode());

        CurrencyToCurrencyRate result = CurrencyToCurrencyRateBuilder.buildWith(nbpRate);

        if (result == null) {
            logger.error("Could not build a CurrencyToCurrencyRate from NBPRate:[" + nbpRate + "]");
        }

        return result;
    }
}