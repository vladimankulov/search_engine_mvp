package se.main.searchengine.indexing;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.http.HttpStatus;
import se.main.searchengine.dto.IndexingResponse;
import se.main.searchengine.entity.Page;
import se.main.searchengine.entity.SiteModel;

@Slf4j
@Data
public class Connection extends RecursiveAction {

  private Url url;
  private String firstUrl;
  private volatile Set<String> visitedLinks = Collections.synchronizedSet(new HashSet<>());
  private volatile Queue<Url> queueUrl = new ConcurrentLinkedQueue<>();
  private List<Page> pageContents;

  private IndexingResponse indexingResponse;
  private SiteModel model;

  public Connection(Url url, String firstUrl, Set<String> visitedLinks, Queue<Url> queueUrl) {
    this.url = url;
    this.firstUrl = firstUrl;
    this.queueUrl = queueUrl;
  }

  //TODO: Reconsider fields to control of pages that you've already iterate
  public Connection(String firstUrl, List<Page> pageContents, SiteModel model) {
    this.url = new Url(firstUrl, 0);
    this.firstUrl = firstUrl;
    this.visitedLinks.add(firstUrl);
    this.pageContents = pageContents;
    this.model = model;
  }

  public HashSet<Connection> getUrl(String url) {
    HashSet<Connection> resultList = new HashSet<>();
    System.out.println("Parsing Url with address: " + url);
    try {
      Thread.sleep(100);
      Document doc = getDocument(url);
      Elements elements = doc.select("a");

      String pathFromRoot = url;
      if (!url.equals(firstUrl)) {
        pathFromRoot = url.split(firstUrl)[1];
      }

      String documentHtmlBody = doc.body().data();
      Page page = new Page();
      page.setSiteModelId(model);
      page.setWebSitePath(pathFromRoot);
      page.setContent(documentHtmlBody);
      page.setError_code(HttpStatus.OK.name());
      //TODO: Think about sync instance field of DTO
      model.setTime(LocalDateTime.now());
      //TODO: make sure that all pages are added to one queue which reference is held in indexing service implementation
      this.pageContents.add(page);
      for (Element el : elements) {
        String link = el.attr("abs:href");
        if (!link.isEmpty() && !link.contains("#") && !visitedLinks.contains(link)
            && link.startsWith(firstUrl)) {
          if (!link.endsWith("/")) {
            link += "/";
          }

          Connection connection = new Connection(link, this.pageContents, this.model);
          connection.fork();
          resultList.add(connection);
        }
      }
    } catch (IOException | NoSuchElementException
             | InterruptedException | NullPointerException e) {
      log.error(e.getMessage());
    }
    return resultList;
  }

  public void compute() {
    getUrl(url.getUrl())
        .forEach(ForkJoinTask::join);
  }

  private Document getDocument(String url) throws IOException {
    return Jsoup.connect(url)
        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT " +
            "5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
        .referrer("https://www.google.com")
        .get();
  }
}
