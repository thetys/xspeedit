package com.ouisncf.test.xspeedit.box;

import com.ouisncf.test.xspeedit.article.Article;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class Box {

    private static final int CAPACITY = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<Article> articles;

    public Box() {
        articles = new ArrayList<>();
    }

    public List<Article> getArticles() {
        return articles;
    }

    public boolean addArticle(Article article) {
        return articles.add(article);
    }

    /**
     * Return the articles in the box as a string.
     * An article is defined by its size.
     *
     * If the box contains 3 articles with respective sizes as 3, 7 and 5,
     * the function will return the string "375"
     *
     * @return String Article sizes as a string
     */
    public String getArticlesAsString() {
        return articles.stream()
                .map(Article::getSize)
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public int getFreeSpace() {
        int usedSpace = articles
                .stream()
                .mapToInt(Article::getSize)
                .sum();
        return CAPACITY - usedSpace;
    }
}
