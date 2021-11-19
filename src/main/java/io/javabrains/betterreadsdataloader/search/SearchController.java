package io.javabrains.betterreadsdataloader.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Controller
public class SearchController {

    private String SEARCH_URL = "http://openlibrary.org/search.json";

    private final String COVERS_URL = "http://covers.openlibrary.org/b/id/";

    private final WebClient webClient;

    public SearchController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(SEARCH_URL).build();
    }

    @GetMapping(value = "search")
    public String getSearchResults(@RequestParam String query, Model model) {
        Mono<SearchResult> resultsMono = this.webClient.get().uri("?q={query}&limit=20", query).retrieve()
                .bodyToMono(SearchResult.class);
        SearchResult searchResult = resultsMono.block();
        List<SearchResultBook> docs = searchResult.getDocs();
        List<SearchResultBook> collect = docs.stream().map(doc -> {
            doc.setKey(doc.getKey().replace("/works/", ""));
            String coverId = doc.getCover_i();
            String coverUrl = "/images/no-image.png";
            if (StringUtils.hasText(coverId)) {
                coverUrl = COVERS_URL + coverId + "-M.jpg";
            }
            doc.setCover_i(coverUrl);
            return doc;
        }).collect(Collectors.toList());
        model.addAttribute("searchResult", collect);
        return "search";
    }
}
