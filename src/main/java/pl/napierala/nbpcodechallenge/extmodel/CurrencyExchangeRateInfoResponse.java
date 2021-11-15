package pl.napierala.nbpcodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "A single currency exchange rate element.")
public class CurrencyExchangeRateInfoResponse implements Serializable {

    private static final long serialVersionUID = -3338970770151668931L;

    @NotEmpty
    @Schema(description = "The amount in cents meaning that $10.50 will be 1050 as it is in cents. The concept of cents is just a name as it could be grosz in PLN, pennies for the pound or euro cents.", example = "1050")
    private Long amountInCents;

    @NotEmpty
    @Schema(description = "The amount with decimal places.", example = "10.50")
    private BigDecimal amountWithDecimalPlaces;

    @NotEmpty
    @Schema(description = "The currency ISO 4217 code.", example = "EUR")
    private String currencyCode;

    @NotEmpty
    @Schema(description = "The bank account number that the balance was taken from.")
    private String bankAccountNumber;
}