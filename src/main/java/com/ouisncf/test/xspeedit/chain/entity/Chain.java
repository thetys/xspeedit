package com.ouisncf.test.xspeedit.chain.entity;

import com.ouisncf.test.xspeedit.article.Article;
import com.ouisncf.test.xspeedit.box.Box;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "chain", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Box> boxes;

    private String initialization;

    public Chain() {
        articles = new ArrayList<>();
        boxes = new ArrayList<>();
    }

    public Chain(String initialization, List<Article> articles) {
        this.initialization = initialization;
        this.articles = articles;
        this.articles.forEach(article -> article.setChain(this));
        this.boxes = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public String getInitialization() {
        return initialization;
    }

    /**
     * Return a string describing the distribution of articles in boxes
     *
     * @return String Boxes as a string
     */
    public String getPackaging() {
        return boxes.stream()
                .filter(box -> box.getArticles().size() > 0)
                .map(Box::getArticlesAsString)
                .collect(Collectors.joining("/"));
    }

    public boolean addBox(Box box) {
        return boxes.add(box);
    }
}
