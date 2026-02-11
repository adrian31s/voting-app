package com.voting.votingapp.application.vote.controller.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public record VoteCreateRequestDto(
    @NotEmpty @NotBlank String optionName, @NotEmpty @NotBlank String electionName) {}
