package se.main.searchengine.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IndexingResponse {
    private boolean result;
    private String error;

}
