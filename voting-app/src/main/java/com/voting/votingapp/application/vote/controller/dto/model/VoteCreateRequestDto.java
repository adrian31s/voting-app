package com.voting.votingapp.application.vote.controller.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class VoteCreateRequestDto {
    @NotEmpty
    @NotBlank
    private String optionName;

    @NotEmpty
    @NotBlank
    private String electionName;
}
