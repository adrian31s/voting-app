package com.voting.votingapp.application.voter.repository;

import com.voting.votingapp.application.voter.model.Voter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VoterRepository extends JpaRepository<Voter, Long> {
    Voter findByEmail(String email);

    @Modifying
    @Transactional
    @Query("UPDATE Voter v SET v.blocked = :blocked WHERE v.email = :email")
    int blockByEmail(@Param("email") String email, @Param("blocked") Boolean blocked);
}
