package com.voting.votingapp.application.option.controller.dto;

import com.voting.votingapp.application.option.controller.dto.model.OptionVoteCountedDto;
import com.voting.votingapp.application.option.model.Option;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OptionMapper {
    @Mapping(target = "voteCount", expression = "java(o.getVotes() != null ? o.getVotes().size() : 0)")
    OptionVoteCountedDto toDto(Option o);

    List<OptionVoteCountedDto> toDtos(List<Option> o);
}
