package com.voting.votingapp.application.election.model;

import com.voting.votingapp.application.election.controller.dto.model.ElectionCreateRequestDto;
import com.voting.votingapp.application.option.model.Option;

import java.util.List;
import java.util.Set;

public class ElectionFactory {
    public static Election createElection(ElectionCreateRequestDto dto) {
        Election election = new Election();
        List<Option> options = createOptions(dto.getOptions(), election);
        election.setName(dto.getName());
        election.setOptions(options);
        return election;
    }

    private static List<Option> createOptions(Set<String> names, Election election) {
        return names.stream()
                .map(name -> new Option(null, name, null, election))
                .toList();
    }

}
