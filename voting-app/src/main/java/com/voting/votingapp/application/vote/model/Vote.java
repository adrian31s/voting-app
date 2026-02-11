package com.voting.votingapp.application.vote.model;

import com.voting.votingapp.application.option.model.Option;
import com.voting.votingapp.application.voter.model.Voter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
  @JoinColumn(name = "voter_id", nullable = false)
  private Voter voter;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "option_id", nullable = false)
  private Option option;
}
