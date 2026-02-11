package com.voting.votingapp.application.election.controller;

import com.voting.votingapp.application.common.rest.exception.model.RestErrorDto;
import com.voting.votingapp.application.common.service.exception.ServiceException;
import com.voting.votingapp.application.election.controller.dto.ElectionMapper;
import com.voting.votingapp.application.election.controller.dto.model.ElectionCreateRequestDto;
import com.voting.votingapp.application.election.controller.dto.model.ElectionDto;
import com.voting.votingapp.application.election.model.Election;
import com.voting.votingapp.application.election.service.ElectionService;
import com.voting.votingapp.config.openapi.common.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/v1/election")
@AllArgsConstructor
public class ElectionController {
    private final ElectionMapper mapper;
    private final ElectionService electionService;

    @GetMapping
    @Operation(
            operationId = "getElections",
            description = "Allows to get all elections"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Ok",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = ElectionDto[].class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server Error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = RestErrorDto.class))
            )
    })
    public ResponseEntity<?> getElections() {
        try {
            List<Election> elections = electionService.getElections();
            return ResponseEntity.ok(mapper.toDtos(elections));
        } catch (Exception ex) {
            log.error("ElectionController::getElections throws exception: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorDto("ElectionController::getElections throws exception", ex.getMessage()));
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('role_admin')")
    @Operation(
            operationId = "createElection",
            description = "Allows to create an election"
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
    public ResponseEntity<?> createElection(@RequestBody @Valid ElectionCreateRequestDto request) {
        try {
            Election election = electionService.creteElection(request);
            return ResponseEntity.ok(mapper.toDto(election));
        } catch (ServiceException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new RestErrorDto("Object not created", ex.getMessage()));
        } catch (Exception ex) {
            log.error("ElectionController::createElection throws exception: ", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new RestErrorDto("ElectionController::createElection", ex.getMessage()));
        }
    }

}
