package com.voting.votingapp.application.election.controller.dto;

import com.voting.votingapp.application.election.controller.dto.model.ElectionDto;
import com.voting.votingapp.application.election.model.Election;
import com.voting.votingapp.application.option.controller.dto.OptionMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = OptionMapper.class)
public interface ElectionMapper {
    ElectionDto toDto(Election election);

    List<ElectionDto> toDtos(List<Election> elections);

}
