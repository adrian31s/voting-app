package com.voting.votingapp.application.option.model;

import com.voting.votingapp.application.election.model.Election;
import com.voting.votingapp.application.vote.model.Vote;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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

  @OneToMany(mappedBy = "option")
  private List<Vote> votes;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "election_id", nullable = false)
  private Election election;
}
