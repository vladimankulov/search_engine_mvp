package se.main.searchengine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity(name = "search_index")
public class SearchIndex {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "page_id")
  @ManyToOne(cascade = {CascadeType.REMOVE},
      fetch = FetchType.LAZY, optional = false)
  private Page page_id;

  @Column(name = "lemma_id")
  @OneToOne(cascade = {CascadeType.REMOVE},
      fetch = FetchType.LAZY, optional = false)
  private Lemma lemma_id;

  @Column(name = "rang")
  private float rang;
}
