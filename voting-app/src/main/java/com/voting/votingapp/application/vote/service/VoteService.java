package com.voting.votingapp.application.vote.service;

import com.voting.votingapp.application.common.service.exception.ServiceException;
import com.voting.votingapp.application.option.model.Option;
import com.voting.votingapp.application.option.repository.OptionRepository;
import com.voting.votingapp.application.vote.controller.dto.model.VoteCreateRequestDto;
import com.voting.votingapp.application.vote.model.Vote;
import com.voting.votingapp.application.vote.repository.VoteRepository;
import com.voting.votingapp.application.voter.model.Voter;
import com.voting.votingapp.application.voter.repository.VoterRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository repository;
    private final OptionRepository optionRepository;
    private final VoterRepository voterRepository;

    @Transactional
    public Vote createVote(VoteCreateRequestDto request, String voterEmail) {
        Voter voter = voterRepository.findByEmail(voterEmail);
        if (voter == null) {
            voter = new Voter();
            voter.setEmail(voterEmail);
            voter = voterRepository.save(voter);
        } else if (voter.isBlocked()) {
            throw new ServiceException("User is blocked");
        }

        Option option = optionRepository
                .findByNameAndElection_Name(request.getOptionName(), request.getElectionName())
                .orElseThrow(() -> new ServiceException("Option for specified Election does not exist"));

        boolean alreadyVoted = repository.existsByVoterAndOption_Election(voter, option.getElection());

        if (alreadyVoted) {
            throw new ServiceException("User has already voted in this election");
        }

        return repository.save(new Vote(null, voter, option));
    }
}
