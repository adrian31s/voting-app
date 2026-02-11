package com.voting.votingapp.application.option.repository;

import com.voting.votingapp.application.option.model.Option;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {
    Optional<Option> findByNameAndElection_Name(String name, String electionName);
}
