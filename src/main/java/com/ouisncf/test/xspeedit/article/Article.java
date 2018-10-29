package com.ouisncf.test.xspeedit.article;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int size;

    public Article() {
    }

    public Article(int size) {
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public int getSize() {
        return size;
    }
}
