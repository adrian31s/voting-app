package com.voting.votingapp.application.vote.repository;

import com.voting.votingapp.application.election.model.Election;
import com.voting.votingapp.application.vote.model.Vote;
import com.voting.votingapp.application.voter.model.Voter;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepository extends JpaRepository<Vote, Long> {
  boolean existsByVoterAndOption_Election(Voter voter, Election election);
}
