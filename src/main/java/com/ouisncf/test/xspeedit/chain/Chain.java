package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;

import javax.persistence.*;
import java.util.List;

@Entity
public class Chain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Article> articles;

    public Chain() {
    }

    public Chain(List<Article> articles) {
        this.articles = articles;
    }

    public Long getId() {
        return id;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
