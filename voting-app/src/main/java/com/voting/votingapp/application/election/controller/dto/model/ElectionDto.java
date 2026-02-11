package com.voting.votingapp.application.election.controller.dto.model;

import com.voting.votingapp.application.option.controller.dto.model.OptionVoteCountedDto;
import java.util.List;

public record ElectionDto(String name, List<OptionVoteCountedDto> options) {}
