package com.voting.votingapp.application.voter.service;

import com.voting.votingapp.application.common.service.exception.ServiceException;
import com.voting.votingapp.application.voter.model.Voter;
import com.voting.votingapp.application.voter.repository.VoterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoterService {
  private final VoterRepository voterRepository;

  public Voter getOrCreateVoter(String email) {
    Voter voter = voterRepository.findByEmail(email);
    if (voter == null) {
      voter = new Voter();
      voter.setEmail(email);
      voter.setBlocked(false);
      return voterRepository.save(voter);
    }
    return voter;
  }

  public void setUserBlockage(boolean isBlockage, String email) {
    int updatedRows = voterRepository.blockByEmail(email, isBlockage);
    if (updatedRows == 0) {
      throw new ServiceException("No user found with email " + email);
    }
  }
}
