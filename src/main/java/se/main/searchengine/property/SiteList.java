package se.main.searchengine.property;

import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application.sites")
public class SiteList {

  private List<Site> sites;

  @Data
  public static class Site {

    private String url;
    private String name;
  }
}
