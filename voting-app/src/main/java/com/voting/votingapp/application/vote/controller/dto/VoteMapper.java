package com.voting.votingapp.application.vote.controller.dto;

import com.voting.votingapp.application.vote.controller.dto.model.VoteDto;
import com.voting.votingapp.application.vote.model.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    @Mapping(target = "voterEmail", source = "vote.voter.email")
    @Mapping(target = "optionName", source = "vote.option.name")
    VoteDto toDto(Vote vote);
}
