package com.voting.votingapp.application.election.controller.dto.model;

import com.voting.votingapp.application.option.controller.dto.model.OptionVoteCountedDto;
import lombok.Data;

import java.util.List;

@Data
public class ElectionDto {
    private String name;
    private List<OptionVoteCountedDto> options;
}
