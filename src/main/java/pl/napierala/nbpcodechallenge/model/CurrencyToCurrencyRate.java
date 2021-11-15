package pl.napierala.nbpcodechallenge.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CurrencyToCurrencyRate implements Serializable {

    private static final long serialVersionUID = -2730458267342151612L;

    private CurrencyType fromCurrency;
    private CurrencyType toCurrency;

    private BigDecimal exchangeRate;
}