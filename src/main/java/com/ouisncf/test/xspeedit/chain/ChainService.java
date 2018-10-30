package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChainService {

    private final ChainRepository chainRepository;

    public ChainService(@Autowired ChainRepository chainRepository) {
        this.chainRepository = chainRepository;
    }

    /**
     * Create a new chain from a list of article sizes
     *
     * @param articles List of article sizes as a string
     * @return Chain Newly created chain
     */
    public Chain createChain(String articles) {
        if (!articles.matches("^\\d+$")) {
            throw new IllegalArgumentException("Articles list must be be a number sequence with at least one element");
        }
        return chainRepository.save(
                new Chain(splitToListOfArticles(articles))
        );
    }

    /**
     * Split a string into a list of articles
     *
     * @param articles List of article sizes as a string
     * @return List<Article> List of articles
     */
    private List<Article> splitToListOfArticles(String articles) {
        return articles.chars()
                .mapToObj(item -> new Article(Character.getNumericValue((char) item)))
                .collect(Collectors.toList());
    }

    /**
     * Get all chains
     *
     * @return List<Chain>
     */
    public List<Chain> getAll() {
        return chainRepository.findAll();
    }

    public Chain getOne(Long id) {
        return chainRepository
                .findById(id)
                .orElseThrow(() -> new ChainNotFoundException(id));
    }
}