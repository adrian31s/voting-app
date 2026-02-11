package com.voting.votingapp.application.voter.controller;

import com.voting.votingapp.application.common.rest.exception.model.RestErrorDto;
import com.voting.votingapp.application.common.service.exception.ServiceException;
import com.voting.votingapp.application.voter.service.VoterService;
import com.voting.votingapp.config.openapi.common.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/voter")
@RequiredArgsConstructor
public class VoterController {
    private final VoterService voterService;

    @PreAuthorize("hasRole('role_admin')")
    @PatchMapping
    @Operation(
            operationId = "setBlockage",
            description = "Allows admin to set user blockage"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "No Content"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized")
            ,
            @ApiResponse(
                    responseCode = "403",
                    description = "Forbidden")
            ,
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RestErrorDto.class))
            )
    })

    public ResponseEntity<?> setBlockage(@RequestParam @NotBlank @NotEmpty String email,
                                         @RequestParam @NotNull Boolean blocked) {
        try {
            voterService.setUserBlockage(blocked, email);
            return ResponseEntity.noContent().build();
        } catch (ServiceException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestErrorDto("User blockage not updated", ex.getMessage()));
        } catch (Exception ex) {
            log.error("VoterController::setBlockage throws exception: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorDto("VoterController::setBlockage throws exception", ex.getMessage()));
        }
    }

}
