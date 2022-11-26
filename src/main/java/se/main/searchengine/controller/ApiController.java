package se.main.searchengine.controller;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import se.main.searchengine.dto.IndexingResponse;
import se.main.searchengine.dto.statistics.StatisticsResponse;
import se.main.searchengine.services.IndexingService;
import se.main.searchengine.services.StatisticsService;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

  private final IndexingService indexingService;
  private final StatisticsService statisticsService;

  @GetMapping("/statistics")
  public ResponseEntity<StatisticsResponse> statistics() {
    return ResponseEntity.ok(statisticsService.getStatistics());
  }

  @GetMapping(path = "/startIndexing",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<IndexingResponse> startIndexing() {
    IndexingResponse indexingResponse = indexingService.startIndexing();

    return ResponseEntity.of(Optional.of(indexingResponse));
  }
}
