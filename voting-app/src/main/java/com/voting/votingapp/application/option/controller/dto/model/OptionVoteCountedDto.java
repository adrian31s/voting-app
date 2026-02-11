package com.voting.votingapp.application.option.controller.dto.model;

import lombok.Data;

@Data
public class OptionVoteCountedDto {
    private String name;
    private Integer voteCount;
}
