package com.ouisncf.test.xspeedit.chain;

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

    @OneToMany(cascade = CascadeType.ALL)
    private List<Article> articles;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Box> boxes;

    public Chain() {
        boxes = new ArrayList<>();
    }

    public Chain(List<Article> articles) {
        this.articles = articles;
        boxes = new ArrayList<>();
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

    public String getPackaging() {
        return boxes.stream()
                .filter(box -> box.getArticles().size() > 0)
                .map(Box::getArticlesAsString)
                .collect(Collectors.joining("/"));
    }
}
