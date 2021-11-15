package pl.napierala.nbpcodechallenge.extmodel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "The user login request.")
public class UserLoginRequest implements Serializable {

    private static final long serialVersionUID = 5518055019311615855L;

    @NotEmpty
    @Size(min = 3)
    @Schema(description = "The username.")
    private String userName;

    @NotEmpty
    @Size(min = 5)
    @Schema(description = "The password, should be in plaintext - not encoded in any way.")
    private String password;
}