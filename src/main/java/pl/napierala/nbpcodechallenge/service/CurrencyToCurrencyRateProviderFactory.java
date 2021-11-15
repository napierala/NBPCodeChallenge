package pl.napierala.nbpcodechallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.nbpcodechallenge.model.CurrencyType;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyToCurrencyRateProviderFactory {

    private NBPService nbpService;
    private Map<CurrencyType, Map<CurrencyType, CurrencyToCurrencyRateProvider>> providerMap = new HashMap<>();

    @Autowired
    public CurrencyToCurrencyRateProviderFactory(NBPService nbpService) {
        this.nbpService = nbpService;
        initProviderMap();
    }

    private void initProviderMap() {

        addProvider(CurrencyType.PLN, CurrencyType.EUR, nbpService);
    }

    private void addProvider(CurrencyType fromCurrencyType, CurrencyType toCurrencyType, CurrencyToCurrencyRateProvider provider) {
        Map<CurrencyType, CurrencyToCurrencyRateProvider> toCurrencyMap = providerMap.computeIfAbsent(fromCurrencyType, k -> new HashMap<>());
        toCurrencyMap.put(toCurrencyType, provider);
    }

    public Optional<CurrencyToCurrencyRateProvider> build(CurrencyType from, CurrencyType to) {

        if (providerMap == null) {
            return Optional.empty();
        }

        if (from == to) {
            return Optional.of(new TheSameCurrencyRateProvider());
        }

        Map<CurrencyType, CurrencyToCurrencyRateProvider> toMap = providerMap.get(from);

        if (toMap == null) {
            return Optional.empty();
        }

        CurrencyToCurrencyRateProvider provider = toMap.get(to);

        return Optional.ofNullable(provider);
    }
}