package pl.napierala.nbpcodechallenge.nbp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class NBPRate implements Serializable {

    private static final long serialVersionUID = -1527290920723684637L;

    @JsonProperty("code")
    private String code;

    @JsonProperty("rates")
    private List<NBPRateElement> rates;
}