package se.main.searchengine.repositoriy;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.main.searchengine.entity.SiteModel;

@Repository
public interface SiteRepository extends JpaRepository<SiteModel, Long> {

  Optional<SiteModel> findByUrl(String url);
}
