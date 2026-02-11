package com.voting.votingapp.application.vote.model;

import com.voting.votingapp.application.option.model.Option;
import com.voting.votingapp.application.voter.model.Voter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = Vote.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class Vote {
    public static final String TABLE_NAME = "VOTE";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Voter voter;

    @ManyToOne
    private Option option;
}
