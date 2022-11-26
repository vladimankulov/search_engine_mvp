package se.main.searchengine.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.main.searchengine.entity.SearchIndex;

@Repository
public interface SearchIndexRepository extends JpaRepository<SearchIndex, Long> {
}
