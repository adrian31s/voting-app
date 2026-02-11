package com.voting.votingapp.application.vote.controller;

import com.voting.votingapp.application.common.rest.exception.model.RestErrorDto;
import com.voting.votingapp.application.common.service.exception.ServiceException;
import com.voting.votingapp.application.election.controller.dto.model.ElectionDto;
import com.voting.votingapp.application.vote.controller.dto.VoteMapper;
import com.voting.votingapp.application.vote.controller.dto.model.VoteCreateRequestDto;
import com.voting.votingapp.application.vote.model.Vote;
import com.voting.votingapp.application.vote.service.VoteService;
import com.voting.votingapp.config.openapi.common.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/v1/vote")
@RequiredArgsConstructor
public class VoteController {
    private final VoteService voteService;
    private final VoteMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('role_user')")
    @Operation(
            operationId = "vote",
            description = "Allows users to vote on election"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "201",
                    description = "Created",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ElectionDto.class))
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
                    responseCode = "400",
                    description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RestErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Conflict",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RestErrorDto.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RestErrorDto.class))
            )
    })
    public ResponseEntity<?> vote(@RequestBody @Valid VoteCreateRequestDto request, Authentication authentication) {
        try {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String email = jwt.getClaim("email");
            if (email == null) {
                throw new ServiceException("User email could not be found");
            }
            Vote vote = voteService.createVote(request, email);
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(vote));
        } catch (ServiceException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestErrorDto("Vote not created", ex.getMessage()));
        } catch (Exception ex) {
            log.error("VoteController::vote throws exception: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorDto("VoteController::vote throws exception", ex.getMessage()));
        }
    }
}
