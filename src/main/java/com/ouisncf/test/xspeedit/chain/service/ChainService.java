package com.ouisncf.test.xspeedit.chain.service;

import com.ouisncf.test.xspeedit.article.Article;
import com.ouisncf.test.xspeedit.box.Box;
import com.ouisncf.test.xspeedit.chain.entity.Chain;
import com.ouisncf.test.xspeedit.chain.exception.ChainNotFoundException;
import com.ouisncf.test.xspeedit.chain.entity.ChainRepository;
import com.ouisncf.test.xspeedit.chain.exception.IllegalChainArgumentException;
import com.ouisncf.test.xspeedit.chain.exception.NoArticleFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
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
        if (!articles.matches("^[1-9]+$")) {
            throw new IllegalChainArgumentException("Article list must be a positive digit sequence with at least one element.");
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

    /**
     * Pack the chain articles in boxes
     *
     * @param chain The chain to be packaged
     * @return Chain The parameter after modifications
     */
    public Chain packChain(Chain chain) {
        if(chain.getArticles().isEmpty()) {
            throw new NoArticleFoundException(chain.getId());
        }

        chain.getBoxes().clear();

        chain.getArticles().stream()
                .sorted(Comparator.comparingInt(Article::getSize).reversed())
                .forEachOrdered(this::putInTheTightestSpot);

        return chainRepository.save(chain);
    }

    /**
     * Find the tightest spot in the chain boxes
     * and put the article in it
     * If no spot is found, a new box is created with the article inside
     *
     * @param article Article to put in a box
     */
    private void putInTheTightestSpot(Article article) {
        Optional<Box> tightestSpotOptional = article.getChain().getBoxes().stream()
                .filter(box -> box.getFreeSpace() >= article.getSize())
                .min(Comparator.comparingInt(Box::getFreeSpace));
        Box tightestSpot = tightestSpotOptional
                .orElseGet(() -> {
                    Box newBox = new Box();
                    article.getChain().addBox(newBox);
                    return newBox;
                });
        tightestSpot.addArticle(article);
    }
}