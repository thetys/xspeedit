package com.ouisncf.test.xspeedit.article;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ouisncf.test.xspeedit.chain.Chain;

import javax.persistence.*;

@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int size;

    @ManyToOne(optional = false)
    @JsonIgnore
    private Chain chain;

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

    public Chain getChain() {
        return chain;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }
}
