package com.voting.votingapp.application.election.controller.dto.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class ElectionCreateRequestDto {
    @NotEmpty
    @Size(min = 2)
    private Set<String> options;

    @NotEmpty
    @NotBlank
    private String name;
}
