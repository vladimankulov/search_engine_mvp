package se.main.searchengine.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import se.main.searchengine.dto.IndexingResponse;
import se.main.searchengine.entity.Page;
import se.main.searchengine.entity.SiteModel;
import se.main.searchengine.enums.Status;
import se.main.searchengine.indexing.Connection;
import se.main.searchengine.property.SiteList;
import se.main.searchengine.property.SiteList.Site;
import se.main.searchengine.repositoriy.PageRepository;
import se.main.searchengine.repositoriy.SiteRepository;
import se.main.searchengine.services.IndexingService;

@Service
@RequiredArgsConstructor
public class IndexingServiceImpl implements IndexingService {

  private final SiteList sitesList;
  private final SiteRepository siteRepository;
  private final PageRepository pageRepository;

  @Override
  public IndexingResponse startIndexing() {
    ForkJoinPool forkJoinPool = new ForkJoinPool();
    List<Site> sites = sitesList.getSites();
    List<IndexingResponse> allResponses = new ArrayList<>();
    for (Site site : sites) {
      //TODO: Use class wrapper to correctly sync objects in list, maybe need to use ReadWriteLocker javadoc see
      //TODO: consider to use Dequeue instead of list
      List<Page> pageContents = Collections.synchronizedList(new LinkedList<>());
      String url = site.getUrl();
      siteRepository.findByUrl(url).ifPresent(siteRepository::delete);
      SiteModel newModel = new SiteModel();
      //TODO: Use DTO as object that operates through all recursive tasks, there is no need to save dto before recursive task is started
      newModel.setUrl(site.getUrl());
      newModel.setName(site.getName());
      newModel.setStatus(Status.INDEXING);
      newModel.setTime(LocalDateTime.now());
      siteRepository.save(newModel);
      Connection connection = new Connection(url, pageContents, newModel);

      forkJoinPool.invoke(connection);
      //TODO: If parent task is not waits all child tasks, then should think about sync at pool level
      connection.join();
      IndexingResponse currentResponse = connection.getIndexingResponse();
      allResponses.add(currentResponse);
      pageContents.stream().parallel().unordered()
          .forEach(p -> {
            //TODO: map dto to entity, save parent entity then bind all child entities
            siteRepository.save(newModel);
            pageRepository.save(p);
          });
    }
    boolean success = true;
    for (IndexingResponse response : allResponses) {
      if (!response.isResult()) {
        success = false;
        break;
      }
    }
    return new IndexingResponse(success, "Не удалось индексировать сайты");
  }


}
