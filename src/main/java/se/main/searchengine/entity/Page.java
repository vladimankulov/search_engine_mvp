package se.main.searchengine.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "page")
public class Page {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "site_model_id")
  @ManyToOne(cascade = {CascadeType.REMOVE},
      fetch = FetchType.EAGER, optional = false)
  private SiteModel siteModelId;

  @Column(name = "web_site_path")
  private String webSitePath;

  @Column(name = "error_code")
  private String error_code;

  @Column(name = "content")
  private String content;
}
