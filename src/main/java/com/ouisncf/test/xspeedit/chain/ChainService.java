package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ChainService {

    private final AtomicLong counter = new AtomicLong();

    /**
     * Create a new chain from a list of article sizes
     * @param articles List of article sizes as a string
     * @return Chain Newly created chain
     */
    public Chain createChain(String articles) {
        return new Chain(
                counter.incrementAndGet(),
                splitToListOfArticles(articles)
        );
    }

    /**
     * Split a string into a list of articles
     * @param articles List of article sizes as a string
     * @return List<Article> List of articles
     */
    private List<Article> splitToListOfArticles(String articles) {
        return articles.chars()
                .mapToObj(item -> new Article(Character.getNumericValue((char) item)))
                .collect(Collectors.toList());
    }
}