package com.voting.votingapp.application.election.service;

import com.voting.votingapp.application.common.service.exception.ServiceException;
import com.voting.votingapp.application.election.controller.dto.model.ElectionCreateRequestDto;
import com.voting.votingapp.application.election.model.Election;
import com.voting.votingapp.application.election.model.ElectionFactory;
import com.voting.votingapp.application.election.repository.ElectionRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ElectionService {
    private final ElectionRepository repository;

    public List<Election> getElections() {
        return repository.findAll();
    }

    public Election creteElection(ElectionCreateRequestDto request) {
        try {
            return repository.save(ElectionFactory.createElection(request));
        } catch (DataIntegrityViolationException ex) {
            throw new ServiceException("Election with specified name already exists");
        }
    }
}
