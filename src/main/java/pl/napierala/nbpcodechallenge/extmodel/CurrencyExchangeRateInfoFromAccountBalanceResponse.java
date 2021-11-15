package pl.napierala.nbpcodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The response to for the currency exchange rate info from an account balance. Each element corresponds to a single bank account converted balance.")
public class CurrencyExchangeRateInfoFromAccountBalanceResponse implements Serializable {

    private static final long serialVersionUID = -9179719593243719059L;

    @ArraySchema(schema = @Schema(description = "Elements making up the response."))
    private List<CurrencyExchangeRateInfoResponse> elements;
}