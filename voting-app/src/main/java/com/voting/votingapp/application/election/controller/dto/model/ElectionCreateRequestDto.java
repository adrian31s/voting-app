package com.voting.votingapp.application.election.controller.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record ElectionCreateRequestDto(
    @NotEmpty @Size(min = 2) Set<String> options, @NotEmpty @NotBlank String name) {}
