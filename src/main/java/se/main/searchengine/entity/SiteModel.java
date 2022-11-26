package se.main.searchengine.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;
import se.main.searchengine.enums.Status;

@Data
@Entity(name = "site")
public class SiteModel {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "status_indexing_of_web_site")
  private volatile Status status;

  @Column(name = "status_time")
  private volatile LocalDateTime time;

  @Column(name = "last_error")
  private String lastError;

  @Column(name = "url")
  private String url;

  @Column(name = "web_site_name")
  private String name;
}
