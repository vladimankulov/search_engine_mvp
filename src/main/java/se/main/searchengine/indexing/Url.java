package se.main.searchengine.indexing;

import java.util.concurrent.CopyOnWriteArrayList;
import lombok.Data;

@Data
public class Url {
    private String url;
    private CopyOnWriteArrayList<Url> subUrlList;
    private int urlLevel;

    public Url(String url, int urlLevel){
        this.url = url;
        this.urlLevel = urlLevel;
    }
    public Url(String url, CopyOnWriteArrayList<Url> subUrlList){
        this.url = url;
        this.subUrlList = subUrlList;
    }
    public void addSubUrl(Url page){
        if (this.subUrlList == null){
            this.subUrlList = new CopyOnWriteArrayList<>();
        }
        this.subUrlList.add(page);
    }
}
