package com.ouisncf.test.xspeedit.chain;

import com.ouisncf.test.xspeedit.article.Article;

import java.util.List;

public class Chain {

    private final long id;
    private final List<Article> articles;

    public Chain(long id, List<Article> articles) {
        this.id = id;
        this.articles = articles;
    }

    public long getId() {
        return id;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
