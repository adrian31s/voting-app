package com.voting.votingapp.application.election.repository;


import com.voting.votingapp.application.election.model.Election;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectionRepository extends JpaRepository<Election, Long> {
}
