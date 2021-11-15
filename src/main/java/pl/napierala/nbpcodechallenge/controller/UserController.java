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
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterRequest;
import pl.napierala.nbpcodechallenge.extmodel.UserRegisterResponse;
import pl.napierala.nbpcodechallenge.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(
            summary = "Register a user.",
            description = "Registers a user if the userName is not already used. Available only for Admins.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Result",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserRegisterResponse.class)
                            )
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public UserRegisterResponse register(@RequestBody @Valid final UserRegisterRequest request) {
        return userService.register(request);
    }
}