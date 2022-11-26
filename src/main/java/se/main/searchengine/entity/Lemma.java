package se.main.searchengine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "lemma")
public class Lemma {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "site_model_id")
  private String siteModelId;

  @Column(name = "lemma")
  private String lemma;

  @Column(name = "frequency")
  private String frequency;

}
