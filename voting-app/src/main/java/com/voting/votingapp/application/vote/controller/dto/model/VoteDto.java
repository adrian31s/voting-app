package com.voting.votingapp.application.vote.controller.dto.model;

import lombok.Data;

@Data
public class VoteDto {
    private String voterEmail;
    private String optionName;
}
