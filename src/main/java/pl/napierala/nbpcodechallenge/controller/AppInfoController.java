package pl.napierala.nbpcodechallenge.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/appInfo")
public class AppInfoController {

    @Value("${application.version.full}")
    private String fullVersion;

    @Operation(
            summary = "Full version of the app.",
            description = "The format: x.x.x-commid_id. Only available for Admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "text/plain",
                                    schema = @Schema(description = "Version", example = "1.0.0-c1233ed")
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String version() {
        return fullVersion;
    }
}