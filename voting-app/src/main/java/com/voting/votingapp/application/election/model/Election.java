package com.voting.votingapp.application.election.model;

import com.voting.votingapp.application.option.model.Option;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@Table(name = Election.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class Election {
    public static final String TABLE_NAME = "ELECTION";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "election")
    private List<Option> options;
}
