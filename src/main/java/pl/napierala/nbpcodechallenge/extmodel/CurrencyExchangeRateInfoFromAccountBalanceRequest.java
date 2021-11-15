package pl.napierala.nbpcodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.napierala.nbpcodechallenge.model.CurrencyType;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The request to for the currency exchange rate info from an account balance.")
public class CurrencyExchangeRateInfoFromAccountBalanceRequest implements Serializable {

    private static final long serialVersionUID = -8276200681206438649L;

    @NotNull
    @Schema(description = "The currency type.")
    private CurrencyType currencyType;

    @Schema(description = "The bank account number to get the balance from. Only null is considered not using the bank account number.")
    private String bankAccountNumber;

    @Schema(description = "The user code to get the balance from. Only null is considered not using the user code.")
    private String userCode;

    @AssertTrue
    @JsonIgnore
    private boolean bankAccountNumberOrUserCodeIsNotNull() {
        return bankAccountNumber != null || userCode != null;
    }
}