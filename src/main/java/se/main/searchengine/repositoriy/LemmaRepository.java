package se.main.searchengine.repositoriy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import se.main.searchengine.entity.Lemma;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Long> {
}
