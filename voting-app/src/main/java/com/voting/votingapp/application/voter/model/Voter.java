package com.voting.votingapp.application.voter.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@Table(name = Voter.TABLE_NAME)
@AllArgsConstructor
@NoArgsConstructor
public class Voter {
    public static final String TABLE_NAME = "VOTER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "EMAIL", nullable = false, unique = true)
    private String email;

    @Column(name = "BLOCKED")
    private boolean blocked;
}
