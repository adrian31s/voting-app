package com.voting.votingapp.application.option.model;

import com.voting.votingapp.application.election.model.Election;
import com.voting.votingapp.application.vote.model.Vote;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = Option.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Option {
    public static final String TABLE_NAME = "OPTION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "option")
    private List<Vote> votes;

    @ManyToOne(cascade = CascadeType.ALL)
    private Election election;
}
