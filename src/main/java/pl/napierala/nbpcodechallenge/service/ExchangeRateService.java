package pl.napierala.nbpcodechallenge.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.napierala.nbpcodechallenge.builder.CurrencyExchangeRateInfoResponseBuilder;
import pl.napierala.nbpcodechallenge.calculator.ExchangeRateCalculator;
import pl.napierala.nbpcodechallenge.entity.BankAccountEntity;
import pl.napierala.nbpcodechallenge.exception.BadRequestException;
import pl.napierala.nbpcodechallenge.exception.BankAccountNumberOrUserCodeNotFoundException;
import pl.napierala.nbpcodechallenge.exception.CurrencyToCurrencyRateNotFoundException;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceRequest;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceResponse;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoResponse;
import pl.napierala.nbpcodechallenge.model.CurrencyToCurrencyRate;
import pl.napierala.nbpcodechallenge.model.CurrencyType;
import pl.napierala.nbpcodechallenge.repository.BankAccountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeRateService {

    private final static Logger logger = LoggerFactory.getLogger(ExchangeRateService.class);

    private BankAccountRepository bankAccountRepository;
    private CurrencyToCurrencyRateProviderFactory currencyToCurrencyRateProviderFactory;

    @Autowired
    public ExchangeRateService(BankAccountRepository bankAccountRepository, CurrencyToCurrencyRateProviderFactory currencyToCurrencyRateProviderFactory) {
        this.bankAccountRepository = bankAccountRepository;
        this.currencyToCurrencyRateProviderFactory = currencyToCurrencyRateProviderFactory;
    }

    public CurrencyExchangeRateInfoFromAccountBalanceResponse accountBalance(CurrencyExchangeRateInfoFromAccountBalanceRequest request) {

        List<BankAccountEntity> bankAccountsToWorkWith = new ArrayList<>();

        String userCode = request.getUserCode();
        String bankAccountNumber = request.getBankAccountNumber();

        if (userCode != null && bankAccountNumber != null) {
            Optional<BankAccountEntity> foundFromDB = bankAccountRepository.findByUserCodeAndAccountNumber(userCode, bankAccountNumber);

            if (foundFromDB.isPresent()) {
                bankAccountsToWorkWith.add(foundFromDB.get());
            }
        } else if (userCode != null && bankAccountNumber == null) {

            bankAccountsToWorkWith = bankAccountRepository.findByUserCode(userCode);

        } else if (userCode == null && bankAccountNumber != null) {

            Optional<BankAccountEntity> foundFromDB = bankAccountRepository.findByAccountNumber(bankAccountNumber);

            if (foundFromDB.isPresent()) {
                bankAccountsToWorkWith.add(foundFromDB.get());
            }

        } else {
            throw new BadRequestException();
        }

        if (bankAccountsToWorkWith == null || bankAccountsToWorkWith.isEmpty()) {
            throw new BankAccountNumberOrUserCodeNotFoundException();
        }

        List<CurrencyExchangeRateInfoResponse> result = new ArrayList<>();

        for (BankAccountEntity bankAccountEntity : bankAccountsToWorkWith) {
            result.add(calculateExchangeRate(bankAccountEntity, request.getCurrencyType()));
        }

        return CurrencyExchangeRateInfoFromAccountBalanceResponse.builder()
                .elements(result)
                .build();
    }

    private CurrencyExchangeRateInfoResponse calculateExchangeRate(BankAccountEntity bankAccountEntity, CurrencyType toCurrencyType) {

        CurrencyType bankAccountCurrency = CurrencyType.findByCodeOrThrowException(bankAccountEntity.getCurrencyCode());

        Optional<CurrencyToCurrencyRateProvider> provider = currencyToCurrencyRateProviderFactory.build(bankAccountCurrency, toCurrencyType);

        if (!provider.isPresent()) {
            logger.error("CurrencyToCurrencyRateProvider not found fromCurrency:[" + bankAccountCurrency + "], toCurrency:[" + toCurrencyType + "]");
            throw new CurrencyToCurrencyRateNotFoundException();
        }

        CurrencyToCurrencyRate rate = provider.get().getRate(bankAccountCurrency, toCurrencyType);

        if (rate == null) {
            logger.error("CurrencyToCurrencyRate not found fromCurrency:[" + bankAccountCurrency + "], toCurrency:[" + toCurrencyType + "]");
            throw new CurrencyToCurrencyRateNotFoundException();
        }

        Long calculatedAmountInCentsInTheOtherCurrency = ExchangeRateCalculator.calculate(bankAccountEntity.getBalanceInCents(), rate.getExchangeRate());

        return CurrencyExchangeRateInfoResponseBuilder.buildWith(calculatedAmountInCentsInTheOtherCurrency, toCurrencyType, bankAccountEntity.getAccountNumber());
    }
}