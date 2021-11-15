package pl.napierala.nbpcodechallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceRequest;
import pl.napierala.nbpcodechallenge.extmodel.CurrencyExchangeRateInfoFromAccountBalanceResponse;
import pl.napierala.nbpcodechallenge.service.ExchangeRateService;

import javax.validation.Valid;

@RestController
@RequestMapping("/exchangeRate")
public class ExchangeRateController {

    private ExchangeRateService exchangeRateService;

    @Autowired
    public ExchangeRateController(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = exchangeRateService;
    }

    @Operation(
            summary = "Currency exchange rates from bank accounts.",
            description = "Returns the exchange rates for accounts found by either the user code or bank account number or both. Only available for regular users.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CurrencyExchangeRateInfoFromAccountBalanceResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/accountBalance", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public CurrencyExchangeRateInfoFromAccountBalanceResponse accountBalance(@RequestBody @Valid final CurrencyExchangeRateInfoFromAccountBalanceRequest request) {
        return exchangeRateService.accountBalance(request);
    }
}